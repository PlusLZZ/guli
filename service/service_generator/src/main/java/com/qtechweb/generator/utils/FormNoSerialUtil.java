package com.qtechweb.generator.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 单号生成工具类
 *
 * @author lzz
 */
public class FormNoSerialUtil {

    /*
     * 得到时间戳
     * */
    public static String getFormat(FormNoTypeEnum typeEnum) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(typeEnum.getDatePattern());
        return formatter.format(LocalDateTime.now());
    }

    /*
     * 生成单号前缀
     * */
    public static String getFormNoPrefix(FormNoTypeEnum typeEnum) {
        StringBuilder sb = new StringBuilder();
        sb.append(typeEnum.getPrefix()).append(getFormat(typeEnum));
        return sb.toString();
    }

    /**
     * 构建流水号缓存Key
     *
     * @param typeEnum 枚举
     * @return 流水号缓存Key
     */
    public static String getCacheKey(FormNoTypeEnum typeEnum) {
        return FormNoConstants.SERIAL_CACHE_PREFIX.concat(typeEnum.name()).concat(getFormat(typeEnum));
    }

    /**
     * 补全随机数
     *
     * @param serialWithPrefix 单号前缀
     * @param formNoTypeEnum   单号生成枚举
     * @author mengqiang
     * @date 2019/1/1
     */
    public static String completionRandom(String serialWithPrefix, FormNoTypeEnum formNoTypeEnum) {
        StringBuffer sb = new StringBuffer(serialWithPrefix);
        //随机数长度
        int length = formNoTypeEnum.getRandomLength();
        if (length > 0) {
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                //十以内随机数补全
                sb.append(random.nextInt(10));
            }
        }
        return sb.toString();
    }

    /**
     * 补全流水号
     *
     * @param serialPrefix      单号前缀
     * @param incrementalSerial 当天自增流水号
     * @author lzz
     */
    public static String completionSerial(String serialPrefix, Long incrementalSerial,
                                          FormNoTypeEnum formNoTypeEnum) {
        StringBuilder sb = new StringBuilder(serialPrefix);

        //需要补0的长度=流水号长度 -当日自增计数长度
        int length = formNoTypeEnum.getSerialLength() - String.valueOf(incrementalSerial).length();
        //补零
        for (int i = 0; i < length; i++) {
            sb.append("0");
        }
        //redis当日自增数
        sb.append(incrementalSerial);
        return sb.toString();
    }

}
