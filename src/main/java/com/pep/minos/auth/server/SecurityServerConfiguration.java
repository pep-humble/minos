package com.pep.minos.auth.server;

import com.pep.minos.auth.server.properties.SecurityBusinessProperties;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 安全服务配置
 *
 * @author liu.gang
 */
@Configuration
public class SecurityServerConfiguration {

    /**
     * 密码编码器
     *
     * @return 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 安全框架业务配置
     *
     * @return 安全框架业务配置
     */
    @Bean
    @ConfigurationProperties(prefix = "business.security")
    public SecurityBusinessProperties securityBusinessProperties() {
        return new SecurityBusinessProperties();
    }

    /**
     * 构建安全拦截器链
     *
     * @param httpSecurity 拦截器链配置构建器
     * @return 安全拦截器链
     * @throws Exception 可能抛出的异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, SecurityBusinessProperties securityBusinessProperties) throws Exception {
        return httpSecurity
                // 禁用csrf
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用跨域
                .cors(AbstractHttpConfigurer::disable)
                // 表单登录
                .formLogin(formLoginConfigurer ->
                        formLoginConfigurer
                                // 登录入口
                                .loginProcessingUrl("/auth/login")
                                // 认证成功处理器
                                .successHandler(new AuthenticationSuccessResponseHandler())
                                // 认证失败处理器
                                .failureHandler(new AuthenticationFailureResponseHandler())
                )
                // 异常处理器
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer
                                .authenticationEntryPoint(new ResponseHttpStatusEntryPoint())
                )
                // 鉴权
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    // 免鉴权
                    if (CollectionUtils.isNotEmpty(securityBusinessProperties.getPermitAll())) {
                        RequestMatcher[] requestMatchers = securityBusinessProperties.getPermitAll()
                                .stream()
                                .map(pathMatcher ->
                                        PathPatternRequestMatcher.withDefaults()
                                                .matcher(pathMatcher.getMethod(), pathMatcher.getPath())
                                )
                                .toArray(RequestMatcher[]::new);
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(requestMatchers)
                                .permitAll();
                    }
                    // 其余接口需要鉴权
                    authorizationManagerRequestMatcherRegistry
                            .anyRequest()
                            .authenticated();
                })
                .build();
    }
}
