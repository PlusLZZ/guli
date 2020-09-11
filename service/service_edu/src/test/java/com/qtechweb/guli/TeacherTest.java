package com.qtechweb.guli;

import com.qtechweb.edu.mapper.EduTeacherMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class TeacherTest {
    @Resource
    private EduTeacherMapper mapper;

    @Test
    void content() {
        System.out.println("123");
    }

    @Test
    void testCheckName() {
        System.out.println(mapper.checkOutTeacherByName("测试讲师2"));
    }
}
