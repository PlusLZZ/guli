package com.qtechweb.order.controller;


import com.qtechweb.commonutils.result.ResMap;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.commonutils.utils.JwtUtils;
import com.qtechweb.order.service.OrderService;
import com.qtechweb.order.service.impl.OrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lzz
 * @since 2020-09-22
 */
@Slf4j
@Api(value = "订单接口")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource(type = OrderServiceImpl.class)
    private OrderService orderService;

    @ApiOperation("根据课程id生成订单")
    @GetMapping("/create/{courseId}")
    public Result createOrder(@PathVariable("courseId") String courseId,
                              HttpServletRequest request) {
        return Result.success(ResMap.create().setKeyValue("orderNo", orderService.createOrder(courseId, request)));
    }

    @ApiOperation("根据订单号码查询订单信息")
    @GetMapping(path = "/no/{orderNo}")
    public Result getOrderInfo(@PathVariable("orderNo") String orderNo) {
        return Result.success(orderService.getOrderInfo(orderNo));
    }

    @ApiOperation("查询订单状态(远程调用)")
    @GetMapping("/isBuy/{courseId}/{memberId}")
    public Result isBuy(@PathVariable("courseId") String courseId,
                        @PathVariable("memberId") String memberId) {
        log.info(courseId + "=====" + memberId);
        return Result.success(orderService.isBuy(courseId, memberId));
    }

    @ApiOperation("查询订单状态")
    @GetMapping("/isBuy/{courseId}")
    public Result isBuy(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        if (null == id || id.isEmpty()) {
            return Result.success(false);
        }
        return Result.success(orderService.isBuy(courseId, id));
    }

}

