package com.magic.server.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataSourceTest {

    @Test
    public void testDataSource() {
        // 查看druid datasource数据源
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/testdb?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("magictang");
        System.out.println(druidDataSource.getUrl());
        System.out.println(druidDataSource.getUsername());
        System.out.println(druidDataSource.getPassword());
        System.out.println(druidDataSource.getClass());

        // 查看最大连接数
        System.out.println(druidDataSource.getMaxActive());
        // 查看最小连接数
        System.out.println(druidDataSource.getMinIdle());
        // 查看初始连接数
        System.out.println(druidDataSource.getInitialSize());

    }
}
