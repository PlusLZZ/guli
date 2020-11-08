package com.qtechweb.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.qtechweb"})//设置扫描到引入的swagger2配置   http://localhost:8008/doc.html
public class StatisticsApplication {


    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }
}
