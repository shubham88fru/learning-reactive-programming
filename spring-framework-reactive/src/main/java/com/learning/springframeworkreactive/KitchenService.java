package com.learning.springframeworkreactive;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class KitchenService {

    private final Random picker = new Random();
    private List<Dish> menu = Arrays.asList(
            new Dish("Rice and lentinls"),
            new Dish("Dosa"),
            new Dish("Wada pav")
    );

    /*
        Generates a continuous stream of dishes.
     */
    Flux<Dish> getDishes(){
        return Flux.<Dish> generate(sink -> sink.next(randomDish()))
                .delayElements(Duration.ofMillis(250));
    }

    /*
        Randomly picks the next dish.
     */
    private Dish randomDish() {
        return menu.get(picker.nextInt(menu.size()));
    }
}
