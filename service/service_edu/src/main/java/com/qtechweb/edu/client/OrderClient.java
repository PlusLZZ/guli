package com.qtechweb.edu.client;

import com.qtechweb.commonutils.result.Result;
import com.qtechweb.edu.client.circuit.OrderClientCircuit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "order", fallback = OrderClientCircuit.class)
@Component
public interface OrderClient {

    @GetMapping("/order/isBuy/{courseId}/{memberId}")
    Result isBuy(@PathVariable("courseId") String courseId,
                 @PathVariable("memberId") String memberId);

}
