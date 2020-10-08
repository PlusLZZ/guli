package com.qtechweb.statistics.controller;


import com.qtechweb.commonutils.result.Result;
import com.qtechweb.statistics.service.StatisticsService;
import com.qtechweb.statistics.service.impl.StatisticsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author lzz
 * @since 2020-10-07
 */
@Slf4j
@Api(value = "统计数据接口")
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource(type = StatisticsServiceImpl.class)
    private StatisticsService statisticsService;

    @ApiOperation("统计表单数据")
    @PostMapping(path = "/statisticsInfo")
    public Result statisticsInfo(@RequestBody String[] day) {
        return Result.success(statisticsService.showChartData(day[0], day[1]));
    }


}

