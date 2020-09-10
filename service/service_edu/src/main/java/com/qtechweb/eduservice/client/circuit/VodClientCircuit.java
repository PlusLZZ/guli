package com.qtechweb.eduservice.client.circuit;

import com.qtechweb.commonutils.result.Result;
import com.qtechweb.eduservice.client.VodClient;
import org.springframework.stereotype.Component;


/*
 * 熔断器
 * */

@Component
public class VodClientCircuit implements VodClient {
    @Override
    public Result deleteVideoById(String id) {
        return Result.fail();
    }
}
