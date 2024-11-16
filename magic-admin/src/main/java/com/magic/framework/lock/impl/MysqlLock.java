package com.magic.framework.lock.impl;

import com.magic.framework.lock.api.Locker;
import com.magic.framework.lock.core.AbstractSimpleILock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

/**
 * 作用: MySql分布式锁实现
 *
 * @author ifredom
 * @version v0.0.1
 * @date 2024/09/19 23:56
 * @email 1950735817@qq.com
 */
@Slf4j
public class MysqlLock extends AbstractSimpleILock {

    // 添加锁
    public static final String ACQUIRE_FORMATTED_QUERY  = "INSERT ignore INTO distribute_lock (lock_key, token, expire, thread_id) VALUES (?, ?, ?, ?);";
    // 删除指定锁
    public static final String RELEASE_FORMATTED_QUERY = "DELETE FROM distribute_lock WHERE lock_key = ? AND token = ?;";
    // 删除所有过期锁
    public static final String DELETE_EXPIRED_FORMATTED_QUERY = "DELETE FROM distribute_lock WHERE expire<?;";
    // 更新锁的过期时间
    public static final String REFRESH_FORMATTED_QUERY = "UPDATE distribute_lock SET expire=? WHERE lock_key=? AND token=?;";
    private final JdbcTemplate jdbcTemplate;


    public MysqlLock(JdbcTemplate jdbcTemplate,TaskScheduler taskScheduler) {
        super(taskScheduler);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public String acquire(String key, String token, Duration expiration) {
        // 这里是为了删除由于一些异常导致的锁,因为db 没有ttl过期机制
        final long now = System.currentTimeMillis();
        final int expired = jdbcTemplate.update(DELETE_EXPIRED_FORMATTED_QUERY,now);
        log.info("删除过期锁数量: {}", expired);

        final long expireAt = expiration.toMillis() + System.currentTimeMillis();
        final int created = jdbcTemplate.update(ACQUIRE_FORMATTED_QUERY, key, token, expireAt, Thread.currentThread().getName());
        return created == 1 ? token : null;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public boolean release0(Locker lock) {
        String key =lock.getKey();
        String token = lock.getToken();
        int deleted = jdbcTemplate.update(RELEASE_FORMATTED_QUERY, key, token);

        final boolean released = deleted == 1;
        if (released) {
            log.info("成功释放了1个锁 key {} token {}", key, token);
        } else if (deleted > 0) {
            log.error("锁释放失败 key {}  token {}, released {}", key, token, deleted);
        } else {
            log.error("锁不存在，无任何操作  key {}  token {}", key, token);
        }
        return released;
    }


    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public boolean refresh(String key, String token, Duration expiration) {
        final long now = System.currentTimeMillis();
        final long expireAt = expiration.toMillis() + now;

        final int updated = jdbcTemplate.update(REFRESH_FORMATTED_QUERY, expireAt, key, token);
        final boolean refreshed = updated == 1;
        if (refreshed) {
            log.info("成功刷新了1个锁 key {} token {}", key, token);
        } else if (updated > 0) {
            log.error("锁刷新失败 key {}  token {}, released {}", key, token, updated);
        } else {
            log.error("刷新锁操作无效  key {}  token {}", key, token);
        }
        return refreshed;
    }
}
