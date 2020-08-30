package com.qtechweb.commonutils.result;


import java.util.HashMap;

/*
 * 创建一个HashMap的工具类用来扩展Result的使用
 * */
public class ResMap extends HashMap<String, Object> {

    public static ResMap create() {
        return new ResMap();
    }

    public ResMap setKeyValue(String key, Object value) {
        this.put(key, value);
        return this;
    }

}
