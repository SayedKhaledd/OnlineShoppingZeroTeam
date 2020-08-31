package com.example.onlineshopping.Model;

public class ShoppingCart {
    private int shoppingCartId,productId, customerId,productQuantity;

    public ShoppingCart(int shoppingCartId, int productId, int customerId, int productQuantity) {
        this.shoppingCartId = shoppingCartId;
        this.productId = productId;
        this.customerId = customerId;
        this.productQuantity = productQuantity;
    }


    public ShoppingCart(int productId, int productQuantity,int customerId) {
        this.productId = productId;
        this.customerId = customerId;
       this.productQuantity=productQuantity;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(int shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
