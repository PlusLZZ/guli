package com.qtechweb.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.qtechweb.vod.service.VodService;
import com.qtechweb.vod.utils.ConstantVodSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VodServiceImpl implements VodService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String uploadVideoAli(MultipartFile video) {
        try {
            String fileName = video.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodSet.ACCESS_KEY_ID, ConstantVodSet.ACCESS_KEY_SECRET,
                    title, fileName, video.getInputStream());
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            return response.getVideoId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleteVideoById(String id) {
        try {
            DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", ConstantVodSet.ACCESS_KEY_ID, ConstantVodSet.ACCESS_KEY_SECRET);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            DeleteVideoResponse response = new DeleteVideoResponse();
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(id);
            response = client.getAcsResponse(request);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getAuth(String id) {
        String playAuth = "";
        if (redisTemplate.hasKey("getAuth:" + id)) {
            return redisTemplate.opsForValue().get("getAuth:" + id);
        }
        try {
            DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", ConstantVodSet.ACCESS_KEY_ID, ConstantVodSet.ACCESS_KEY_SECRET);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response;
            request.setVideoId(id);
            request.setAuthInfoTimeout(3000l);
            response = client.getAcsResponse(request);
            playAuth = response.getPlayAuth();
            redisTemplate.opsForValue().set("getAuth:" + id, playAuth, 3000, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playAuth;
    }

}
