package com.magic.config;

import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.magic.framework.common.RedisJsonSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;


/**
 * 作用: Redis 配置类
 * 目的：使用自己定义的 RedisTemplate Bean
 * @author ifredom
 * @date 2024/9/20 上午11:43
 * @version v0.0.1
 * @email 1950735817@qq.com
 */
@Configuration
@EnableCaching
public class RedisAutoConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建 RedisTemplate 对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // 使用 String 序列化方式，序列化 KEY 。
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        // 使用 JSON 序列化方式（库是 Jackson ），序列化 VALUE 。
        // redisTemplate.setValueSerializer(buildRedisSerializer());
        // redisTemplate.setHashValueSerializer(buildRedisSerializer());

        //  使用 JSON 序列化方式（库是 FastJson ），序列化 VALUE (自定义类实现)
        redisTemplate.setValueSerializer(new RedisJsonSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(new RedisJsonSerializer<>(Object.class));

        // 设置 RedisConnection 工厂。😈 它就是实现多种 Java Redis 客户端接入的秘密工厂。感兴趣的胖友，可以自己去撸下。
        redisTemplate.setConnectionFactory(factory);

        return redisTemplate;
    }

    public static RedisSerializer<?> buildRedisSerializer() {
        RedisSerializer<Object> json = RedisSerializer.json();
        // 解决 LocalDateTime 的序列化
        ObjectMapper objectMapper = (ObjectMapper) ReflectUtil.getFieldValue(json, "mapper");
        objectMapper.registerModules(new JavaTimeModule());
        return json;

    }

}
