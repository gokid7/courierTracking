package com.courier.tracking.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String SWAGGER_API_VERSION = "1.0";
    private static final String LICENSE_TEXT = "License";
    private static final String TITLE = "Courier Tracking Rest API";
    private static final String DESCRIPTION = "Restful Api for Backend Courier Tracking Application";

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info()
                        .title(TITLE)
                        .description(DESCRIPTION)
                        .version(SWAGGER_API_VERSION)
                        .license(new License().name(LICENSE_TEXT)));
    }
}
