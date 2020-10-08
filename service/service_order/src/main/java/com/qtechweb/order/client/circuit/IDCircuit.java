package com.qtechweb.order.client.circuit;

import com.qtechweb.commonutils.result.Result;
import com.qtechweb.order.client.IDClient;
import org.springframework.stereotype.Component;

@Component
public class IDCircuit implements IDClient {

    @Override
    public Result generatorID(String type) {
        return Result.fail(50000, "生成订单号失败!!!");
    }
}
