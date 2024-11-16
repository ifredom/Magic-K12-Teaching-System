package com.magic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 作用: 跨域配置
 *
 * @author ifredom
 * @version v0.0.1
 * @date 2024/09/23 23:19
 * @email 1950735817@qq.com
 */
@Configuration
@Slf4j
public class WebMvcConfig  implements WebMvcConfigurer {

    /**
     * 白名单
     * 排除不需要跨域的接口
     */
    private static final String[] exclude_path = {"/admin/**",
            "/admin/login.html",
            "/error",
            "/swagger-resources/**",
            "/sys/auth/v1/login",
            "/favicon.ico",
            "/ureport/preview",
            "/ureport/res/**",
            "/captcha"};

    private static final String[] trace_exclude_path = {"/admin/**",
            "/admin/login.html",
            "/error",
            "/swagger-resources/**"};

    /**
     * 允许跨域配置
     */
    @Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry.addMapping("/**")
                // 允许任何域名
                .allowedOrigins("*")
                // 允许任何方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许任何头
                .allowedHeaders("*")
                // 暴露header中的其他属性给客户端
                .exposedHeaders("*")
                // 最大响应时间
                .maxAge(3600);
    }
}
