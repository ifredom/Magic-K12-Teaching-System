package com.magic.framework.lock.api;

import java.time.Duration;

/**
 * 作用: 分布式锁接口功能定义
 *
 * @author ifredom
 * @version v0.0.1
 * @date 2024/09/19 23:23
 * @email 1950735817@qq.com
 */
public interface ILock {

    /**
     * 获取锁
     * @param key        锁定的key
     * @param expiration 锁过期时间
     * @return 锁定失败返回null
     */
    Locker acquireLock(String key, Duration expiration);

    /**
     * 释放锁
     * @param locker   锁定的key
     * @return 是否成功
     */
    boolean releaseLock(Locker locker);
}
