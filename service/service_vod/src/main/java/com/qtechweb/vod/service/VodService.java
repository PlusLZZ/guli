package com.qtechweb.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {

    /*上传视频到阿里云*/
    String uploadVideoAli(MultipartFile video);

    /*通过ID删除阿里云视频*/
    Boolean deleteVideoById(String id);
}
