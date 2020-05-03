package ru.otus.highload.socialbackend.config.data;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class LiquibaseConfig {

    @Qualifier("dataSourceSlave")
    private final DataSource dataSourceSlave;

    @Qualifier("dataSourceMaster")
    private final DataSource dataSourceMaster;

    @Bean
    @ConfigurationProperties(prefix = "spring.liquibase")
    public LiquibaseProperties liquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean("slaveLiquibase")
    public SpringLiquibase slaveLiquibase() {
        return springLiquibase(dataSourceSlave, liquibaseProperties());
    }

    @Bean("masterLiquibase")
    public SpringLiquibase masterLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSourceMaster);
        liquibase.setChangeLog("classpath:liquibase/master.xml");
        return liquibase;
    }

    private static SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabels(properties.getLabels());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }
}
