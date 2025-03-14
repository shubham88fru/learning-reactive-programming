package com.learning.springframeworkreactive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class TemplateDatabaseLoader {

    @Bean
    CommandLineRunner initialize(MongoOperations mongoOperations) {
        return args -> {
            System.out.println("Initializing database...");
            mongoOperations.dropCollection(Item.class);
            mongoOperations.dropCollection(Cart.class);
            mongoOperations.save(new Item("Alarm clock", "clock", 19.99));
            mongoOperations.save(new Item("Television",  "television", 24.99));
        };
    }
}
