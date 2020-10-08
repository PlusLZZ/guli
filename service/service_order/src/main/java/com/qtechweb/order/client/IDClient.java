package com.qtechweb.order.client;


import com.qtechweb.commonutils.result.Result;
import com.qtechweb.order.client.circuit.IDCircuit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "generator", fallback = IDCircuit.class)
public interface IDClient {

    @GetMapping("/generator/id/{type}")
    Result generatorID(@PathVariable("type") String type);

}
