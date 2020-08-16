package ru.otus.highload.socialbackend.config.data;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@ConfigurationProperties("spring.datasource-master")
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryMaster",
        transactionManagerRef = "transactionManagerMaster",
        basePackages = {"ru.otus.highload.socialbackend.repository"}
//        basePackages = {"ru.otus.highload.socialbackend.repository.master"}
)
@RequiredArgsConstructor
public class MasterDataSourceConfig {

    private final static String PERSISTENCE_UNIT_NAME = "master";

    private final DiscoveryClient discoveryClient;

    @Primary
    @Bean("dataSourceMaster")
    public DataSource dataSourceMaster(MasterDataSourceProp masterDataSourceProp) {
        ServiceInstance dbInstance = getDbInstance();
        String jdbcUrl = String.format("jdbc:mysql://%s:%s/%s", dbInstance.getHost(), dbInstance.getPort(), "mydb");
//        String jdbcUrl = masterDataSourceProp.getJdbcUrl();

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(masterDataSourceProp.getDriverClassName());
        dataSource.setJdbcUrl(jdbcUrl);
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

    private ServiceInstance getDbInstance() {
        return discoveryClient.getInstances("mysql")
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unable to discover a mysql instance"));
    }

    @Primary
    @Bean
//    @DependsOn("masterLiquibase")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMaster(
            @Qualifier("dataSourceMaster") final DataSource dataSourceMaster,
            JpaVendorAdapter jpaVendorAdapter) {
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

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    public static Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", "1000");
        return hibernateProperties;
    }
}
