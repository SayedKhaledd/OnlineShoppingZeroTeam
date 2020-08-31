package com.example.onlineshopping.Model;

public class Product {
    private String productName, description,barcode;
    private Category category;
    private double price, totalPrice, rating,totalrating;
    private int quantity, image, id,totalraters=0;



    public double getTotalrating() {

        return totalrating;
    }



    public int getTotalraters() {
        return totalraters;
    }

    public void setTotalraters(int totalraters) {
        this.totalraters = totalraters;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
       totalrating+=rating;
        this.rating = rating;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Product(String productName, String description, Category category, double price, int quantity, int image, String barcode) {
        this.productName = productName;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.barcode=barcode;
    }
    public Product(int id){
        this.id=id;
    }
    public Product(int id, int quantity){
        this.id=id;
        this.quantity=quantity;
    }
    public Product(int id,String productName, String description,  double price, int quantity, int image) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.id= id;
    }
public Product(int id, String productName, String description,Category category,double price, int quantity,int image,double rating){
    this.id= id;
    this.productName = productName;
    this.description = description;
    this.category = category;
    this.price = price;
    this.quantity = quantity;
    this.image = image;
    this.rating=rating;
}
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public double getTotalPrice() {

        return getPrice() * getQuantity();
    }

}
