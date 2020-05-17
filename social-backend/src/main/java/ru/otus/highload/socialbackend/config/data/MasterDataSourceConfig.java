package ru.otus.highload.socialbackend.config.data;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@ConfigurationProperties("spring.datasource-master")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryMaster",
        transactionManagerRef = "transactionManagerMaster",
        basePackages = {"ru.otus.highload.socialbackend.repository.master"}
)
public class MasterDataSourceConfig {

    private final static String PERSISTENCE_UNIT_NAME = "master";

    @Resource
    private JpaVendorAdapter jpaVendorAdapter;

    @Primary
    @Bean("dataSourceMaster")
    public DataSource dataSourceMaster(MasterDataSourceProp masterDataSourceProp) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(masterDataSourceProp.getDriverClassName());
        dataSource.setJdbcUrl(masterDataSourceProp.getJdbcUrl());
        dataSource.setUsername(masterDataSourceProp.getUsername());
        dataSource.setPassword(masterDataSourceProp.getPassword());
        dataSource.setMaximumPoolSize(masterDataSourceProp.getMaximumPoolSize());
        dataSource.setMinimumIdle(masterDataSourceProp.getMinimumIdle());
        dataSource.setIdleTimeout(masterDataSourceProp.getIdleTimeout());
        dataSource.setPoolName(masterDataSourceProp.getPoolName());
        dataSource.setAutoCommit(true);
        //todo metric registry

        return dataSource;
    }

    @Primary
    @Bean
//    @DependsOn("masterLiquibase")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMaster(
            @Qualifier("dataSourceMaster") final DataSource dataSourceMaster) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSourceMaster);
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        factoryBean.setPackagesToScan("ru.otus.highload.socialbackend.domain");
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        return factoryBean;
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManagerMaster(EntityManagerFactory entityManagerFactoryMaster) {
        return new JpaTransactionManager(entityManagerFactoryMaster);
    }

    public static Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", "1000");
        return hibernateProperties;
    }
}
