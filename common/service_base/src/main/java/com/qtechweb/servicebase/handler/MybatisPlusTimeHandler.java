package com.qtechweb.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 * 全局配置时间字段自动填充
 * */
@Slf4j
@Component
public class MybatisPlusTimeHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        /*这里填充的是实体类的属性名而不是数据库字段名*/
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
