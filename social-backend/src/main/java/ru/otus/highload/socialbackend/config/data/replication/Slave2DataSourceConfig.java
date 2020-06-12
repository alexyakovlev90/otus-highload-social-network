package ru.otus.highload.socialbackend.config.data.replication;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
//@ConfigurationProperties("spring.datasource-slave")
public class Slave2DataSourceConfig {

    private final static String PERSISTENCE_UNIT_NAME = "slave";

    @Resource
    private JpaVendorAdapter jpaVendorAdapter;

    @Bean("dataSourceSlave2")
    public DataSource dataSourceSlave(Slave2DataSourceProp slave2DataSourceProp) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(slave2DataSourceProp.getDriverClassName());
        dataSource.setJdbcUrl(slave2DataSourceProp.getJdbcUrl());
        dataSource.setUsername(slave2DataSourceProp.getUsername());
        dataSource.setPassword(slave2DataSourceProp.getPassword());
        dataSource.setMaximumPoolSize(slave2DataSourceProp.getMaximumPoolSize());
        dataSource.setMinimumIdle(slave2DataSourceProp.getMinimumIdle());
        dataSource.setIdleTimeout(slave2DataSourceProp.getIdleTimeout());
        dataSource.setPoolName(slave2DataSourceProp.getPoolName());
        dataSource.setAutoCommit(true);
        return dataSource;
    }
}
