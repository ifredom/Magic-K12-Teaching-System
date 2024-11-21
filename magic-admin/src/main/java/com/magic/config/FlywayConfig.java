package com.magic.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
@Configuration
@EnableTransactionManagement
public class FlywayConfig {

    @Value("${spring.flyway.enabled}")
    private boolean flywayEnabled;

    @Value("${spring.flyway.encoding}")
    private String flywayEncoding;

    @Value("${spring.flyway.locations}")
    private String flywayLocations;

    @Value("${spring.flyway.sql-migration-prefix}")
    private String flywaySqlMigrationPrefix;

    @Value("${spring.flyway.sql-migration-separator}")
    private String flywaySqlMigrationSeparator;

    @Value("${spring.flyway.sql-migration-suffixes}")
    private String[] flywaySqlMigrationSuffixes;

    @Value("${spring.flyway.placeholder-replacement}")
    private boolean flywayPlaceholderReplacement;

    @Value("${spring.flyway.validate-on-migrate}")
    private boolean flywayValidateOnMigrate;

    @Value("${spring.flyway.clean-disabled}")
    private boolean flywayCleanDisabled;

    @Value("${spring.flyway.baseline-on-migrate}")
    private boolean flywayBaselineOnMigrate;

    @Value("${spring.flyway.baseline-version}")
    private String flywayBaselineVersion;

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource dataSource;

    @Bean
    public void migrate() {
        if (!flywayEnabled) {
            System.out.println("Flyway is disabled, skipping migration.");
            return;
        }

        System.out.println("调用数据库生成工具");
        System.out.println("正在执行多数据源生成数据库文件");

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(flywayLocations)
                .encoding(flywayEncoding)
                .sqlMigrationPrefix(flywaySqlMigrationPrefix)
                .sqlMigrationSeparator(flywaySqlMigrationSeparator)
                .sqlMigrationSuffixes(flywaySqlMigrationSuffixes)
                .placeholderReplacement(flywayPlaceholderReplacement)
                .validateOnMigrate(flywayValidateOnMigrate)
                .cleanDisabled(flywayCleanDisabled)
                .baselineOnMigrate(flywayBaselineOnMigrate)
                .baselineVersion(flywayBaselineVersion)
                .load();

        flyway.migrate();
    }
}