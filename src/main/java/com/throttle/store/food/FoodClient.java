package com.throttle.store.food;

import lombok.Getter;

import java.security.InvalidParameterException;

@Getter
public class FoodClient {

    private final String clientName;
    private final int ordersPerMinute;

    public FoodClient(String clientName, int ordersPerMinute, OrderCount orderCount) {
        if ( ordersPerMinute < 0) {
            throw new InvalidParameterException("Number of orders under 0 not allowed");
        }
        this. clientName = clientName;
        this. ordersPerMinute = ordersPerMinute;

        orderCount.addTenant(clientName);
    }
}
