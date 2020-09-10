package com.qtechweb.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.commonutils.exception.DefaultException;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.eduservice.client.VodClient;
import com.qtechweb.eduservice.entity.EduVideo;
import com.qtechweb.eduservice.mapper.EduVideoMapper;
import com.qtechweb.eduservice.service.EduVideoService;
import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.isNotBlank(eduVideo.getVideoSourceId())) {
            Result result = vodClient.deleteVideoById(eduVideo.getVideoSourceId());
            if (!result.getIsSuccess())
                throw new DefaultException(50000, "视频删除出错: " + id);

        }
        return removeById(id);
    }
}
