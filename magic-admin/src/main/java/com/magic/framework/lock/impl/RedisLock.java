package com.magic.framework.lock.impl;

import com.magic.framework.lock.api.Locker;
import com.magic.framework.lock.core.AbstractSimpleILock;
import io.lettuce.core.RedisCommandInterruptedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * 作用: RedisLock 实现类
 *
 * @author ifredom
 * @version v0.0.1
 * @date 2024/09/20 11:57
 * @email 1950735817@qq.com
 */
@Slf4j
public class RedisLock extends AbstractSimpleILock {

    //  lock_script 加锁lua脚本, 释放锁lua脚本, 刷新锁lua脚本
    // tonumber(ARGV[2]) 的作用是将传入的参数（通常是字符串形式的数字）转换为数字。这插入 Redis 的 'SET' 命令中的过期时间参数需要是一个整数值，因此使用 tonumber 来确保传入的参数正确解析为数字
    private static final String LOCK_SCRIPT = "return redis.call('SET', KEYS[1], ARGV[1], 'PX', tonumber(ARGV[2]), 'NX') and true or false";

    private static final String LOCK_RELEASE_SCRIPT = "return redis.call('GET', KEYS[1]) == ARGV[1] and (redis.call('DEL', KEYS[1]) == 1) or false";

    private static final String LOCK_REFRESH_SCRIPT =
            "if redis.call('GET', KEYS[1]) == ARGV[1] then\n" +
                    "    redis.call('PEXPIRE', KEYS[1], tonumber(ARGV[2]))\n" +
                    "    return true\n" +
                    "end\n" +
                    "return false";

    private final RedisScript<Boolean> lockScript =  new DefaultRedisScript<>(LOCK_SCRIPT, Boolean.class);
    private final RedisScript<Boolean> lockReleaseScript = new DefaultRedisScript<>(LOCK_RELEASE_SCRIPT, Boolean.class);
    private final RedisScript<Boolean> lockRefreshScript = new DefaultRedisScript<>(LOCK_REFRESH_SCRIPT, Boolean.class);


    private final StringRedisTemplate stringRedisTemplate;

    public RedisLock(final StringRedisTemplate stringRedisTemplate, TaskScheduler taskScheduler) {
        super(taskScheduler);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private String key (String key) { return "lock:"+ key; }

    @Override
    public String acquire(String key, String token, Duration expiration) {
        Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(key(key), token, expiration);
        log.info("Tried to acquire lock for key {} with token {}. Locked: {}", key, token, locked);
        return Boolean.TRUE.equals(locked) ? token : null;
    }

    @Override
    public boolean release0(Locker lock) {
        String key = lock.getKey();
        String token = lock.getToken();
        Boolean released = stringRedisTemplate.execute(lockReleaseScript, Collections.singletonList(key(key)), token);

        if (released) {
            log.info("删除脚本成功释放了1个锁 key {} token {}", key, token);
        } else {
            log.error("删除脚本释放锁失败 key {}  token {}, released {}", key, token);
        }

        return released;
    }

    @Override
    public boolean refresh(String key, String token, Duration expiration) {
        final List<String> singletonKeyList = Collections.singletonList(key(key));

        boolean refreshed = false;
        try {
            refreshed = Boolean.TRUE.equals(stringRedisTemplate.execute(lockRefreshScript, singletonKeyList, token, String.valueOf(expiration.toMillis())));
            if (refreshed) {
                log.info("刷新脚本更新了过期时间 key {}  token {} expiration: {}", key, token, expiration);
            } else {
                log.info("刷新脚本更新失败 key {} token {} expiration: {}", key, token, expiration);
            }
        } catch (RedisSystemException e) {
            if (e.getCause() != null && (e.getCause() instanceof RedisCommandInterruptedException)) {
                log.error("Refresh script thread interrupted to update expiration for key {} with token {} with expiration: {}", key, token, expiration);
            } else {
                throw e;
            }
        }
        return refreshed;
    }
}
