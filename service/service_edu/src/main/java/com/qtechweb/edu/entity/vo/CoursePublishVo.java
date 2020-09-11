package com.qtechweb.edu.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@ApiModel(value = "CoursePublishVo", description = "课程发布展示对象")
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private BigDecimal price;
    private Integer lessonNum;
    private String description;
    private String name;
    private String oneSubject;
    private String twoSubject;

}
