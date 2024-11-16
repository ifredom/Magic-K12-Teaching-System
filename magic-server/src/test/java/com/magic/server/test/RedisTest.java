package com.magic.server.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("key1", "张三1");
        String value = (String) redisTemplate.opsForValue().get("key1");
        System.out.println(value);
        assertEquals("张三1", value);
    }
}
