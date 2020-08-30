package com.qtechweb.oss.service.impl;

import com.qtechweb.oss.service.OssService;
import com.qtechweb.oss.utils.AliYunOssUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OssServiceImpl implements OssService {


    @Override
    public String uploadEduTeacherAvatar(MultipartFile file) {
        return AliYunOssUpload.uploadFileStream(file, "edu-teacher/");
    }
}
