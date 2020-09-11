package com.qtechweb.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.edu.client.VodClient;
import com.qtechweb.edu.entity.EduVideo;
import com.qtechweb.edu.mapper.EduVideoMapper;
import com.qtechweb.edu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-09-01
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Transactional
    @Override
    public Boolean deleteVideoById(String id) {
        EduVideo eduVideo = getById(id);
        AssertUtils.StringNotNull(eduVideo.getVideoSourceId(), "无此视频ID");
        Result result = vodClient.deleteVideoById(eduVideo.getVideoSourceId());
        AssertUtils.BooleanIsTrue(result.getIsSuccess(), "视频删除出错: " + id);
        return removeById(id);
    }
}
