package com.qtechweb.oss.utils;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class AliYunOssUpload {

    /*
     * 上传文件流
     * 1.文件
     * 2.上传文件位置
     * 返回: url访问路径
     * */
    public static String uploadFileStream(MultipartFile file, String path) {
        OSS build = new OSSClientBuilder().build(ConstantOssSet.END_POINT, ConstantOssSet.ACCESS_KEY_ID, ConstantOssSet.ACCESS_KEY_SECRET);
        InputStream inputStream;
        String objectName = path + rename(file.getOriginalFilename());
        String url = null;
        try {
            inputStream = file.getInputStream();
            build.putObject(ConstantOssSet.BUCKET_NAME, objectName, inputStream);
            inputStream.close();
            url = getUrlForObjectName(build, objectName);
        } catch (IOException e) {
        } finally {
            build.shutdown();
        }
        return url;
    }

    /*
     * 通过objectName获取图片url
     * */
    public static String getUrlForObjectName(OSS oss, String objectName) {
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 365 * 10);
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(ConstantOssSet.BUCKET_NAME, objectName, HttpMethod.GET);
        urlRequest.setExpiration(expiration);
        URL url = oss.generatePresignedUrl(urlRequest);
        return url.toString();
    }

    /*
     * 命名策略
     * */
    public static String rename(String fileName) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/");
        return format.format(date) + UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));
    }
}
