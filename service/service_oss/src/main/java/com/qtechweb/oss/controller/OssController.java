package com.qtechweb.oss.controller;

import com.qtechweb.commonutils.result.ResMap;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.oss.service.OssService;
import com.qtechweb.oss.service.impl.OssServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 * oss文件上传接口
 * </p>
 *
 * @author lzz
 * @since 2020-08-29
 */
@Slf4j
@Api(value = "讲师相关文件上传接口")
@CrossOrigin
@RestController
@RequestMapping("/oss/edu-teacher")
public class OssController {

    @Resource(type = OssServiceImpl.class)
    private OssService ossService;

    /*1.上传eduTeacher头像*/
    @PostMapping(path = "/avatar")
    public Result uploadEduTeacherAvatar(MultipartFile avatar) {
        String url;
        try {
            url = ossService.uploadEduTeacherAvatar(avatar);
            return Result.success(ResMap.create().setKeyValue("url", url));
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
}
