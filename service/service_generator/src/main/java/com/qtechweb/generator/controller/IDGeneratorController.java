package com.qtechweb.generator.controller;

import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.result.ResMap;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.generator.service.IDGeneratorService;
import com.qtechweb.generator.service.impl.IDGeneratorServiceImpl;
import com.qtechweb.generator.utils.FormNoTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * ID生成器
 * </p>
 *
 * @author lzz
 * @since 2020-09-14
 */
@Slf4j
@Api(value = "ID生成器")
@RestController
@RequestMapping("/generator/id")
public class IDGeneratorController {

    @Resource(type = IDGeneratorServiceImpl.class)
    private IDGeneratorService generatorService;

    @ApiOperation("生成分布式ID")
    @GetMapping("/{type}")
    public Result generatorID(@PathVariable("type") String type) {
        FormNoTypeEnum typeEnum = FormNoTypeEnum.valueOf(type);
        AssertUtils.ObjectNotNull(type, "ID类型选择错误");
        String formNo = generatorService.generateFormNo(typeEnum);
        AssertUtils.StringNotNull(formNo, "错误,请重试!!!");
        AssertUtils.BooleanIsTrue(formNo.length() == 19, "生成单号异常!");
        return Result.success(ResMap.create().setKeyValue("ID", formNo));
    }

}
