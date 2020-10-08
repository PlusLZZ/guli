package com.qtechweb.order.controller;


import com.qtechweb.commonutils.result.Result;
import com.qtechweb.order.service.PayLogService;
import com.qtechweb.order.service.impl.PayLogServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author lzz
 * @since 2020-09-22
 */
@Slf4j
@Api(value = "支付日志接口")
@RestController
@RequestMapping("/order/pay")
public class PayLogController {

    @Resource(type = PayLogServiceImpl.class)
    private PayLogService payLogService;

    @ApiOperation("支付二维码生成")
    @GetMapping("createNative/{orderNo}")
    public Result createNative(@PathVariable("orderNo") String orderNo) {
        return Result.success(payLogService.createNative(orderNo));
    }

    @ApiOperation("查询订单支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable("orderNo") String orderNo) {
        log.info(orderNo);
        String s = payLogService.updateOrdersStatus(payLogService.queryPayStatus(orderNo));
        return Result.success(s);
    }

}

