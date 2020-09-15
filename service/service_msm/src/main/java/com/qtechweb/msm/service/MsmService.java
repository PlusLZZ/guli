package com.qtechweb.msm.service;

import java.util.Map;

public interface MsmService {

    /* 发送短信 */
    Map<String, Object> send(String phone, Map<String, Object> params);

}
