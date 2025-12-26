package com.pep.base.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 异常配置
 *
 * @author gang.liu
 */
@Configuration
public class ExceptionConfiguration {

    /**
     * 异常配置
     *
     * @return 异常配置
     */
    @Bean
    @ConfigurationProperties(prefix = "business.exception")
    @ConditionalOnMissingBean(name = "exceptionProperties")
    public ExceptionProperties exceptionProperties() {
        return new ExceptionProperties();
    }

    /**
     * 异常映射器
     *
     * @param messageSource       国际化消息
     * @param exceptionProperties 异常配置
     * @return 异常映射器
     */
    @Bean
    public ExceptionMapper exceptionMapper(
            MessageSource messageSource,
            ExceptionProperties exceptionProperties
    ) {
        return new ExceptionMapper(messageSource, exceptionProperties);
    }

    /**
     * 控制器异常切面 - 基础
     *
     * @param exceptionMapper 异常映射器
     * @return 控制器异常切面 - 基础
     */
    @Bean
    @Order(1)
    @ConditionalOnMissingBean(name = "exceptionControllerBasicAdvice")
    public ExceptionControllerBasicAdvice exceptionControllerBasicAdvice(ExceptionMapper exceptionMapper) {
        return new ExceptionControllerBasicAdvice(exceptionMapper);
    }
}