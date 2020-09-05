package com.qtechweb.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.eduservice.entity.EduChapter;
import com.qtechweb.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-09-01
 */
public interface EduChapterService extends IService<EduChapter> {

    /*1.通过id获取章节列表大纲*/
    List<ChapterVo> getChapterLine(String courseId);
}
