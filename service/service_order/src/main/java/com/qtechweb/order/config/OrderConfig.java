package com.qtechweb.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.qtechweb.order.mapper"})
public class OrderConfig {
}
