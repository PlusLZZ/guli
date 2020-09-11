package com.qtechweb.edu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.qtechweb.edu.mapper"})
public class EduTeacherConfig {

}
