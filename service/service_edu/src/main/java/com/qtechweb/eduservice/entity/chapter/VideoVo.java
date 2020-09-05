package com.qtechweb.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@ApiModel(value = "VideoVo", description = "课程小节前端操作对象")
@Accessors(chain = true)
public class VideoVo {

    @ApiModelProperty(value = "小节ID")
    private String id;

    @ApiModelProperty(value = "小节名称")
    private String title;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;
}
