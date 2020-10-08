package com.qtechweb.statistics.task;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.statistics.client.UcenterClient;
import com.qtechweb.statistics.entity.Statistics;
import com.qtechweb.statistics.service.StatisticsService;
import com.qtechweb.statistics.service.impl.StatisticsServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class StatisticsTask {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(type = StatisticsServiceImpl.class)
    private StatisticsService statisticsService;

    @Autowired
    private UcenterClient ucenterClient;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void statisticsInfo() {
        String day = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
        updateStatisticsInfo(day);
    }

    @Scheduled(cron = "30 59 23 * * ?")
    public void statisticsInfoByBefore() {
        String day = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
        updateStatisticsInfo(day);
    }

    @Scheduled(cron = "1 0 0 * * ?")
    public void statisticsInfoByAfter() {
        String day = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
        updateStatisticsInfo(day);
    }

    public void updateStatisticsInfo(String day) {
        Result result = ucenterClient.countRegister(day);
        Statistics statistics = new Statistics();
        statistics.setDateCalculated(day);
        if (result.getIsSuccess()) {
            statistics.setRegisterNum((Integer) result.getData());
        } else {
            statistics.setRegisterNum(0);
        }
        /*
         * TODO
         *  后期加上redis计数
         *  */
        statistics.setVideoViewNum(RandomUtils.nextInt(10, 1000));
        statistics.setLoginNum(RandomUtils.nextInt(20, 100));
        statistics.setCourseNum(RandomUtils.nextInt(1, 10));
        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        if (statisticsService.count(wrapper) > 0) {
            statisticsService.update(statistics, wrapper);
        } else {
            statisticsService.save(statistics);
        }
        /* redis操作 */
        String key = "statistics:" + day;
        Map<String, Object> map = new HashMap<>();
        map.put("registerNum", statistics.getRegisterNum());
        map.put("loginNum", statistics.getLoginNum());
        map.put("videoViewNum", statistics.getVideoViewNum());
        map.put("courseNum", statistics.getCourseNum());
        redisTemplate.opsForValue().set(key, map, 30, TimeUnit.DAYS);
    }

}
