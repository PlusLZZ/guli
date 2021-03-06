package com.qtechweb.ucenter.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConstantQq implements InitializingBean {

    @Value("${open.qq.app_id}")
    private String appId;

    @Value("${open.qq.app_secret}")
    private String appSecret;

    @Value("${open.qq.redirect_url}")
    private String redirectUrl;

    public static String APP_ID;
    public static String APP_SECRET;
    public static String REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appId;
        APP_SECRET = appSecret;
        REDIRECT_URL = redirectUrl;
    }
}
