package com.qtechweb.statistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.statistics.entity.Statistics;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-10-07
 */
public interface StatisticsService extends IService<Statistics> {


    /* 统计网站当天数据 */
    Map<String, Object> statisticsInfo(String day);

    /* 返回四个图表数据 */
    Map<String, List> showChartData(String start, String end);

}
