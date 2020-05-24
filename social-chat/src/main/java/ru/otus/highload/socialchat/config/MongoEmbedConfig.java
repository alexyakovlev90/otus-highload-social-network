package ru.otus.highload.socialchat.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;


/**
 * https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#upgrading.2-3
 */
@Configuration
@EnableConfigurationProperties({ MongoProperties.class, EmbeddedMongoProperties.class })
@EnableMongoRepositories(basePackages = "ru.otus.highload.socialchat.feature")
public class MongoEmbedConfig {

    @Resource
    private MongoProperties mongoProperties;

    @Value("${spring.mongodb.embedded.enabled:false}")
    private Boolean embedMongoEnabled;

    // embedded mongo
    private MongodExecutable mongodExecutable;

    @PostConstruct
    public void construct() throws IOException {
        if (embedMongoEnabled) {
            MongodStarter starter = MongodStarter.getDefaultInstance();
            IMongodConfig mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(mongoProperties.getHost(), mongoProperties.getPort(), true))
                    .build();
            mongodExecutable = starter.prepare(mongodConfig);
            mongodExecutable.start();
        }
    }

    @PreDestroy
    public void destroy() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }

    @Bean
    public MongoDbFactory mongoDbFactory() {
//        MongoCredential userCredentials = new MongoCredential("", ""); // username and password
        MongoClientURI mongoClientURI = new MongoClientURI(mongoProperties.getUri());
        MongoClient mongo = new MongoClient(mongoClientURI);
        return new SimpleMongoDbFactory(mongo, mongoProperties.getDatabase());
    }

    /**
     * Used in mongobee
     */
    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

    /**
     * To enable mongo transactions
     */
    @Bean
    public MongoTransactionManager transactionManager(MongoDbFactory mongoDbFactory) {
        return new MongoTransactionManager(mongoDbFactory);
    }
}
