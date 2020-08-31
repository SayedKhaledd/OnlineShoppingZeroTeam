package com.example.onlineshopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.onlineshopping.Model.Category;
import com.example.onlineshopping.Model.Customer;
import com.example.onlineshopping.Model.Order;
import com.example.onlineshopping.Model.OrderDetail;
import com.example.onlineshopping.Model.Product;
import com.example.onlineshopping.Model.ShoppingCart;

import java.util.ArrayList;

public class ShoppingDb extends SQLiteOpenHelper {
    public ArrayList<Product> clothesProducts;
    SQLiteDatabase sqLiteDatabase;

    public ShoppingDb(Context context) {
        super(context, Constants.DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // create customers table
        sqLiteDatabase.execSQL("create table " + Constants.CustomersTable.TABLE_NAME
                + "("
                + Constants.CustomersTable.CUSTOMER_ID + " integer primary key, "
                + Constants.CustomersTable.CUSTOMER_NAME + " text not null, "
                + Constants.CustomersTable.PASSWORD + " text,"
                + Constants.CustomersTable.EMAIL + " text,"
                + Constants.CustomersTable.PROFILE_IMAGE + " text,"
                + Constants.CustomersTable.GENDER + " text,"
                + Constants.CustomersTable.BIRTH_DATE + " text)");

        // create orders table
        sqLiteDatabase.execSQL("create table " + Constants.OrdersTable.TABLE_NAME
                + "(" + Constants.OrdersTable.ORDER_ID + " integer primary key, "
                + Constants.OrdersTable.ORDER_DATE + " text not null, "
                + Constants.OrdersTable.CUSTOMER_ID + " integer,"
                + Constants.OrdersTable.ADDRESS + " text,"
                + "FOREIGN KEY(" + Constants.CustomersTable.CUSTOMER_ID + ") REFERENCES " + Constants.CustomersTable.TABLE_NAME + "(" + Constants.CustomersTable.CUSTOMER_ID + "));");

        // create categories table
        sqLiteDatabase.execSQL("create table " + Constants.CategoriesTable.TABLE_NAME
                + "(" + Constants.CategoriesTable.CATEGORY_ID + " integer primary key, "
                + Constants.CategoriesTable.CATEGORY_NAME + " text)");

        // create products table
        sqLiteDatabase.execSQL("create table " + Constants.ProductsTable.TABLE_NAME
                + "(" + Constants.ProductsTable.PRODUCT_ID + " integer primary key, "
                + Constants.ProductsTable.PRODUCT_NAME + " text,"
                + Constants.ProductsTable.QUANTITY + " integer,"
                + Constants.ProductsTable.DESCRIPTION + " text,"
                + Constants.ProductsTable.BARCODE + " text,"

                + Constants.ProductsTable.PRICE + " double,"

                + Constants.ProductsTable.CATEGORY_ID + " integer,"
                + Constants.ProductsTable.RATING + " double,"

                + Constants.ProductsTable.IMAGE + " integer,"

                + "FOREIGN KEY(" + Constants.ProductsTable.CATEGORY_ID + ") REFERENCES " + Constants.CategoriesTable.TABLE_NAME + "(" + Constants.CategoriesTable.CATEGORY_ID + ")"
                + ")");

        // create order details table
        sqLiteDatabase.execSQL("create table " + Constants.OrderDetailsTable.TABLE_NAME
                + "(" + Constants.OrderDetailsTable.ORDER_Details_ID + " integer primary key, "

                + Constants.OrderDetailsTable.PRODUCT_ID + " integer,"
                + Constants.OrderDetailsTable.ORDER_ID + " integer,"
                + Constants.OrderDetailsTable.PRICE + " double,"
                + Constants.OrderDetailsTable.TOTAL_PRICE + " double,"
                + Constants.OrderDetailsTable.QUANTITY + " integer,"
                + Constants.OrderDetailsTable.PRODUCT_NAME + " text,"

                + "FOREIGN KEY(" + Constants.OrderDetailsTable.ORDER_ID + ") REFERENCES " + Constants.OrdersTable.TABLE_NAME + "(" + Constants.OrdersTable.ORDER_ID + ")," +
                "FOREIGN KEY(" + Constants.OrderDetailsTable.PRODUCT_ID + ") REFERENCES " + Constants.ProductsTable.TABLE_NAME + "(" + Constants.ProductsTable.PRODUCT_ID + ")"
                +
                ");"
        );

        sqLiteDatabase.execSQL("create table " + Constants.ShoppingCartTable.TABLE_NAME +
                "(" + Constants.ShoppingCartTable.SHOPPING_CART_ID + " integer primary key,"
                + Constants.ShoppingCartTable.CUSTOMER_ID + " integer,"
                + Constants.ShoppingCartTable.PRODUCT_ID + " integer,"
                + Constants.ShoppingCartTable.PRODUCT_QUANTITY + " integer,"
                + Constants.ShoppingCartTable.IS_IN_SHOPPING_CART + " boolean,"
                + "FOREIGN KEY(" + Constants.ShoppingCartTable.CUSTOMER_ID + ") REFERENCES " + Constants.CustomersTable.TABLE_NAME + "(" + Constants.CustomersTable.CUSTOMER_ID + ")," +
                "FOREIGN KEY(" + Constants.ShoppingCartTable.PRODUCT_ID + ") REFERENCES " + Constants.ProductsTable.TABLE_NAME + "(" + Constants.ProductsTable.PRODUCT_ID + ")"
                +
                ");"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Constants.CustomersTable.TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + Constants.OrdersTable.TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + Constants.CategoriesTable.TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + Constants.ProductsTable.TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + Constants.OrderDetailsTable.TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + Constants.ShoppingCartTable.TABLE_NAME);


        onCreate(sqLiteDatabase);
    }


    public Cursor showCustomers() {
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.CustomersTable.EMAIL, Constants.CustomersTable.PASSWORD, Constants.CustomersTable.CUSTOMER_ID, Constants.CustomersTable.PROFILE_IMAGE, Constants.CustomersTable.CUSTOMER_NAME};
        Cursor curs = sqLiteDatabase.query(Constants.CustomersTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null)
            curs.moveToFirst();
        sqLiteDatabase.close();
        return curs;
    }

    public Cursor showProductsId() {
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.ProductsTable.PRODUCT_ID, Constants.ProductsTable.CATEGORY_ID};
        Cursor curs = sqLiteDatabase.query(Constants.ProductsTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null)
            curs.moveToFirst();
        sqLiteDatabase.close();
        return curs;
    }

    public Cursor showProducts() {
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.ProductsTable.PRODUCT_ID, Constants.ProductsTable.PRODUCT_NAME, Constants.ProductsTable.DESCRIPTION, Constants.ProductsTable.CATEGORY_ID, Constants.ProductsTable.PRICE, Constants.ProductsTable.QUANTITY, Constants.ProductsTable.IMAGE, Constants.ProductsTable.RATING, Constants.ProductsTable.BARCODE};
        Cursor curs = sqLiteDatabase.query(Constants.ProductsTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null)
            curs.moveToFirst();
        sqLiteDatabase.close();
        return curs;
    }

    public Cursor showOrders() {
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.OrdersTable.ORDER_ID, Constants.OrdersTable.ADDRESS, Constants.OrdersTable.ORDER_DATE, Constants.OrdersTable.CUSTOMER_ID};
        Cursor curs = sqLiteDatabase.query(Constants.OrdersTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null)
            curs.moveToFirst();
        sqLiteDatabase.close();
        return curs;
    }

    public Cursor showOrderDetails() {
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.OrderDetailsTable.ORDER_Details_ID, Constants.OrderDetailsTable.ORDER_ID, Constants.OrderDetailsTable.PRODUCT_ID, Constants.OrderDetailsTable.PRODUCT_NAME, Constants.OrderDetailsTable.PRICE, Constants.OrderDetailsTable.QUANTITY, Constants.OrderDetailsTable.TOTAL_PRICE};
        Cursor curs = sqLiteDatabase.query(Constants.OrderDetailsTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null)
            curs.moveToFirst();
        sqLiteDatabase.close();
        return curs;
    }

    public Cursor showProductsNames() {
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.ProductsTable.PRODUCT_NAME};
        Cursor curs = sqLiteDatabase.query(Constants.ProductsTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null)
            curs.moveToFirst();
        sqLiteDatabase.close();
        return curs;
    }

    public Cursor showCategories() {
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.CategoriesTable.CATEGORY_ID, Constants.CategoriesTable.CATEGORY_NAME};
        Cursor curs = sqLiteDatabase.query(Constants.CategoriesTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null)
            curs.moveToFirst();
        sqLiteDatabase.close();
        return curs;

    }

    public Cursor showShoppingCart() {
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetail = {Constants.ShoppingCartTable.CUSTOMER_ID, Constants.ShoppingCartTable.PRODUCT_ID, Constants.ShoppingCartTable.PRODUCT_QUANTITY, Constants.ShoppingCartTable.SHOPPING_CART_ID};
        Cursor curs = sqLiteDatabase.query(Constants.ShoppingCartTable.TABLE_NAME, rowDetail, null, null, null, null, null);
        if (curs != null)
            curs.moveToFirst();
        sqLiteDatabase.close();
        return curs;

    }

    public void createCategory(Category category) {

        ContentValues row = new ContentValues();
        row.put(Constants.CategoriesTable.CATEGORY_NAME, category.getName());
        // row.put(Constants.CustomersTable.CUSTOMER_ID, customer.getId());

        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(Constants.CategoriesTable.TABLE_NAME, null, row);


        sqLiteDatabase.close();


    }


    public void createCustomer(Customer customer) {
        ContentValues row = new ContentValues();
        row.put(Constants.CustomersTable.CUSTOMER_NAME, customer.getName());
        // row.put(Constants.CustomersTable.CUSTOMER_ID, customer.getId());
        row.put(Constants.CustomersTable.EMAIL, customer.getEmail());
        row.put(Constants.CustomersTable.PASSWORD, customer.getPassword());
        //  row.put(Constants.CustomersTable.BIRTH_DATE, customer.getBirthdate());

        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(Constants.CustomersTable.TABLE_NAME, null, row);
        sqLiteDatabase.close();

    }

    public void createShoppingCart(ShoppingCart shoppingCart) {

        ContentValues row = new ContentValues();
        row.put(Constants.ShoppingCartTable.CUSTOMER_ID, shoppingCart.getCustomerId());
        row.put(Constants.ShoppingCartTable.PRODUCT_ID, shoppingCart.getProductId());
        row.put(Constants.ShoppingCartTable.PRODUCT_QUANTITY, shoppingCart.getProductQuantity());

        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(Constants.ShoppingCartTable.TABLE_NAME, null, row);
        sqLiteDatabase.close();
    }

    public void createProduct(Product product) {
        ContentValues row = new ContentValues();

        row.put(Constants.ProductsTable.PRODUCT_NAME, product.getProductName());
        row.put(Constants.ProductsTable.PRICE, product.getPrice());
        row.put(Constants.ProductsTable.DESCRIPTION, product.getDescription());
        row.put(Constants.ProductsTable.QUANTITY, product.getQuantity());
        row.put(Constants.ProductsTable.IMAGE, product.getImage());
        row.put(Constants.ProductsTable.CATEGORY_ID, product.getCategory().getId());
        row.put(Constants.ProductsTable.RATING, product.getRating());
        row.put(Constants.ProductsTable.BARCODE, product.getBarcode());

        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(Constants.ProductsTable.TABLE_NAME, null, row);
        sqLiteDatabase.close();
    }

    public int getProductId(Product product) {
        int x = -1;
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.ProductsTable.PRODUCT_ID};
        Cursor curs = sqLiteDatabase.query(Constants.ProductsTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        while (!curs.isAfterLast()) {
            if (product.getQuantity() == curs.getInt(5) && product.getProductName().equals(curs.getString(1)) && curs.getString(2).equals(product.getDescription()))

                x = curs.getInt(0);
        }
        sqLiteDatabase.close();
        return x;
    }

    public int getLastCategoryId() {
        int x;
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.CategoriesTable.CATEGORY_ID};
        Cursor curs = sqLiteDatabase.query(Constants.CategoriesTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null && curs.getCount() > 0) {
            curs.moveToLast();

            x = curs.getInt(0);
        } else x = -1;
        sqLiteDatabase.close();

        return x;
    }

    public int getLastCustomerId() {
        int x;
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.CustomersTable.CUSTOMER_ID};
        Cursor curs = sqLiteDatabase.query(Constants.CustomersTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null && curs.getCount() > 0) {
            curs.moveToLast();

            x = curs.getInt(0);
        } else x = -1;
        sqLiteDatabase.close();

        return x;

    }

    public int getLastOrderId() {
        int x;
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.OrdersTable.ORDER_ID};
        Cursor curs = sqLiteDatabase.query(Constants.OrdersTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null && curs.getCount() > 0) {
            curs.moveToLast();

            x = curs.getInt(0);
        } else x = -1;
        sqLiteDatabase.close();

        return x;

    }

    public int getLastOrderDetailId() {
        int x;
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.OrderDetailsTable.ORDER_Details_ID};
        Cursor curs = sqLiteDatabase.query(Constants.OrderDetailsTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null && curs.getCount() > 0) {
            curs.moveToLast();

            x = curs.getInt(0);
        } else x = -1;
        sqLiteDatabase.close();

        return x;

    }

    public int getLastProductId() {
        int x;
        sqLiteDatabase = getReadableDatabase();
        String[] rowDetails = {Constants.ProductsTable.PRODUCT_ID};
        Cursor curs = sqLiteDatabase.query(Constants.ProductsTable.TABLE_NAME, rowDetails, null, null, null, null, null);
        if (curs != null && curs.getCount() > 0) {
            curs.moveToLast();

            x = curs.getInt(0);
        } else x = -1;
        sqLiteDatabase.close();

        return x;

    }

    public void updatePassword(int id, String password) {
        ContentValues row = new ContentValues();
        row.put(Constants.CustomersTable.PASSWORD, password);
//        row.put(Constants.ContactTable.PHONE, contact.getPhone());
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(Constants.CustomersTable.TABLE_NAME, row, Constants.CustomersTable.CUSTOMER_ID + "='" + id + "'", null);
        sqLiteDatabase.close();
    }

    public void updateShoppingCart(ShoppingCart shoppingCart) {
        ContentValues row = new ContentValues();
        row.put(Constants.ShoppingCartTable.PRODUCT_QUANTITY, shoppingCart.getProductQuantity());
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(Constants.ShoppingCartTable.TABLE_NAME, row, Constants.ShoppingCartTable.SHOPPING_CART_ID + "='" + shoppingCart.getShoppingCartId() + "'", null);
        sqLiteDatabase.close();

    }

    public int getShoppingCartId(ShoppingCart shoppingCart) {
        Cursor cursor1 = showShoppingCart();
        int id = 0;
        while (!cursor1.isAfterLast()) {
            if (cursor1.getInt(0) == shoppingCart.getCustomerId() && cursor1.getInt(1) == shoppingCart.getProductId()) {
                id = cursor1.getInt(3);
                break;
            }
            cursor1.moveToNext();

        }
        return id;
    }

    public void createOrder(Order order) {
        ContentValues row = new ContentValues();
        row.put(Constants.OrdersTable.CUSTOMER_ID, order.getCustomerId());
        row.put(Constants.OrdersTable.ADDRESS, order.getLocation());
        row.put(Constants.OrdersTable.ORDER_DATE, order.getDate().toString());


        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(Constants.OrdersTable.TABLE_NAME, null, row);
        sqLiteDatabase.close();
    }

    public void updateProduct(int id, int quantity) {
        ContentValues row = new ContentValues();
        row.put(Constants.ProductsTable.QUANTITY, quantity);
//        row.put(Constants.ContactTable.PHONE, contact.getPhone());
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(Constants.ProductsTable.TABLE_NAME, row, Constants.ProductsTable.PRODUCT_ID + "='" + id + "'", null);
        sqLiteDatabase.close();
    }

    public void createOrderDetail(OrderDetail orderDetail) {
        ContentValues row = new ContentValues();
        row.put(Constants.OrderDetailsTable.ORDER_ID, orderDetail.getOrderId());
        row.put(Constants.OrderDetailsTable.PRODUCT_ID, orderDetail.getProductId());
        row.put(Constants.OrderDetailsTable.PRICE, orderDetail.getPrice());
        row.put(Constants.OrderDetailsTable.TOTAL_PRICE, orderDetail.getTotalPrice());
        row.put(Constants.OrderDetailsTable.QUANTITY, orderDetail.getQuantity());
        row.put(Constants.OrderDetailsTable.PRODUCT_NAME, orderDetail.getProductName());
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(Constants.OrderDetailsTable.TABLE_NAME, null, row);
        sqLiteDatabase.close();

    }


    public void addOrder(ArrayList<Product> arrayList) {

    }

    //    public void updateContact(Contact contact) {
//        ContentValues row = new ContentValues();
//        row.put(Constants.ContactTable.NAME, contact.getName());
//        row.put(Constants.ContactTable.PHONE, contact.getPhone());
//        sqLiteDatabase = getWritableDatabase();
//        sqLiteDatabase.update(Constants.ContactTable.TABLE_NAME, row, Constants.ContactTable.ID + "='" + contact.getId() + "'", null);
//        sqLiteDatabase.close();
//    }
//
    public void deleteShoppingCart(int id) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(Constants.ShoppingCartTable.TABLE_NAME, Constants.ShoppingCartTable.SHOPPING_CART_ID + "='" + id + "'", null);
        sqLiteDatabase.close();
    }

    public void deleteProduct(int id) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(Constants.ProductsTable.TABLE_NAME, Constants.ProductsTable.PRODUCT_ID + "='" + id + "'", null);
        sqLiteDatabase.close();
    }

    public void updateCustomerPic(int id, String uri) {
        ContentValues row = new ContentValues();
        row.put(Constants.CustomersTable.PROFILE_IMAGE, uri);
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(Constants.CustomersTable.TABLE_NAME, row, Constants.CustomersTable.CUSTOMER_ID + "='" + id + "'", null);
        sqLiteDatabase.close();
    }

}
