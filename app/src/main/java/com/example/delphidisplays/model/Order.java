package com.example.delphidisplays.model;

import java.util.ArrayList;

public class Order {

    private String orderId;
    private int restaurantId;
    private String userId;
    private ArrayList<Integer> ordered_items;

    public Order(int restaurantId, String userId, ArrayList<Integer> ordered_items) {
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.ordered_items = ordered_items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurantId = restaurant_id;
    }

    public ArrayList<Integer> getOrdered_items() {
        return ordered_items;
    }

    public void setOrdered_items(ArrayList<Integer> ordered_items) {
        this.ordered_items = ordered_items;
    }
}