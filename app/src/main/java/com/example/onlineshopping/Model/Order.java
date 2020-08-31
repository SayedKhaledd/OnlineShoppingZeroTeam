package com.example.onlineshopping.Model;

import java.util.Date;

public class Order {
private String location;
private int customerId,orderId;
private Date date;

    public Order(String location, int customerId, Date date) {
        this.location = location;
        this.customerId = customerId;
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
