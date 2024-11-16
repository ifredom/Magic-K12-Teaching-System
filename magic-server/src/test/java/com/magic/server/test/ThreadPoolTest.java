package com.magic.server.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Magic Server
 * +----------------------------------------------------------------------
 * 作用:
 * +----------------------------------------------------------------------
 * @author ifredom
 * +----------------------------------------------------------------------
 * @version v0.0.1
 * +----------------------------------------------------------------------
 * @date 2024/09/27 23:40
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */
@SpringBootTest
public class ThreadPoolTest {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Test
    public void testThreadPoolTaskExecutor() {

        for (int i = 0; i < 50; i++) {
//            threadPoolTaskExecutor.execute(myTask(i));
            threadPoolTaskExecutor.execute(new MyThreadPoolTaskExecutor(i));
        }
        threadPoolTaskExecutor.execute(()->{
            System.out.println("执行任务");
        });
    }

    private Runnable myTask(int i) {
        System.out.println("执行任务" + i);
        // 休眠1秒
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return () -> {
            System.out.println("执行任务"+ i +" 线程名称:" + Thread.currentThread().getName());
        };
    }
}

class MyThreadPoolTaskExecutor implements Runnable{

     private int taskNum;

     MyThreadPoolTaskExecutor(int taskNum){
        this.taskNum = taskNum;
     }

    @Override
    public void run() {
        // 休眠1秒
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行任务"+taskNum+" 线程名称:" + Thread.currentThread().getName());
    }
}