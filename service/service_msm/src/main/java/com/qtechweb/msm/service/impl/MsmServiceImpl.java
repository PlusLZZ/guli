package com.qtechweb.msm.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.exception.DefaultException;
import com.qtechweb.msm.service.MsmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MsmServiceImpl implements MsmService {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /* 阿里云短信发送实现 */
    @Override
    public Map<String, Object> send(String phone, Map<String, Object> params) {
        AssertUtils.BooleanIsTrue(!redisTemplate.hasKey(phone), "一分钟之内不能重复发送");
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FzqurskcBUFRiVfHKfD", "oXj8FXiBpXPHIcbA1IO2e80Fu1EnWZ");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        /* 设置自定义参数 */
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "谷粒学院");
        request.putQueryParameter("TemplateCode", "SMS_202547473");
        request.putQueryParameter("TemplateParam", JSON.toJSONString(params));
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
            log.info(response.getData());
            log.info(String.valueOf(response.getHttpStatus()));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        if (response.getHttpStatus() != 200) {
            throw new DefaultException(50000, "发送验证码失败");
        }
        if (response.getData().indexOf("触发小时级流控") >= 0) {
            throw new DefaultException(50000, "同一个手机号一个小时最多只能发送五次验证码!!!!");
        }
        redisTemplate.opsForValue().setIfAbsent(phone, params.get("code").toString(), 60, TimeUnit.SECONDS);
        params.put("time", 60);
        params.put("phone", phone);
        return params;
    }
}
