package ru.otus.highload.socialchat.config;

import com.github.mongobee.Mongobee;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.highload.socialchat.migration.InitChangelog;

import javax.annotation.Resource;

/**
 * https://github.com/mongobee/mongobee
 */
@Configuration
public class MongobeeConfig {

    @Resource
    private MongoProperties mongoProperties;

    @Resource
    private MongoTemplate mongoTemplate;

    @Bean
    public Mongobee mongobee(){
        Mongobee runner = new Mongobee(mongoProperties.getUri());
        runner.setMongoTemplate(mongoTemplate);
        // host must be set if not set in URI
        runner.setDbName(mongoProperties.getDatabase());
        // the package to be scanned for changesets
        runner.setChangeLogsScanPackage(InitChangelog.class.getPackageName());
        return runner;
    }
}
