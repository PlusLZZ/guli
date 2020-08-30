package com.qtechweb.commonutils.result;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(value = "分页对象", description = "封装分页需要的数据")
@Data
@Accessors(chain = true)
public class PageUtils<T> {
    @ApiModelProperty("总记录数")
    private Long total;
    @ApiModelProperty(value = "每页记录数")
    private Long size;
    @ApiModelProperty(value = "当前页码")
    private Long current;
    @ApiModelProperty(value = "总页数")
    private Long pageCount;
    @ApiModelProperty(value = "是否有上一页")
    private Boolean hasPrevious;
    @ApiModelProperty(value = "是否有下一页")
    private Boolean hasNext;
    @ApiModelProperty(value = "当前页数据")
    private T data;

    public static PageUtils page(Page page) {
        Double pageCount = Math.ceil(page.getTotal() / (double) page.getSize());
        return new PageUtils().setTotal(page.getTotal()).setSize(page.getSize()).setCurrent(page.getCurrent())
                .setPageCount(pageCount.longValue()).setHasPrevious(page.hasPrevious()).setHasNext(page.hasNext()).setData(page.getRecords());
    }

}
