package com.qtechweb.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.commonutils.result.PageUtils;
import com.qtechweb.edu.entity.EduComment;

import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-09-21
 */
public interface EduCommentService extends IService<EduComment> {

    /* 主页评论分页 */
    CompletableFuture<PageUtils> pageByComment(Long size, Long current, String courseId);

    /* 添加评论 */
    void addComment(EduComment comment, String memberId);

}
