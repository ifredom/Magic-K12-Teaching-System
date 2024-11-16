package com.magic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.*;

/**
 * Magic Server
 * +----------------------------------------------------------------------
 * 作用: 配置线程池大小（含有动态设置和固定设置两种）
 * +----------------------------------------------------------------------
 * 阅读链接：<a href="https://blog.csdn.net/win7583362/article/details/127166798">...</a>
 * +----------------------------------------------------------------------
 * @author ifredom
 * +----------------------------------------------------------------------
 * @version v0.0.1
 * +----------------------------------------------------------------------
 * @date 2024/09/27 23:22
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */
@Configuration
public class ThreadPoolConfig {

    //普通模式
    private final int corePoolSize = 100; //核心线程池数量
    private final int maxPoolSize = 200; //最大线程
    private final int queueCapacity = 10000; //缓存队列条数
    private final int keepAliveSeconds = 10; //允许的空闲时间
    private final String threadNamePrefix = "ThreadPoolTaskExecutor-"; //线程名称前缀

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {

        return setFixedThreadPoolExecutor();
//        return setDynamicThreadPoolExecutor();
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        //获取cpu核心数
        int i = Runtime.getRuntime().availableProcessors();
        //核心线程数
        int corePoolSize = i * 2;
        //最大线程数
        int maximumPoolSize = i * 2;
        //线程无引用存活时间
        long keepAliveTime = 60;

        TimeUnit unit = TimeUnit.SECONDS;

        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(i * 2 * 10);

        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //拒绝执行处理器
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        //创建线程池
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * 设置固定线程池
     * 效果：
     * 1.线程池中线程数量为3
     * 2.最大线程数为5
     * 3.队列中最大的数目为10000
     * 4.线程空闲后的最大存活时间为10秒。
     * 5.线程名称前缀为ThreadPoolTaskExecutor-
     * @return ThreadPoolExecutor
     */
    private ThreadPoolTaskExecutor setFixedThreadPoolExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        //核心线程数目
        executor.setCorePoolSize(corePoolSize);
        //指定最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //队列中最大的数目
        executor.setQueueCapacity(queueCapacity);
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(keepAliveSeconds);
        //线程名称前缀
        executor.setThreadNamePrefix(threadNamePrefix);
        //拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //当调度器shutdown被调用时等待当前被调度的任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);

        //加载
        executor.initialize();
        System.out.println("初始化线程池成功");
        return executor;
    }

    /**
     * 设置动态线程池
     * 效果：
     * 1.线程池中线程数量根据CPU核数的2倍来动态调整
     * 2.最大线程数为CPU核数的4倍
     * 3.队列中最大的数目为CPU核数的8倍
     * 4.线程空闲后的最大存活时间为60秒。
     * @return ThreadPoolExecutor
     */
    private ThreadPoolTaskExecutor setDynamicThreadPoolExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        int i = Runtime.getRuntime().availableProcessors();
        //核心线程数目
        executor.setCorePoolSize(i * 2);
        //指定最大线程数
        executor.setMaxPoolSize(i * 2);
        //队列中最大的数目
        executor.setQueueCapacity(i * 2 * 10);
        //线程名称前缀
        executor.setThreadNamePrefix("ThreadPoolTaskExecutor-");
        //拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //当调度器shutdown被调用时等待当前被调度的任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(60);
        //加载
        executor.initialize();
        System.out.println("初始化线程池成功");
        return executor;
    }

}

