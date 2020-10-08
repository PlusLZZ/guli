package com.qtechweb.order.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.*;

@Configuration
@MapperScan(basePackages = {"com.qtechweb.order.mapper"})
@EnableAsync
@Slf4j
@EnableScheduling
public class OrderConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /* 获取处理器数量来动态设置线程池参数 */
    private static final int THREADS = Runtime.getRuntime().availableProcessors() + 1;

    final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("guli-task-name-%d")
            .setDaemon(true)
            .build();

    @Bean("guliExecutor")
    public Executor guliExecutor() {
        return new ThreadPoolExecutor(THREADS, 2 * THREADS,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                threadFactory, (r, executor) -> {
            log.error("线程队列已满 :" + r.toString());
            /*
             * TODO
             *  队列已满可以存进redis里面,再做定时任务进行执行
             *  或者放到mq中,提交给别的空闲服务进行执行
             * */
        });
    }
}
