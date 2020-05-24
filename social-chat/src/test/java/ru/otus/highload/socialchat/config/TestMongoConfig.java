package ru.otus.highload.socialchat.config;

import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class TestMongoConfig implements InitializingBean, DisposableBean {

    MongodExecutable executable;

    @Override
    public void afterPropertiesSet() throws Exception {
        String host = "localhost";
        int port = 27019;

        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(host, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        executable = starter.prepare(mongodConfig);
        executable.start();
    }


    @Bean
    public MongoDbFactory factory() {
        // also possible to connect to a remote or real MongoDB instance
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(new MongoClientURI("mongodb://localhost:27019/test_db"));
        return mongoDbFactory;
    }


    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        MongoTemplate template = new MongoTemplate(mongoDbFactory);
        template.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        return template;
    }

//    @Bean
//    public MongoRepositoryFactoryBean mongoFactoryRepositoryBean(MongoTemplate template) {
//        MongoRepositoryFactoryBean mongoDbFactoryBean = new MongoRepositoryFactoryBean(TransactionRepository.class);
//        mongoDbFactoryBean.setMongoOperations(template);
//
//        return mongoDbFactoryBean;
//    }

    @Override
    public void destroy() throws Exception {
        executable.stop();
    }

}
