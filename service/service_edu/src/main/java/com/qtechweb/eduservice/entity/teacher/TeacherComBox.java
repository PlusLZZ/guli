package com.qtechweb.eduservice.entity.teacher;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(value = "TeacherComBox对象", description = "讲师列表下拉框")
public class TeacherComBox implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师id")
    private String id;

    @ApiModelProperty(value = "讲师姓名")
    private String name;
}
