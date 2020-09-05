package com.qtechweb.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.eduservice.entity.EduChapter;
import com.qtechweb.eduservice.entity.EduVideo;
import com.qtechweb.eduservice.entity.chapter.ChapterVo;
import com.qtechweb.eduservice.entity.chapter.VideoVo;
import com.qtechweb.eduservice.mapper.EduChapterMapper;
import com.qtechweb.eduservice.service.EduChapterService;
import com.qtechweb.eduservice.service.EduVideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-09-01
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource(type = EduVideoServiceImpl.class)
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterLine(String courseId) {
        QueryWrapper<EduChapter> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", courseId);
        wrapper1.orderByAsc("sort");
        List<EduChapter> chapters = list(wrapper1);
        if (chapters == null || chapters.size() <= 0) {
            return null;
        }
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id", courseId);
        wrapper2.orderByAsc("sort");
        List<EduVideo> videos = videoService.list(wrapper2);
        List<ChapterVo> chapterVos = new ArrayList<>();
        chapters.forEach(cap -> {
            ChapterVo chapterVo = new ChapterVo();
            List<VideoVo> vos = new ArrayList<>();
            chapterVo.setId(cap.getId()).setTitle(cap.getTitle()).setSort(cap.getSort());
            videos.forEach(vo -> {
                if (vo.getChapterId().equals(cap.getId())) {
                    vos.add(new VideoVo().setId(vo.getId()).setTitle(vo.getTitle()).setSort(vo.getSort()));
                }
            });
            chapterVo.setChildren(vos);
            chapterVos.add(chapterVo);
        });
        return chapterVos;
    }
}
