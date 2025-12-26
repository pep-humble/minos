package com.pep.base.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * openapi配置
 * @author liu.gang
 */
@Configuration
public class OpenApiConfiguration {

    /**
     * openApi客制化配置
     * @return openApi客制化配置
     */
    @Bean
    public OpenAPI mainOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Minos系统API文档")
                                .description("Minos系统API文档, 使用Spring Boot 3 + OpenAPI 3构建")
                                .version("v1.0.0")
                                .contact(
                                        new Contact()
                                                .name("刘岗")
                                                .email("150934732310@163.com")
                                                .url("https://github.com/pep-humble/minos")
                                )
                );
    }

}
