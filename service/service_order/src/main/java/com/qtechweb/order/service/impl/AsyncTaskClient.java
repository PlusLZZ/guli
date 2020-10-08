package com.qtechweb.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.order.client.CourseClient;
import com.qtechweb.order.client.IDClient;
import com.qtechweb.order.client.UcenterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@Slf4j
public class AsyncTaskClient {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private IDClient idClient;

    @Autowired
    @Qualifier("guliExecutor")
    private Executor executor;

    //@Async("guliExecutor")
    public CompletableFuture<JSONObject> getMemberById(String memberId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("开始获取用户信息");
            Result member = ucenterClient.getMemberById(memberId);
            AssertUtils.BooleanIsTrue(member.getIsSuccess(), member.getCode(), member.getMessage());
            log.info(member.getData().toString());
            return (JSONObject) JSON.toJSON(member.getData());
        }, executor);
    }

    //@Async("guliExecutor")
    public CompletableFuture<JSONObject> getCourseById(String courseId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("开始获取课程信息");
            Result result = courseClient.getInfo(courseId);
            AssertUtils.BooleanIsTrue(result.getIsSuccess(), result.getCode(), result.getMessage());
            log.info(result.getData().toString());
            return (JSONObject) JSON.toJSON(result.getData());
        }, executor);
    }

    //@Async("guliExecutor")
    public CompletableFuture<JSONObject> getOrderId() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("获取订单号");
            Result result = idClient.generatorID("ORDER");
            AssertUtils.BooleanIsTrue(result.getIsSuccess(), result.getCode(), "订单号生成失败");
            log.info(result.getData().toString());
            return (JSONObject) JSON.toJSON(result.getData());
        }, executor);
    }
}
