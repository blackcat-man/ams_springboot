package com.four.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {


    @Bean(value = "defaultApi")
    public Docket defaultApi2() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("电信运营商支撑平台")
                .description("软件工程3班第4组")
                .contact(new Contact("HeiMaoMan", "www.xxx.com", "17299806" +
                        "0@com"))
                .version("1.0")
                .build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("all")
                .select().apis(RequestHandlerSelectors.basePackage("com.four.controller"))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }
}
