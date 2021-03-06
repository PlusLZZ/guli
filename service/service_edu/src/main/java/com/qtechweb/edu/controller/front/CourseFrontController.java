package com.qtechweb.edu.controller.front;

import com.qtechweb.commonutils.result.ResMap;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.edu.client.OrderClient;
import com.qtechweb.edu.entity.frontvo.CourseFrontVo;
import com.qtechweb.edu.service.EduChapterService;
import com.qtechweb.edu.service.EduCourseService;
import com.qtechweb.edu.service.impl.EduChapterServiceImpl;
import com.qtechweb.edu.service.impl.EduCourseServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前台课程接口
 * </p>
 *
 * @author lzz
 * @since 2020-09-19
 */
@Slf4j
@Api(value = "前台课程接口")
@RestController
@RequestMapping("/edu/front/course")
public class CourseFrontController {

    @Resource(type = EduCourseServiceImpl.class)
    private EduCourseService courseService;

    @Resource(type = EduChapterServiceImpl.class)
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @ApiOperation("前台课程分页")
    @PostMapping(path = "/page/{size}/{current}")
    public Result page(@PathVariable("size") Long size,
                       @PathVariable("current") Long current,
                       @RequestBody(required = false) CourseFrontVo vo) {
        return Result.success(courseService.getCourseFrontList(size, current, vo));
    }

    @ApiOperation("查询课程详情")
    @GetMapping(path = "/info/{id}")
    public Result info(@PathVariable("id") String id, HttpServletRequest request) {
        //String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //log.info(memberId);
        return Result.success(ResMap.create()
                .setKeyValue("courseInfo", courseService.getCourseFrontInfo(id))
                .setKeyValue("chapterInfo", chapterService.getChapterFront(id))
        );
        /*
         * .setKeyValue("isBuy",null==memberId||memberId.isEmpty()?false:orderClient.isBuy(id,memberId).getData())
         * */
    }


}
