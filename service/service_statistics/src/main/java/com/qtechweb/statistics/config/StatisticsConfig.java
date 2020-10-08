package com.qtechweb.statistics.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@MapperScan("com.qtechweb.statistics.mapper")
@EnableScheduling
public class StatisticsConfig {


}
