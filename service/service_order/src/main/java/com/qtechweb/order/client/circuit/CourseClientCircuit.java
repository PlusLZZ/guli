package com.qtechweb.order.client.circuit;

import com.qtechweb.commonutils.result.Result;
import com.qtechweb.order.client.CourseClient;
import org.springframework.stereotype.Component;

@Component
public class CourseClientCircuit implements CourseClient {
    @Override
    public Result getInfo(String courseId) {
        return Result.fail(50000, "课程信息获取错误,请重试!!!");
    }
}
