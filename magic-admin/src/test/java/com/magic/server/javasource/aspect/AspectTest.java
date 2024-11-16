package com.magic.admin.javasource.aspect;


import com.magic.module.sys.controller.SysUserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
public class AspectTest {

//    @Autowired
//    private MockMvc mockMvc;

    @Test
    public void testAspect() throws Exception {

        // 测试 /sys/user/addUser接口
//        mockMvc.perform(MockMvcRequestBuilders.get("/sys/user/info"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("ALL user info"));

    }
}
