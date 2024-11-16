package com.magic.admin.test;

import com.magic.module.sys.model.UserAndOrderModel;
import com.magic.module.sys.service.ISysUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private ISysUserService sysUserService;

    @Test
    public void testGetUser() {
        UserAndOrderModel u = sysUserService.getUnionUserInfo(1);
        System.out.println(u);
        Assert.assertEquals(u.getUsername(), "Jack Ma");
    }
}