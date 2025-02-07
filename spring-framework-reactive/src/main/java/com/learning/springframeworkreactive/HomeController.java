package com.learning.springframeworkreactive;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
public class HomeController {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    @GetMapping
    public Mono<Rendering> home() {
        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items", itemRepository.findAll())
                .modelAttribute("cart", cartRepository.findById("My Cart")
                        .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }

    @PostMapping("/add/{id}")
    public Mono<String> addToCart(@PathVariable String id) {
        return cartRepository.findById("My Cart")
                .defaultIfEmpty(new Cart("My Cart"))
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem().getId().equals(id))
                        .findAny()
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        })
                        .orElseGet(() -> {
                            return itemRepository.findById(id)
                                    .map(item -> new CartItem(item))
                                    .map(cartItem -> {
                                        cart.getCartItems().add(cartItem);
                                        return cart;
                                    });
                        }))
                .flatMap(cart -> cartRepository.save(cart))
                .thenReturn("redirect:/");
    }
}
