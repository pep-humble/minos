package com.pep.base.mvc;

import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 额外的spring web mvc配置
 *
 * @author liu.gang
 */
@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
        if (CollectionUtils.isNotEmpty(converters)) {
            // 将FastJsonHttpMessageConverter放到首位
            converters.add(0, new FastJsonHttpMessageConverter());
        }
    }

}
