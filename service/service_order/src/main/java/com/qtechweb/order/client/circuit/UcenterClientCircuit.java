package com.qtechweb.order.client.circuit;

import com.qtechweb.commonutils.result.Result;
import com.qtechweb.order.client.UcenterClient;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientCircuit implements UcenterClient {
    @Override
    public Result getMemberById(String memberId) {
        return Result.fail(50000, "用户信息服务链路出现问题,请刷新");
    }
}
