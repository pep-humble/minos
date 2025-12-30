package com.pep.base.mvc.customize;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.List;

/**
 * 包装成ResponseRequest返回值的响应体切面
 *
 * @author liu.gang
 */
@ControllerAdvice
public class ResponseResultBodyCustomizeAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 不包装的类型
     */
    private final List<Class<?>> DONT_WRAPPER_CLASS_LIST = Arrays.asList(
            ResponseEntity.class,
            ResponseResult.class
    );

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> parameterType = returnType.getParameterType();
        return DONT_WRAPPER_CLASS_LIST.stream()
                .noneMatch(aClass -> aClass.isAssignableFrom(parameterType));
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        return new ResponseResult().setData(body);
    }
}
