package com.magic.admin.test;

import com.magic.framework.common.RedisUtils;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisSerializerTest {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 存储复杂类型。也就是村粗java对象
     */
    @Test
    public void testSetValueWithSerializer() {
        Fater fater = new Fater();
        fater.setName("Tom");
        fater.setAge(25);

        redisUtils.set("obj:fater", fater);

        Fater fater1 = redisUtils.get("obj:fater", Fater.class);
        System.out.println(fater1);
    }

}

@Data
class Fater{
    private String name;
    private int age;
}