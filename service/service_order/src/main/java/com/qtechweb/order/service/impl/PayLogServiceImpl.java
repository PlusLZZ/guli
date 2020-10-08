package com.qtechweb.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import com.qtechweb.commonutils.enums.OrderEnum;
import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.exception.DefaultException;
import com.qtechweb.order.entity.Order;
import com.qtechweb.order.entity.PayLog;
import com.qtechweb.order.mapper.PayLogMapper;
import com.qtechweb.order.service.OrderService;
import com.qtechweb.order.service.PayLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-09-22
 */
@Slf4j
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Resource(type = OrderServiceImpl.class)
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Map<String, Object> createNative(String orderNo) {
        Order order = orderService.getOrderInfo(orderNo);
        /* 2.设置生成二维码的参数 */
        Map<String, String> m = new HashMap<>();
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        m.put("body", order.getCourseTitle());
        m.put("out_trade_no", orderNo);
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
        m.put("spbill_create_ip", "127.0.0.1");
        m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
        m.put("trade_type", "NATIVE");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> formEntity = null;
        try {
            formEntity = new HttpEntity<>(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"), headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseEntity<String> entity = restTemplate.postForEntity("https://api.mch.weixin.qq.com/pay/unifiedorder", formEntity, String.class);
        Map<String, String> result = null;
        try {
            result = WXPayUtil.xmlToMap(entity.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map = new HashMap<>();
        map.put("out_trade_no", orderNo);
        map.put("course_id", order.getCourseId());
        map.put("total_fee", order.getTotalFee());
        map.put("result_code", result.get("result_code"));
        map.put("code_url", result.get("code_url"));
        log.info(map.toString());
        return map;
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> formEntity = null;
            try {
                formEntity = new HttpEntity<>(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"), headers);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResponseEntity<String> entity = restTemplate.postForEntity("https://api.mch.weixin.qq.com/pay/orderquery", formEntity, String.class);
            Map<String, String> result = null;
            try {
                result = WXPayUtil.xmlToMap(entity.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //7、返回
            return result;
        } catch (Exception e) {
            throw new DefaultException(OrderEnum.PAY_FAIL);
        }
    }

    @Override
    public String updateOrdersStatus(Map<String, String> map) {
        AssertUtils.StringNotNull(map.get("trade_state"), OrderEnum.PAY_FAIL);
        AssertUtils.BooleanIsTrue(map.get("trade_state").equals("SUCCESS"), OrderEnum.PAY_RUN);
        //获取订单id
        String orderNo = map.get("out_trade_no");
        //根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        if (order.getStatus().intValue() == 1) return "已支付";
        order.setStatus(1);
        orderService.updateById(order);

        //记录支付日志
        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表
        return "支付成功";
    }
}
