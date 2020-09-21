package com.qtechweb.commonutils.result;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

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

    @ApiModelProperty(value = "可选:展示的页码")
    private Long[] pages;

    @ApiModelProperty(value = "当前页数据")
    private T data;

    public static PageUtils page(Page page) {
        Double pageCount = Math.ceil(page.getTotal() / (double) page.getSize());
        Long current = page.getCurrent();
        if (current <= 0) {
            current = 1l;
        } else if (current > pageCount) {
            current = pageCount.longValue();
        }

        return new PageUtils().setTotal(page.getTotal()).setSize(page.getSize()).setCurrent(current)
                .setPageCount(pageCount.longValue()).setHasPrevious(page.hasPrevious()).setHasNext(page.hasNext()).setData(page.getRecords());
    }

    /* 提供自定义显示的 */
    public static PageUtils page(Page page, Integer length) {
        Double pageCount = Math.ceil(page.getTotal() / (double) page.getSize());
        Long current = page.getCurrent();
        if (current <= 0) {
            current = 1l;
        } else if (current > pageCount) {
            current = pageCount.longValue();
        }
        Long[] longs = resolvePages(length, current, pageCount.longValue());
        return new PageUtils().setTotal(page.getTotal()).setSize(page.getSize()).setCurrent(current)
                .setPageCount(pageCount.longValue()).setHasPrevious(page.hasPrevious())
                .setHasNext(page.hasNext()).setData(page.getRecords()).setPages(longs);
    }

    /*
     根据页码来选择
    *   使用奇数,比如5,7,9
    *  */
    public static Long[] resolvePages(Integer length, Long current, Long pageCount) {
        List<Long> longs = new ArrayList<>();
        length = length / 2;
        Long start = current - length;
        Long end = current + length;
        if (start <= 0) {
            start = 1l;
        }
        if (end > pageCount) {
            end = pageCount;
        }
        for (int i = start.intValue(); i <= end.intValue(); i++) {
            longs.add(Long.valueOf(String.valueOf(i)));
        }
        Long[] longs1 = new Long[longs.size()];
        return longs.toArray(longs1);
    }

}
