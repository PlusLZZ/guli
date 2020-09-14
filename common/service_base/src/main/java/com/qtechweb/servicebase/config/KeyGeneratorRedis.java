package com.qtechweb.servicebase.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtechweb.servicebase.utils.BeanHelper;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("redisKey")
public class KeyGeneratorRedis implements KeyGenerator {

    private final static int NO_PARAM_KEY = 0;
    private String keyPrefix = "guli";// key前缀，用于区分不同项目的缓存，建议每个项目单独设置

    @Override
    public Object generate(Object target, Method method, Object... params) {
        char sp = ':';
        StringBuilder strBuilder = new StringBuilder(30);
        strBuilder.append(keyPrefix);
        strBuilder.append(sp);
        // 类名
        strBuilder.append(target.getClass().getSimpleName());
        strBuilder.append(sp);
        // 方法名
        strBuilder.append(method.getName());
        strBuilder.append(sp);
        if (params.length > 0) {
            // 参数值
            for (Object object : params) {
                if (BeanHelper.isSimpleValueType(object.getClass())) {
                    strBuilder.append(object);
                } else {
                    strBuilder.append(JSON.toJSONString(object, SerializerFeature.WriteMapNullValue).hashCode());
                }
            }
        } else {
            strBuilder.append(NO_PARAM_KEY);
        }
        System.out.println("------------------" + strBuilder.toString());
        return strBuilder.toString();
    }
}
