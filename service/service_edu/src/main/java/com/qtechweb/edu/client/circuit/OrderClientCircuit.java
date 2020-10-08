package com.qtechweb.edu.client.circuit;

import com.qtechweb.commonutils.result.Result;
import com.qtechweb.edu.client.OrderClient;
import org.springframework.stereotype.Component;


@Component
public class OrderClientCircuit implements OrderClient {

    @Override
    public Result isBuy(String courseId, String memberId) {
        return Result.success(false);
    }
}
