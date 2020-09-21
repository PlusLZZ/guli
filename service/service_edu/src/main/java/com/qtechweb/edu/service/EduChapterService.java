package com.qtechweb.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.edu.entity.EduChapter;
import com.qtechweb.edu.entity.chapter.ChapterVo;

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

    /* 2.通过id获取章节列表包括小节列表的视频id以及收费情况 */
    List<ChapterVo> getChapterFront(String courseId);
}
