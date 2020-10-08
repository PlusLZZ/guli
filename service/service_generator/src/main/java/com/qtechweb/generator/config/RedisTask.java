package com.qtechweb.generator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisTask {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Scheduled(cron = "0 0/3 * * * ?")
    public void redisHeart() {
        redisTemplate.hasKey("redisKey");
        log.info("执行了一次redis心跳");
    }


}
