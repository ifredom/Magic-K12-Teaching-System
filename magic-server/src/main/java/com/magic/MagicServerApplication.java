package com.magic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.magic.module.**.mapper")
public class MagicServerApplication {

    public static void main(String[] args) {
        long start = System.nanoTime();
        SpringApplication.run(MagicServerApplication.class, args);
        Duration duration = Duration.ofNanos(System.nanoTime() - start);

        System.out.println("Startup time: " + duration.toMillis() + " ms");
        System.out.println("MagicServerApplication started successfully!");
    }
}