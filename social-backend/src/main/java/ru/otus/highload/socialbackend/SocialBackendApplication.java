package ru.otus.highload.socialbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import ru.otus.highload.socialbackend.config.MainConfig;
import ru.otus.highload.socialbackend.config.data.MasterDataSourceConfig;
import ru.otus.highload.socialbackend.config.data.replication.Slave2DataSourceConfig;
import ru.otus.highload.socialbackend.config.data.replication.SlaveDataSourceConfig;
import ru.otus.highload.socialbackend.config.data.tarantool.TarantoolDataSourceConfig;


@SpringBootConfiguration
@EnableAutoConfiguration(
        exclude = {LiquibaseAutoConfiguration.class, DataSourceAutoConfiguration.class}
)
@ComponentScan(
//        basePackageClasses = MainConfig.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {/*SlaveDataSourceConfig.class,*/ Slave2DataSourceConfig.class, TarantoolDataSourceConfig.class}),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)
        }
)
public class SocialBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialBackendApplication.class, args);
    }

}
