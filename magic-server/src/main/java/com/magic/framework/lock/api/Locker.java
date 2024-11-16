package com.magic.framework.lock.api;


import lombok.Builder;
import lombok.Data;

import java.util.concurrent.ScheduledFuture;

/**
 * 作用: 封装锁的信息,包含 key键值，以及唯一的token值，还有对应的定时任务
 *
 * @author ifredom
 * @version v0.0.1
 * @date 2024/09/20 12:00
 * @email 1950735817@qq.com
 */
@Data
@Builder
public class Locker {
    /**
     * 锁定的key
     */
    private String key;
    /**
     * 用于检查是否是这个锁，防止误删
     */
    private String token;
    /**
     * 每个锁都有一个线程去后台续约
     */
    private ScheduledFuture<?> schedulerFuture;
}
