package com.magic.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

/**
 * Magic Server
 * +----------------------------------------------------------------------
 * 作用:
 * +----------------------------------------------------------------------
 *
 * @author ifredom
 * +----------------------------------------------------------------------
 * @version v0.0.1
 * +----------------------------------------------------------------------
 * @date 2024/11/20 23:36
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */
//@Configuration
//@EnableTransactionManagement
public class FlywayConfig {

    private static final String SQL_LOCATION = "classpath:db/migration";


//    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource dataSource;

    @Bean
    public void migrate() {
        System.out.println("调用数据库生成工具");


        System.out.println("正在执行多数据源生成数据库文件 " );
        Flyway flyway = Flyway.configure()
               .dataSource(dataSource)
               .locations("classpath:/db/migration")
               .load();
        flyway.migrate();
    }

}
