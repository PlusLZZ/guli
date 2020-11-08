package com.qtechweb.acl.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@MapperScan("com.qtechweb.acl.mapper")
@EnableScheduling
public class AclConfig {
}
