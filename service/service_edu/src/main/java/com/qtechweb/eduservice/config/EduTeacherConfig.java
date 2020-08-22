package com.qtechweb.eduservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.qtechweb.eduservice.mapper"})
public class EduTeacherConfig {

}
