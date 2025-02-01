package com.learning.springframeworkreactive;

public class Dish {
    private String name;
    private boolean delivered = false;

    public Dish(String name) {
        this.name = name;
    }

    public static Dish deliver(Dish dish) {
        Dish deliveredDish = new Dish(dish.name);
        deliveredDish.delivered = true;
        return deliveredDish;
    }

    public String getName() {
        return name;
    }


    public boolean isDelivered() {
        return delivered;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", delivered=" + delivered +
                '}';
    }
}
