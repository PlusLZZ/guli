package com.qtechweb.edu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(value = "条件查询对象TeacherQuery", description = "讲师条件查询")
@Data
@Accessors(chain = true)
public class TeacherQuery {
    @ApiModelProperty(value = "模糊查询姓名")
    private String name;
    @ApiModelProperty(value = "头衔 1 高级讲师 2 首席讲师")
    private Integer level;
    @ApiModelProperty(value = "查询起始时间", example = "2020-08-09 10:10:10")
    private String begin;
    @ApiModelProperty(value = "查询结束时间", example = "2020-08-09 10:10:10")
    private String end;
}
