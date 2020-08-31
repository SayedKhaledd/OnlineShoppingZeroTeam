package com.example.onlineshopping;

import com.example.onlineshopping.Model.Product;

public interface OrdersOnclickListener {
    void plusOnClickListener(Product product) ;
    void minusOnClickListener(Product product) ;
    void deleteOnClickListener(Product product) ;

}
