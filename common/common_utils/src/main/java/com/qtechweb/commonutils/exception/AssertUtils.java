package com.qtechweb.commonutils.exception;


import com.qtechweb.commonutils.enums.DefaultEnum;
import com.qtechweb.commonutils.exce.IDefaultEnum;

import java.util.Collection;
import java.util.Map;

public class AssertUtils {

    /*
     * 断言对象不为空
     * */
    public static void ObjectNotNull(Object obj) {
        if (null == obj) {
            throw new DefaultException(DefaultEnum.OBJECT_IS_NULL);
        }
    }

    public static void ObjectNotNull(Object obj, String message) {
        if (null == obj) {
            throw new DefaultException(50000, message);
        }
    }

    /*
     * 断言字符串不为空
     * */
    public static void StringNotNull(CharSequence s) {
        if (null == s || s.length() == 0) {
            throw new DefaultException(DefaultEnum.STRING_IS_NULL);
        }
    }

    public static void StringNotNull(CharSequence s, String message) {
        if (null == s || s.length() == 0) {
            throw new DefaultException(50000, message);
        }
    }

    /*
     * 断言List不为空
     * */
    public static void ListNotNull(Collection ct) {
        if (null == ct || ct.isEmpty()) {
            throw new DefaultException(DefaultEnum.LIST_IS_NULL);
        }
    }

    public static void ListNotNull(Collection ct, String message) {
        if (null == ct || ct.isEmpty()) {
            throw new DefaultException(50000, message);
        }
    }

    /*
     * 断言map不为空
     * */
    public static void MapNotNull(Map map) {
        if (null == map || map.isEmpty()) {
            throw new DefaultException(DefaultEnum.MAP_IS_NULL);
        }
    }

    public static void MapNotNull(Map map, String message) {
        if (null == map || map.isEmpty()) {
            throw new DefaultException(50000, message);
        }
    }

    /*
     * 断言判断为true
     * */
    public static void BooleanIsTrue(boolean bool, IDefaultEnum denum) {
        if (!bool) {
            throw new DefaultException(denum);
        }
    }

    public static void BooleanIsTrue(boolean bool, String message) {
        if (!bool) {
            throw new DefaultException(50000, message);
        }
    }

    public static void BooleanIsTrue(boolean bool, int code, String message) {
        if (!bool) {
            throw new DefaultException(code, message);
        }
    }
}
