package com.qtechweb.gateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class TimeGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("[TimeGlobalFilter] 访问的接口：{}", path);
        long start = System.currentTimeMillis();
        return chain.filter(exchange)
                // then的内容会在过滤器返回的时候执行，即最后执行
                .then(Mono.fromRunnable(() ->
                        log.info("[ {} ] 接口的访问耗时：{} /ms",
                                path, System.currentTimeMillis() - start))
                );
    }

}
