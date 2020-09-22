package com.qtechweb.edu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.result.PageUtils;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.edu.client.UcenterClient;
import com.qtechweb.edu.entity.EduComment;
import com.qtechweb.edu.mapper.EduCommentMapper;
import com.qtechweb.edu.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-09-21
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private UcenterClient ucenterClient;

    @Async("guliExecutor")
    @Override
    public CompletableFuture<PageUtils> pageByComment(Long size, Long current, String courseId) {
        Page<EduComment> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId).orderByDesc("gmt_modified");
        return CompletableFuture.completedFuture(PageUtils.page(page(page, wrapper)));
    }

    @Override
    public void addComment(EduComment comment, String memberId) {
        Result result = ucenterClient.getMemberById(memberId);
        AssertUtils.BooleanIsTrue(result.getIsSuccess(), result.getCode(), result.getMessage());
        JSONObject obj = (JSONObject) JSON.toJSON(result.getData());
        comment.setMemberId(memberId);
        comment.setAvatar(obj.getString("avatar"));
        comment.setNickname(obj.getString("nickname"));
        save(comment);
    }


}
