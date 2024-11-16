package com.magic.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义配置
 *
 * @author Magic Tang
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "magic")
public class MagicConfig {

    /**
     * 临时值。用于存储临时值配置
     */
    @Value("${ecsFilePath}")
    public static String ecsFilePath;

    /**
     * 文件存储
     */
    private Storage storage = new Storage();


    @Data
    public static class Storage {
        private Local local = new Local();
        private Aliyun aliyun = new Aliyun();
    }

    @Data
    public static class Local {
        private boolean enable = true;
        private String address = "http://localhost:8080";
        private String storagePath = "storage";

    }

    @Data
    public static class Aliyun {
        private boolean enable;
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
    }
}
