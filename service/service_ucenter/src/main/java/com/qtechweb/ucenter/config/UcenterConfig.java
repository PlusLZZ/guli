package com.qtechweb.ucenter.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.qtechweb.ucenter.mapper")
@Configuration
@EnableScheduling
public class UcenterConfig {
}
