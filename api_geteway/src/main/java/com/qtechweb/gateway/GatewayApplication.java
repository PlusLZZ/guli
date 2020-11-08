package com.qtechweb.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
/*
 *  配置中心的Data ID的规则为
 *  ${prefix}-${spring.profile.active}.${file-extension}
 *  - prefix 服务名称
 *  - spring.profile.active 环境
 *  - file-extension 配置文件格式比如properties或者yml
 * */