package ru.otus.highload.socialbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class SocialBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialBackendApplication.class, args);
    }

}
