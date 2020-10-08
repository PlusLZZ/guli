package com.qtechweb.generator.service.impl;

import com.qtechweb.generator.service.IDGeneratorService;
import com.qtechweb.generator.utils.FormNoConstants;
import com.qtechweb.generator.utils.FormNoSerialUtil;
import com.qtechweb.generator.utils.FormNoTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class IDGeneratorServiceImpl implements IDGeneratorService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String generateFormNo(FormNoTypeEnum formNoTypeEnum) {
        String formNoPrefix = FormNoSerialUtil.getFormNoPrefix(formNoTypeEnum);
        String cacheKey = FormNoSerialUtil.getCacheKey(formNoTypeEnum);
        Long increment = redisTemplate.opsForValue().increment(cacheKey, 1l);
        redisTemplate.expire(cacheKey, FormNoConstants.DEFAULT_CACHE_DAYS, TimeUnit.DAYS);
        formNoPrefix = FormNoSerialUtil.completionRandom(formNoPrefix, formNoTypeEnum);
        return FormNoSerialUtil.completionSerial(formNoPrefix, increment, formNoTypeEnum);
    }

}
