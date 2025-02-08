package com.learning.springframeworkreactive;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Flux<Item> findAll() {
        return itemRepository.findAll();
    }

    public Mono<Item> findById(String id) {
        return itemRepository.findById(id);
    }
}
