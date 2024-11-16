package com.magic.framework.lock;

import com.magic.framework.lock.api.ILock;
import com.magic.framework.lock.impl.MysqlLock;
import com.magic.framework.lock.impl.RedisLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;

/**
 * 作用: 读取配置文件，配置对应的锁实现类
 *
 * @author ifredom
 * @version v0.0.1
 * @date 2024/09/19 23:22
 * @email 1950735817@qq.com
 */
@Configuration
public class MagicLockConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "magic.lock", name = "type", havingValue = "mysql", matchIfMissing = true)
    public ILock mysqlLock(JdbcTemplate jdbcTemplate, TaskScheduler taskScheduler){
        return new MysqlLock(jdbcTemplate, taskScheduler);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "magic.lock", name = "type", havingValue = "redis")
    public ILock redisLock(StringRedisTemplate stringRedisTemplate, TaskScheduler taskScheduler){
        return new RedisLock(stringRedisTemplate, taskScheduler);
    }
}
