package com.qtechweb.gateway.config;

import com.qtechweb.gateway.filter.TimeGlobalFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@Slf4j
public class GatewayConfig {

    /*
     * 网关跨域配置
     * */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }


    /*
     * 注册全局过滤器
     * order数字越小,优先级越高
     *  */
    @Bean
    @Order(value = 10)
    public GlobalFilter timeGlobalFilter() {
        log.info(" timeGlobalFilter 创建了---");
        return new TimeGlobalFilter();
    }

}
