package com.qtechweb.edu.client;

import com.qtechweb.commonutils.result.Result;
import com.qtechweb.edu.client.circuit.UcenterClientCircuit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ucenter", fallback = UcenterClientCircuit.class)
@Component
public interface UcenterClient {


    @GetMapping("/ucenter/member/memberInfo/{id}")
    Result getMemberById(@PathVariable("id") String memberId);

}
