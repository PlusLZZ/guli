package com.qtechweb.statistics.client.circuit;

import com.qtechweb.commonutils.result.Result;
import com.qtechweb.statistics.client.UcenterClient;
import org.springframework.stereotype.Component;


@Component
public class UcenterClientCircuit implements UcenterClient {
    @Override
    public Result countRegister(String day) {
        return Result.fail();
    }
}
