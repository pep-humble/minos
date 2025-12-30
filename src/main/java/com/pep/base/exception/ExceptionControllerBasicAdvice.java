package com.pep.base.exception;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Locale;

/**
 * 控制器异常切面
 * Hidden注解,隐藏了该类,防止knife4j生成文档时受影响
 *
 * @author liu.gang
 */
@Slf4j
@Hidden
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerBasicAdvice {

    /**
     * 异常映射器
     */
    protected final ExceptionMapper exceptionMapper;

    /**
     * 处理业务异常
     *
     * @param locale    地区
     * @param exception 异常信息
     * @return 处理结果
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionVo> handlerBusinessException(Locale locale, BusinessException exception) {
        log.error(exception.getMessage(), exception);
        return this.exceptionMapper.handlerException(locale, exception);
    }

    /**
     * 处理验证异常
     *
     * @param locale    地区
     * @param exception 异常信息
     * @return 处理结果
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionVo> handlerBindException(Locale locale, BindException exception) {
        log.error(exception.getMessage(), exception);
        return this.exceptionMapper.handlerValidateErrors(locale, exception);
    }

    /**
     * 处理验证异常
     *
     * @param locale    地区
     * @param exception 异常信息
     * @return 处理结果
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ExceptionVo> handlerBindException(Locale locale, WebExchangeBindException exception) {
        log.error(exception.getMessage(), exception);
        return this.exceptionMapper.handlerValidateErrors(locale, exception);
    }

    /**
     * 处理属性验证异常
     *
     * @param locale    地区
     * @param exception 异常信息
     * @return 处理结果
     */
    @ExceptionHandler(InvalidPropertyException.class)
    public ResponseEntity<ExceptionVo> handlerInvalidPropertyException(Locale locale, InvalidPropertyException exception) {
        log.error(exception.getMessage(), exception);
        return this.exceptionMapper.handlerException(locale, exception, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理HTTP消息不可读异常
     *
     * @param locale    地区
     * @param exception 异常信息
     * @return 处理结果
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionVo> handlerHttpMessageNotReadableException(Locale locale, HttpMessageNotReadableException exception) {
        log.error(exception.getMessage(), exception);
        return this.exceptionMapper.handlerException(locale, exception, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理HTTP请求方式不支持的异常
     *
     * @param locale    地区
     * @param exception 异常信息
     * @return 处理结果
     */
    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ExceptionVo> handlerMethodNotAllowedException(Locale locale, MethodNotAllowedException exception) {
        log.error(exception.getMessage(), exception);
        return this.exceptionMapper.handlerException(locale, exception, HttpStatus.resolve(exception.getStatusCode().value()));
    }

    /**
     * 兜底异常处理
     *
     * @param locale    地区
     * @param exception 异常信息
     * @return 处理结果
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionVo> handlerThrowable(Locale locale, Throwable exception) {
        log.error(exception.getMessage(), exception);
        Throwable cause = exception.getCause();
        if (cause instanceof BusinessException businessException) {
            return this.exceptionMapper.handlerException(locale, businessException);
        }
        return this.exceptionMapper.handlerException(locale, exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理接口不存在的异常
     *
     * @param locale    地区
     * @param exception 异常信息
     * @return 处理结果
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionVo> handlerNoHandlerFoundException(Locale locale, NoHandlerFoundException exception) {
        log.error(exception.getMessage(), exception);
        return this.exceptionMapper.handlerException(locale, exception, HttpStatus.NOT_FOUND);
    }

    /**
     * 处理资源不存在的异常
     *
     * @param locale    地区
     * @param exception 异常信息
     * @return 处理结果
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionVo> handlerNoHandlerFoundException(Locale locale, NoResourceFoundException exception) {
        log.error(exception.getMessage(), exception);
        return this.exceptionMapper.handlerException(locale, exception, HttpStatus.NOT_FOUND);
    }
}
