package com.pep.base.mvc.customize;

import com.pep.base.function.ListCollector;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 包装成ResponseRequest返回值的响应体切面
 *
 * @author liu.gang
 */
@Hidden
@RestControllerAdvice
public class ResponseResultBodyCustomizeAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 不包装的类型
     */
    private final List<Class<?>> DONT_WRAPPER_CLASS_LIST = Arrays.asList(
            ResponseEntity.class,
            ResponseResult.class
    );

    /**
     * 不包装响应的路径集合
     */
    private final List<String> ignoreWrapperPathList;

    /**
     * 路径匹配器
     */
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public ResponseResultBodyCustomizeAdvice(ServerProperties serverProperties) {
        String contextPath = serverProperties.getServlet().getContextPath();
        ignoreWrapperPathList = Stream.of(
                        "/v3/api-docs/**"
                )
                .map(contextPath::concat)
                .collect(new ListCollector<>());

    }

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> parameterType = returnType.getParameterType();
        return DONT_WRAPPER_CLASS_LIST.stream()
                .noneMatch(aClass -> aClass.isAssignableFrom(parameterType));
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        String requestPath = request.getURI().getPath();
        if (CollectionUtils.isNotEmpty(ignoreWrapperPathList)) {
            boolean anyMatch = ignoreWrapperPathList.stream()
                    .anyMatch(path ->
                            antPathMatcher.match(path, requestPath)
                    );
            if (anyMatch) {
                return body;
            }
        }
        return new ResponseResult().setData(body);
    }
}
