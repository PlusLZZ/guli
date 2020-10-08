package com.qtechweb.edu.controller;


import com.qtechweb.commonutils.enums.AuthEnum;
import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.result.PageUtils;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.commonutils.utils.JwtUtils;
import com.qtechweb.edu.entity.EduComment;
import com.qtechweb.edu.service.EduCommentService;
import com.qtechweb.edu.service.impl.EduCommentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author lzz
 * @since 2020-09-21
 */
@Slf4j
@Api(value = "评论接口")
@RestController
@RequestMapping("/edu/comment")
public class EduCommentController {

    @Resource(type = EduCommentServiceImpl.class)
    private EduCommentService commentService;

    @ApiOperation("评论分页")
    @GetMapping(path = "/page/{size}/{current}/{courseId}")
    public Result page(@PathVariable("size") Long size,
                       @PathVariable("current") Long current,
                       @PathVariable("courseId") String courseId) throws ExecutionException, InterruptedException {
        CompletableFuture<PageUtils> future = commentService.pageByComment(size, current, courseId);
        return Result.success(future.get());
    }

    @ApiOperation("添加评论")
    @PostMapping("/add")
    public Result addComment(@RequestBody EduComment comment,
                             HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        AssertUtils.StringNotNull(memberId, AuthEnum.NOT_LOGGED);
        commentService.addComment(comment, memberId);
        return Result.success();
    }

    @ApiOperation("删除自己的评论")
    @DeleteMapping("/del/{memberId}/{commentId}")
    public Result deleteComment(@PathVariable("memberId") String memberId,
                                @PathVariable("commentId") String commentId,
                                HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        AssertUtils.StringNotNull(id, AuthEnum.NOT_LOGGED);
        AssertUtils.BooleanIsTrue(id.equals(memberId), "此评论不是该用户所有");
        boolean b = commentService.removeById(commentId);
        AssertUtils.BooleanIsTrue(b, "删除失败,请重试");
        return Result.success();
    }

}

