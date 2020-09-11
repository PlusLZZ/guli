package com.qtechweb.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.edu.entity.EduVideo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-09-01
 */
public interface EduVideoService extends IService<EduVideo> {

    /*删除小节时删除视频*/
    Boolean deleteVideoById(String id);

}
