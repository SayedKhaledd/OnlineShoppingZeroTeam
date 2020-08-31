package com.example.onlineshopping;

public class Constants {
    public static final String DATABASE_NAME = "com.example.onlineshopping";

    public static final String PREFERENCE_NAME = "com.example.onlineshopping";
    public static final String EMAIL = "email";
    public static final String REMEMBER_ME = "remember_me";
public static final String ID="id";
    public static final String IMAGE_URI_KEY = "image_uri_key";



    public static class CustomersTable {
        public static final String TABLE_NAME = "customers";
        public static final String CUSTOMER_NAME = "customer_name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String GENDER = "gender";
        public static final String BIRTH_DATE = "birth_date";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String PROFILE_IMAGE = "profile_image";

    }

    public static class ProductsTable {
        public static final String TABLE_NAME = "Products";
        public static final String PRODUCT_NAME = "product_name";
        public static final String PRODUCT_ID = "product_id";
        public static final String PRICE = "price";
        public static final String QUANTITY = "quantity";
        public static final String CATEGORY_ID = "category_id";
        public static final String DESCRIPTION = "description";
        public static final String IMAGE = "image";
        public static final String RATING = "rating";
        public static final String BARCODE = "barcode";




    }

    public static class OrdersTable {
        public static final String TABLE_NAME = "orders";
        public static final String ORDER_DATE ="order_date";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String ADDRESS = "address";
        public static final String ORDER_ID = "order_id";

    }

    public static class OrderDetailsTable {
        public static final String TABLE_NAME = "order_details";
        public static final String ORDER_ID = "order_id";
        public static final String ORDER_Details_ID = "order_details_id";
        public static final String PRODUCT_ID = "product_id";
        public static final String PRODUCT_NAME = "product_name";

        public static final String QUANTITY = "quantity";
        public static final String PRICE = "price";
        public static final String TOTAL_PRICE = "total_price";



    }

    public static class CategoriesTable {
        public static final String TABLE_NAME = "categories";
        public static final String CATEGORY_NAME = "category_name";
        public static final String CATEGORY_ID = "category_id";

    }

    public static class ShoppingCartTable{
        public static final String TABLE_NAME="shopping_cart";
        public static final String SHOPPING_CART_ID="shopping_cart_id";
        public static final String PRODUCT_ID="product_id";
        public static final String PRODUCT_QUANTITY="product_quantity";

        public static final String CUSTOMER_ID="customer_id";
        public static final String IS_IN_SHOPPING_CART="is_in_shopping_cart";


    }
}
