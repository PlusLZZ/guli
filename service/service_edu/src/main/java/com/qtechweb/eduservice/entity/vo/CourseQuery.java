package com.qtechweb.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "CourseQuery", description = "课程条件查询")
@Data
public class CourseQuery {
    @ApiModelProperty(value = "模糊查询姓名")
    private String name;
    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String state;
    @ApiModelProperty(value = "查询起始时间", example = "2020-08-09 10:10:10")
    private String begin;
    @ApiModelProperty(value = "查询结束时间", example = "2020-08-09 10:10:10")
    private String end;
}
