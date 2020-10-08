package com.qtechweb.cms.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@MapperScan("com.qtechweb.cms.mapper")
@EnableScheduling
public class CmsConfig {
}
