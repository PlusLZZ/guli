package com.qtechweb.order.controller;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@CrossOrigin
@RestController
@RequestMapping("/order/log")
public class PayLogController {

}

