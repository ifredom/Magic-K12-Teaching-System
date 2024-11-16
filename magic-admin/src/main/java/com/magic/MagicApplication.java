package com.magic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.magic.module.**.mapper")
class MagicApplication {

    public static void main(String[] args) {
        long start = System.nanoTime();
        SpringApplication.run(MagicApplication.class, args);
        Duration duration = Duration.ofNanos(System.nanoTime() - start);

        System.out.println("Startup time: " + duration.toMillis() + " ms");
        System.out.println("MagicApplication started successfully!");
    }
}