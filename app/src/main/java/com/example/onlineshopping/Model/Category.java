package com.example.onlineshopping.Model;

public class Category {
    int id;
    String name;

    public Category(int id) {
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
