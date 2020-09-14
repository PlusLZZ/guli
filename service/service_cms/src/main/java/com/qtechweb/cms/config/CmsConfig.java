package com.qtechweb.cms.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.qtechweb.cms.mapper")
public class CmsConfig {
}
