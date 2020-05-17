package ru.otus.highload.socialbackend.config.data;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.liquibase")
    public LiquibaseProperties liquibaseProperties() {
        return new LiquibaseProperties();
    }

//    @Bean("slaveLiquibase")
//    public SpringLiquibase slaveLiquibase(@Qualifier("dataSourceSlave") DataSource dataSourceSlave) {
////        return springLiquibase(dataSourceSlave, liquibaseProperties());
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setDataSource(dataSourceSlave);
//        liquibase.setChangeLog("classpath:liquibase/master.xml");
//        return liquibase;
//    }
//
//    @Bean("slave2Liquibase")
//    public SpringLiquibase slave2Liquibase(@Qualifier("dataSourceSlave2") DataSource dataSourceSlave2) {
////        return springLiquibase(dataSourceSlave, liquibaseProperties());
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setDataSource(dataSourceSlave2);
//        liquibase.setChangeLog("classpath:liquibase/master.xml");
//        return liquibase;
//    }

    @Bean("masterLiquibase")
    public SpringLiquibase masterLiquibase(@Qualifier("dataSourceMaster") DataSource dataSourceMaster) {
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
