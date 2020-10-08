package com.qtechweb.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.commonutils.enums.AuthEnum;
import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.utils.JwtUtils;
import com.qtechweb.order.entity.Order;
import com.qtechweb.order.mapper.OrderMapper;
import com.qtechweb.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-09-22
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private AsyncTaskClient client;

    @Autowired
    @Qualifier("guliExecutor")
    private Executor executor;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Transactional
    @Override
    public String createOrder(String courseId, HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        AssertUtils.StringNotNull(id, AuthEnum.NOT_LOGGED);
        String key = courseId + ":" + id;
        if (redisTemplate.hasKey(key)) {
            return redisTemplate.opsForValue().get(key);
        }
        Map<String, JSONObject> result = new HashMap<>();
        CompletableFuture<JSONObject> mf = client.getMemberById(id)
                .thenApplyAsync((a) -> {
                    result.put("mo", a);
                    log.info("合并之后的任务执行使用的线程池是:");
                    return null;
                }, executor);
        CompletableFuture<JSONObject> cf = client.getCourseById(courseId)
                .thenApplyAsync((a) -> {
                    result.put("co", a);
                    return null;
                }, executor);
        ;
        CompletableFuture<JSONObject> idf = client.getOrderId()
                .thenApplyAsync((a) -> {
                    result.put("idf", a);
                    return null;
                }, executor);
        ;
        CompletableFuture.allOf(idf, mf, cf).join();
        //log.info(result.get("idf").toString());
        log.info(result.toString());
        Order order = new Order();
        JSONObject co = result.get("co");
        JSONObject mo = result.get("mo");
        JSONObject idf1 = result.get("idf");
        order.setOrderNo(idf1.getString("ID")).setCourseId(co.getString("id"))
                .setCourseTitle(co.getString("title")).setCourseCover(co.getString("cover"))
                .setMemberId(id).setNickname(mo.getString("nickname")).setMobile(mo.getString("mobile"))
                .setTotalFee(co.getBigDecimal("price")).setStatus(0).setPayType(1);
        boolean save = save(order);
        AssertUtils.BooleanIsTrue(save, "订单创建失败");
        redisTemplate.opsForValue().set(key, order.getOrderNo(), 30, TimeUnit.MINUTES);
        return order.getOrderNo();
    }

    @Cacheable(cacheNames = {"order"}, key = "#orderNo", sync = true)
    @Override
    public Order getOrderInfo(String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order one = getOne(wrapper);
        AssertUtils.ObjectNotNull(one, "无此订单号");
        return one;
    }

    @Override
    public Boolean isBuy(String courseId, String memberId) {
        if (redisTemplate.opsForHash().hasKey("isBuy", courseId + ":" + memberId)) {
            return true;
        }
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = count(wrapper);
        if (count > 0) {
            redisTemplate.opsForHash().put("isBuy", courseId + ":" + memberId, "1");
            return true;
        }
        return false;
    }

}
