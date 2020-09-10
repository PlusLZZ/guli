package com.qtechweb.eduservice.client;

import com.qtechweb.commonutils.result.Result;
import com.qtechweb.eduservice.client.circuit.VodClientCircuit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "vod", fallback = VodClientCircuit.class)
@Component
public interface VodClient {

    /*定义调用的方法路径(必须要全路径)*/
    @DeleteMapping("/vod/delete/{id}")
    Result deleteVideoById(@PathVariable("id") String id);
}
