package com.qtechweb.msm.controller;

import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.commonutils.utils.RandomUtil;
import com.qtechweb.msm.service.MsmService;
import com.qtechweb.msm.service.impl.MsmServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 短信接口
 * </p>
 *
 * @author lzz
 * @since 2020-09-14
 */
@Slf4j
@Api(value = "短信接口")
@CrossOrigin
@RestController
@RequestMapping("/msm")
public class MsmController {

    @Resource(type = MsmServiceImpl.class)
    private MsmService msmService;

    @ApiOperation("发送短信")
    @GetMapping(path = "/send/{phone}")
    public Result sendMessage(@PathVariable("phone") String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", RandomUtil.getSixBitRandom());
        Map<String, Object> send = msmService.send(phone, map);
        AssertUtils.MapNotNull(send, "发送短信失败");
        return Result.success(send);
    }


}
