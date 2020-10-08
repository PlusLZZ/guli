package com.qtechweb.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.statistics.client.UcenterClient;
import com.qtechweb.statistics.entity.Statistics;
import com.qtechweb.statistics.mapper.StatisticsMapper;
import com.qtechweb.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-10-07
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, Statistics> implements StatisticsService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /*
     * TODO
     *  这里暂时做数据模拟,后期有机会再完善
     * */
    @Override
    public Map<String, Object> statisticsInfo(String day) {
        Map<String, Object> map = new HashMap<>();
        String key = "statistics:" + day;
        if (redisTemplate.hasKey(key)) {
            map = (Map<String, Object>) redisTemplate.opsForValue().get(key);
            return map;
        }
        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        Statistics one = getOne(wrapper);
        AssertUtils.ObjectNotNull(one, "请稍等,正在统计中");
        map.put("registerNum", one.getRegisterNum());
        map.put("loginNum", one.getLoginNum());
        map.put("videoViewNum", one.getVideoViewNum());
        map.put("courseNum", one.getCourseNum());
        redisTemplate.opsForValue().set(key, map, 30, TimeUnit.DAYS);
        return map;
    }

    @Override
    public Map<String, List> showChartData(String start, String end) {
        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", start, end);
        List<Statistics> list = list(wrapper);
        Map<String, List> map = new HashMap<>();
        List<String> timeList = new ArrayList<>();
        List<Integer> courseList = new ArrayList<>();
        List<Integer> registerList = new ArrayList<>();
        List<Integer> videoViewList = new ArrayList<>();
        List<Integer> loginList = new ArrayList<>();
        list.forEach(o -> {
            timeList.add(o.getDateCalculated());
            courseList.add(o.getCourseNum());
            registerList.add(o.getRegisterNum());
            videoViewList.add(o.getVideoViewNum());
            loginList.add(o.getLoginNum());
        });
        map.put("timeList", timeList);
        map.put("courseList", courseList);
        map.put("registerList", registerList);
        map.put("videoViewList", videoViewList);
        map.put("loginList", loginList);
        return map;
    }
}
