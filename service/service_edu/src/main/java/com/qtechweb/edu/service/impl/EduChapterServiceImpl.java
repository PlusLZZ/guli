package com.qtechweb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.edu.entity.EduChapter;
import com.qtechweb.edu.entity.EduVideo;
import com.qtechweb.edu.entity.chapter.ChapterVo;
import com.qtechweb.edu.entity.chapter.VideoVo;
import com.qtechweb.edu.mapper.EduChapterMapper;
import com.qtechweb.edu.service.EduChapterService;
import com.qtechweb.edu.service.EduVideoService;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(cacheNames = {"getChapterFront"}, key = "#courseId", unless = "#result==null")
    @Override
    public List<ChapterVo> getChapterFront(String courseId) {
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
                    vos.add(new VideoVo().setId(vo.getId()).setTitle(vo.getTitle()).setSort(vo.getSort()).setIsFree(vo.getIsFree()).setVideoSourceId(vo.getVideoSourceId()));
                }
            });
            chapterVo.setChildren(vos);
            chapterVos.add(chapterVo);
        });
        return chapterVos;
    }
}
