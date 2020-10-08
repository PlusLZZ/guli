package com.qtechweb.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //服务不需要数据库,排除数据源的自动配置
@ComponentScan(basePackages = {"com.qtechweb"}) //http://localhost:8009/doc.html
public class IDGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(IDGeneratorApplication.class, args);
    }
}
