/**
 * 采用 Spring Data Redis 操作 Redis，底层使用 Redisson 作为客户端
 */
package com.magic.framework.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;

@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 默认过期时长为24小时，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;
    /**
     * 验证码 5分钟过期时间
     */
    public final static long CAPTCHA_EXPIRE = 60 * 5L;
    /**
     * 过期时长为1小时，单位：秒
     */
    public final static long HOUR_ONE_EXPIRE = 60 * 60 * 1L;
    /**
     * 过期时长为6小时，单位：秒
     */
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;
    /**
     * 过期时长为1月，单位：秒
     */
    public final static long MONTH_ONE_EXPIRE = 60 * 60 * 24 * 30L;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1L;

    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @param expire
     */
    public void set(String key, Object value, long expire) {
        if (!StrUtil.isBlank(key) && value != null) {
            redisTemplate.opsForValue().set(key, value);
        }
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 保存数据 默认一天过期
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        set(key, value, NOT_EXPIRE);
    }

    /**
     * 获取数据 并修改过期时间
     *
     * @param key
     * @param expire
     * @return
     */
    public Object get(String key, long expire) {
        Object value = redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
        return value;
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    /**
     * 获取数据
     *
     * @param key
     * @param clazz
     * @return
     */

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }

    /**
     * 匹配查询 例如 test* 查询以test开头的全部key
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 匹配删除 test* 删除以test开头的全部key
     *
     * @param pattern
     * @return
     */
    public void deleteByPattern(String pattern) {
        redisTemplate.delete(keys(pattern));
    }

    /**
     * 删除指定的key
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除
     *
     * @param keys
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 获取指定的键值对
     *
     * @param key
     * @param field
     * @return
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取全部的键值对
     *
     * @param key
     * @return
     */
    public Map<String, Object> hGetAll(String key) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    public Set<Object> hGetField(String key) {
        Set<Object> fieldSet = redisTemplate.opsForHash().keys(key);
        return fieldSet;
    }

    /**
     * 批量保存 默认一天过期
     *
     * @param key
     * @param map
     */
    public void hMSet(String key, Map<String, Object> map) {
        hMSet(key, map, NOT_EXPIRE);
    }

    /**
     * 批量保存 自定义过期时间
     *
     * @param key
     * @param map
     * @param expire
     */
    public void hMSet(String key, Map<String, Object> map, long expire) {
        redisTemplate.opsForHash().putAll(key, map);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 新增hashMap值 默认一天过期
     *
     * @param key
     * @param field
     * @param value
     */
    public void hSet(String key, String field, Object value) {
        hSet(key, field, value, NOT_EXPIRE);
    }

    /**
     * 新增hashMap值 自定义过期时间
     *
     * @param key
     * @param field
     * @param value
     * @param expire
     */
    public void hSet(String key, String field, Object value, long expire) {
        redisTemplate.opsForHash().put(key, field, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 修改过期时间
     *
     * @param key
     * @param expire
     */
    public void expire(String key, long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 删除变量中的键值对
     *
     * @param key
     * @param fields
     */
    public void hDel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 在左边添加元素值 默认一天过期
     *
     * @param key
     * @param value
     */
    public void leftPush(String key, Object value) {
        leftPush(key, value, NOT_EXPIRE);
    }

    /**
     * 在左边添加元素值 自定义过期时间
     *
     * @param key
     * @param value
     * @param expire
     */
    public void leftPush(String key, Object value, long expire) {
        redisTemplate.opsForList().leftPush(key, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 移除集合中右边的元素
     *
     * @param key
     * @return
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 新增 Set 中新增成员
     *
     * @param key
     * @param value
     */
    public void addToSet(String key, long expire, String... value) {
        stringRedisTemplate.opsForSet().add(key, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 删除 Set 中对应的成员
     *
     * @param key
     * @param value
     */
    public void removeFromSet(String key, String value) {
        stringRedisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 获取 Set 全部的成员
     *
     * @param key
     * @return
     */
    public Set<String> membersOfSet(String key) {
        Set<String> members = stringRedisTemplate.opsForSet().members(key);
        return members;
    }

    /**
     * 获取 Set 是否包含成员
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setContainsWarn(String key, String value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 添加 到 set
     *
     * @param key
     * @param value
     * @param expire
     */
    public void addSet(String key, Object value, long expire) {
        redisTemplate.opsForSet().add(key, value);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 根据 key 查询 set
     *
     * @param key
     */
    public Set<Object> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 删除 Set 中对应的成员
     *
     * @param key
     * @param value
     */
    public void removeOfSet(String key, Object value) {
        stringRedisTemplate.opsForSet().remove(key, value);
    }

    public long inc(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public List<Object> getAllByKey(String keysPattern) {
        Set<String> keys = redisTemplate.keys(keysPattern);
        return redisTemplate.opsForValue().multiGet(keys);
    }
    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
    /**
     * 对象转成JSON字符串
     */
    private String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
