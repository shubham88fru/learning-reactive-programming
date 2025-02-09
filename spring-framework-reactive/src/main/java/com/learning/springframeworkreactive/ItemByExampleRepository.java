package com.learning.springframeworkreactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemByExampleRepository extends ReactiveMongoRepository<Item, String> {
}
