package com.pep.base.exception;

import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import com.pep.base.function.ListCollector;
import com.pep.base.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 异常映射器
 * @author liu.gang
 * @param messageSource 国际化消息
 * @param exceptionProperties 异常配置
 */
@Slf4j
public record ExceptionMapper(MessageSource messageSource, ExceptionProperties exceptionProperties) {

    /**
     * 过滤消息
     *
     * @param message 原消息
     * @return 过滤后的消息
     */
    private String filterMessage(String message) {
        return Optional
                .ofNullable(message)
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .map(
                        item -> {
                            Map<String, String> escape = this.exceptionProperties.getMessageEscape();
                            if (MapUtils.isNotEmpty(escape)) {
                                for (Map.Entry<String, String> entry : escape.entrySet()) {
                                    item = item.replace(entry.getKey(), entry.getValue());
                                }
                            }
                            return item;
                        }
                )
                .orElse(StringUtils.EMPTY);
    }

    /**
     * 包装异常信息
     *
     * @param locale 地区
     * @param vo     异常信息
     * @return 响应体
     */
    public ResponseEntity<ExceptionVo> wrap(Locale locale, ExceptionVo vo) {
        HttpStatus httpStatus = Optional
                .ofNullable(vo.getCode())
                .map(item -> {
                    try {
                        return HttpStatus.valueOf(item);
                    } catch (Exception e) {
                        log.warn(e.getMessage(), e);
                        return HttpStatus.INTERNAL_SERVER_ERROR;
                    }
                })
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        if (StringUtils.isNotEmpty(vo.getMsg())) {
            String code = "http.status.".concat(String.valueOf(httpStatus.value()));
            String message = this.messageSource.getMessage(code, null, code, locale);
            if (Objects.equals(message, code)) {
                code = "http.status.".concat(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
                message = this.messageSource.getMessage(code, null, code, locale);
            }
            vo.setMsg(this.filterMessage(message));
        }
        return new ResponseEntity<>(vo, httpStatus);
    }

    /**
     * 处理异常
     *
     * @param locale     地区
     * @param exception  异常信息
     * @param httpStatus HTTP状态
     * @return 处理结果
     */
    public ResponseEntity<ExceptionVo> handlerException(Locale locale, Throwable exception, HttpStatus httpStatus) {
        if (BooleanUtils.isTrue(this.exceptionProperties.getResponseRealExceptions())) {
            // 输出真实异常
            return this.wrap(
                    locale,
                    new ExceptionVo()
                            .setCode(httpStatus.value())
                            .setData(
                                    new ExceptionVo()
                                            .setCode(httpStatus.value())
                                            .setData(
                                                    new SimpleExceptionVo()
                                                            .setMessage(exception.getMessage())
                                                            .setType(exception.getClass())
                                                            .setCauseType(
                                                                    Optional
                                                                            .ofNullable(exception.getCause())
                                                                            .map(Throwable::getClass)
                                                                            .orElse(null)
                                                            )
                                            )
                            )
            );
        }
        return this.wrap(locale, new ExceptionVo().setCode(httpStatus.value()));
    }

    /**
     * 处理异常
     *
     * @param locale    地区
     * @param exception 异常信息
     * @return 处理结果
     */
    public ResponseEntity<ExceptionVo> handlerException(Locale locale, BusinessException exception) {
        String message = exception.getMessage();
        String finalMessage = StringUtil
                .ifFriendly(message)
                //空指针异常的消息不是国际化文案
                .filter(item -> StringUtil.notEqualsIgnoreCase(item, "null"))
                //超长文案不会是国际化code
                .filter(item -> item.length() < 200)
                //包含特殊字符的的不会是国际化code
                .filter(item -> StringUtils.containsNone(item, '{', '}', '[', ']', '(', ')', ' ', '/', CharUtils.LF, CharUtils.CR))
                .map(code -> this.messageSource.getMessage(code, exception.getMessageArgs(), code, locale))
                .orElse(message);
        return this.wrap(
                locale,
                new ExceptionVo()
                        .setCode(exception.getResponseCode())
                        .setMsg(finalMessage)
                        .setData(exception.getData())
        );
    }

    /**
     * 处理验证异常
     *
     * @param locale 地区
     * @param errors 异常信息
     * @return 处理结果
     */
    public ResponseEntity<ExceptionVo> handlerValidateErrors(Locale locale, Errors errors) {
        return this.wrap(
                locale,
                new ExceptionVo()
                        .setCode(HttpStatus.BAD_REQUEST.value())
                        .setMsg(
                                Optional
                                        .of(errors.getAllErrors())
                                        .filter(CollectionUtils::isNotEmpty)
                                        .map(
                                                list -> list
                                                        .stream()
                                                        .filter(Objects::nonNull)
                                                        .map(error -> {
                                                            if (error instanceof FieldError fieldError) {
                                                                return String.join(StringUtils.SPACE, fieldError.getField(), fieldError.getDefaultMessage());
                                                            }
                                                            return error.getDefaultMessage();
                                                        })
                                                        .map(StringUtil::friendly)
                                                        .filter(Objects::nonNull)
                                                        .distinct()
                                                        .collect(Collectors.joining(StringUtil.COMMA))
                                        )
                                        .flatMap(StringUtil::ifFriendly)
                                        .map(this::filterMessage)
                                        .orElse(null)
                        )
                        .setData(
                                new ErrorsVo()
                                        .setGlobalErrors(
                                                Optional
                                                        .of(errors.getGlobalErrors())
                                                        .filter(CollectionUtils::isNotEmpty)
                                                        .map(
                                                                list -> list
                                                                        .stream()
                                                                        .filter(Objects::nonNull)
                                                                        .map(item -> new ObjectErrorVo().setObjectName(item.getObjectName()).setMessage(item.getDefaultMessage()))
                                                                        .collect(new ListCollector<>(list.size()))
                                                        )
                                                        .orElseGet(Collections::emptyList)
                                        )
                                        .setFieldErrors(
                                                Optional
                                                        .of(errors.getFieldErrors())
                                                        .filter(CollectionUtils::isNotEmpty)
                                                        .map(
                                                                list -> list
                                                                        .stream()
                                                                        .filter(Objects::nonNull)
                                                                        .map(item -> new FieldErrorVo().setObjectName(item.getObjectName()).setFieldName(item.getField()).setMessage(item.getDefaultMessage()))
                                                                        .collect(new ListCollector<>(list.size()))
                                                        )
                                                        .orElseGet(Collections::emptyList)
                                        )
                        )
        );
    }
}
