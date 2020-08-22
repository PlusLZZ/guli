package com.qtechweb.servicebase.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
@PropertySource("classpath:application.yml")
public class SwaggerConfig {

    //从配置文件获取配置信息
    @Value("${swagger.groupName}")
    private String groupName;

    @Value("${swagger.backage}")
    private String backage;

    /*
     * 通过apiInfo属性配置文档信息
     * */
    public ApiInfo apiInfo() {
        Contact contact = new Contact("刘泽志", "http://qtechweb.xyz:8088/#/home", "lzzwuhanwork@outlook.com");
        return new ApiInfo(
                "谷粒学院", // 标题
                "谷粒学院API接口文档", // 描述
                "v1.0", // 版本
                "https://github.com/PlusLZZ/guli", // 组织链接
                contact, // 联系人信息
                "Apache 2.0 许可", // 许可
                "http://www.apache.org/licenses/LICENSE-2.0.html", // 许可连接
                new ArrayList<>()// 扩展
        );
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage(backage))
                .build();
    }

}
