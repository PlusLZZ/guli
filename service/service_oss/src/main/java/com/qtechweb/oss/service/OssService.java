package com.qtechweb.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OssService {
    String uploadEduTeacherAvatar(MultipartFile file) throws IOException;
}
