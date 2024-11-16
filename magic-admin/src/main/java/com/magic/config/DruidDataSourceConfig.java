package com.magic.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Magic Server
 * +----------------------------------------------------------------------
 * 作用: 配置多数据源
 * 1. druid-spring-boot-starter 数据库连接池，配置单数据源
 * 2. dynamic-datasource-spring-boot-starter 数据库连接池，配置动态数据源，可以切换
 * +----------------------------------------------------------------------
 *
 * @author ifredom
 * +----------------------------------------------------------------------
 * @version v0.0.1
 * +----------------------------------------------------------------------
 * @date 2024/10/09 22:30
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */

@Configuration
public class DruidDataSourceConfig {

    @Primary
    @Bean(name="primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.master")
    public DataSource dataSource1() {
        return new DruidDataSource();
    }


    @Bean(name="secondaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.slave")
    @ConditionalOnProperty(prefix="spring.datasource.slave", name="spring.datasource.slave.enabled", havingValue="true")
    public DataSource dataSource2() {
        return new DruidDataSource();
    }
}
