package com.qtechweb.order.client;

import com.qtechweb.commonutils.result.Result;
import com.qtechweb.order.client.circuit.CourseClientCircuit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "edu", fallback = CourseClientCircuit.class)
public interface CourseClient {

    @GetMapping(path = "/edu/course/getInfo/{courseId}")
    Result getInfo(@PathVariable("courseId") String courseId);

}
