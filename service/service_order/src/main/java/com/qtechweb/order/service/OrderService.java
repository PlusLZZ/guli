package com.qtechweb.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.order.entity.Order;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-09-22
 */
public interface OrderService extends IService<Order> {

    /* 根据课程ID生成订单 */
    String createOrder(String courseId, HttpServletRequest request);

    /* 查询订单信息 */
    Order getOrderInfo(String orderNo);

    /* 查询课程时候已经被支付 */
    Boolean isBuy(String courseId, String memberId);

}
