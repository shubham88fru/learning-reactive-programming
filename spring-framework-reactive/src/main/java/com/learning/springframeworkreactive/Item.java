package com.learning.springframeworkreactive;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode
public class Item {
    private @Id String id;
    private String name;
    private double price;
    private String description;
    private Item() {}

    public Item(String name, String description, double price) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
