package com.qtechweb.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@ApiModel(value = "ChapterVo", description = "课程章节前端操作对象")
@Accessors(chain = true)
public class ChapterVo {

    @ApiModelProperty(value = "章节ID")
    private String id;

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "小节列表")
    private List<VideoVo> children;
}
