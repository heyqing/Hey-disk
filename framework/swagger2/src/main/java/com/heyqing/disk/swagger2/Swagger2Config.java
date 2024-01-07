package com.heyqing.disk.swagger2;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ClassName:Swagger2Config
 * Package:com.heyqing.disk.swagger2
 * Description:
 *          接口文章配置类
 * @Date:2024/1/7
 * @Author:Heyqing
 */

@SpringBootConfiguration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Log4j2
public class Swagger2Config {

    @Autowired
    private Swagger2ConfigProperties properties;

    @Bean
    public Docket diskServerApi(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .enable(properties.isShow())
                .groupName(properties.getGroupName())
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
        log.info("The swagger2 have been loaded successful !");
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .termsOfServiceUrl(properties.getTermsOfServiceUrl())
                .contact(new Contact(properties.getContactName(),properties.getContactUrl(),properties.getContactEmail()))
                .build();
    }

}
