package com.qtechweb.statistics.client;

import com.qtechweb.commonutils.result.Result;
import com.qtechweb.statistics.client.circuit.UcenterClientCircuit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "ucenter", fallback = UcenterClientCircuit.class)
public interface UcenterClient {

    @GetMapping(path = "/ucenter/info/countRegister/{day}")
    Result countRegister(@PathVariable("day") String day);

}
