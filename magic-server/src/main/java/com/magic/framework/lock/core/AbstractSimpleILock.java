package com.magic.framework.lock.core;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.magic.framework.exception.CustomException;
import com.magic.framework.lock.api.ILock;
import com.magic.framework.lock.api.Locker;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

/**
 * 作用: MySql/Redis 分布式锁中间层抽象，暴漏 acquire, release, refresh 方法交给子类实现
 * 1.任务调度队列 TaskScheduler, 重写 获取锁，释放锁方法
 * 2.定义抽象方法  获取锁，释放锁，刷新锁方法
 * @author ifredom
 * @version v0.0.1
 * @date 2024/09/20 12:09
 * @email 1950735817@qq.com
 */
public abstract class AbstractSimpleILock  implements ILock {

    private TaskScheduler taskScheduler;

    protected AbstractSimpleILock(TaskScheduler taskScheduler){
        this.taskScheduler = taskScheduler;
    }

    @Override
    public Locker acquireLock(String key, final Duration expiration){
        String token = IdUtil.fastSimpleUUID();
        String acquire = acquire(key, token, expiration);
        if(StrUtil.isBlank(acquire)){
            throw new CustomException("当前任务正在处理中，请稍后重试");
        }
        // 后台线程定时续租 一个锁一个后台线程续约
        ScheduledFuture<?> scheduledFuture = scheduleLockRefresh(key, token, expiration);
        return Locker.builder().key(key).token(token).schedulerFuture(scheduledFuture).build();
    }

    @Override
    public boolean releaseLock(Locker locker) {
        cancelSchedule(locker);
        return release0(locker);
    }

    private ScheduledFuture<?> scheduleLockRefresh(String key,String token,Duration expiration){
        return taskScheduler.scheduleAtFixedRate(() -> refresh(key, token, expiration), expiration.toMillis() / 3);
    }

    private void cancelSchedule(Locker lock){
        final ScheduledFuture<?> taskScheduler  = lock.getSchedulerFuture();
        if(taskScheduler!= null && !taskScheduler.isCancelled()&& !taskScheduler.isDone()){
            taskScheduler.cancel(true);
        }
    }

    public abstract String acquire(String key, String token, Duration expiration);

    public abstract boolean release0(Locker lock);

    public abstract boolean refresh(String key, String token, Duration expiration);
}
