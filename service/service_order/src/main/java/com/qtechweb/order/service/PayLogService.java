package com.qtechweb.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.order.entity.PayLog;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-09-22
 */
public interface PayLogService extends IService<PayLog> {

    /* 生成微信二维码支付信息 */
    Map<String, Object> createNative(String orderNo);

    /* 查询订单支付状态 */
    Map<String, String> queryPayStatus(String orderNo);

    /* 更新订单表订单状态 */
    String updateOrdersStatus(Map<String, String> map);

}
