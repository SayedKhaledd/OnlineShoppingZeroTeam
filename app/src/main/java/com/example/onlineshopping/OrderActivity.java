package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping.Email.GmailSender;
import com.example.onlineshopping.Model.Category;
import com.example.onlineshopping.Model.Order;
import com.example.onlineshopping.Model.OrderDetail;
import com.example.onlineshopping.Model.Product;

import java.util.ArrayList;
import java.util.Date;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
FinalOrderAdapter finalOrderAdapter;
RecyclerView recyclerView;
Button order;
ShoppingDb shoppingDb;
Date date;
SharedPreferences sharedPreferences;
ArrayList<Product> arrayList;
    String address,body;
    TextView totalPriceOfOrder,location;
    Button ButtonEmail;
    double price=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Orders");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        shoppingDb=new ShoppingDb(this);
        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
Intent  intent=getIntent();
        address=intent.getStringExtra("Address");
        orderedList();
        totalPriceOfOrder=(TextView)findViewById(R.id.totalPriceOfOrder);
ButtonEmail =(Button)findViewById(R.id.send_email);
        finalOrderAdapter =new FinalOrderAdapter(arrayList,this);
        recyclerView = (RecyclerView) findViewById(R.id.order_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(finalOrderAdapter);
        order=(Button) findViewById(R.id.order);
        order.setOnClickListener(this);
        ButtonEmail.setOnClickListener(this);
location=(TextView) findViewById(R.id.location);
location.setText(location.getText().toString()+address);
for(int i=0;i<arrayList.size();i++){
           price+= arrayList.get(i).getTotalPrice();

        }
totalPriceOfOrder.setText(totalPriceOfOrder.getText().toString()+price+"");

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.order){
        date=new Date();
            Order order=new Order(address,sharedPreferences.getInt(Constants.ID,0),date);
            shoppingDb.createOrder(order);
            int orderId=shoppingDb.getLastOrderId();
            order.setOrderId(orderId);
             ArrayList<OrderDetail> orderDetailArrayList=new ArrayList<>();
             for(int i=0;i<arrayList.size();i++){
                orderDetailArrayList.add(new OrderDetail(orderId,   arrayList.get(i).getId(), arrayList.get(i).getProductName()  ,arrayList.get(i).getQuantity(),   arrayList.get(i).getPrice(),   arrayList.get(i).getTotalPrice()));
              shoppingDb.createOrderDetail(orderDetailArrayList.get(i));
                 int orderDetailId=shoppingDb.getLastOrderDetailId();
                 orderDetailArrayList.get(i).setOrderDetailId(orderDetailId);
                 Cursor cursor3=shoppingDb.showProducts();
                 int quantity=0;
                 while (!cursor3.isAfterLast()){
                     if(cursor3.getInt(0)==arrayList.get(i).getId()){
                         quantity=cursor3.getInt(5);
                     break;
                     }
                     cursor3.moveToNext();
                 }
                 shoppingDb.updateProduct(arrayList.get(i).getId(),quantity-arrayList.get(i).getQuantity());
             }
            Cursor cursor=shoppingDb.showOrders();
             while(!cursor.isAfterLast()){
                 if(cursor.getInt(3)==sharedPreferences.getInt(Constants.ID,0)){
                     Log.d("Order: ID", cursor.getInt(0)+"");
                     Log.d("Order: Address", cursor.getString(1)+"");
                     Log.d("Order: Date", cursor.getString(2)+"");
                     Log.d("Order: Customer", cursor.getInt(3)+"");

                 }
                 cursor.moveToNext();
             }
             Cursor cursor1=shoppingDb.showOrderDetails();



             while(!cursor1.isAfterLast()){
                 if(cursor1.getInt(1)==orderId){
                     Log.d("OrderDetail: Id", cursor1.getInt(1)+"");
                     Log.d("OrderDetail: product id", cursor1.getInt(2)+"");
                     Log.d("OrderDetail:productname", cursor1.getString(3)+"");
                     Log.d("OrderDetail:price", cursor1.getDouble(4)+"");
                     Log.d("OrderDetail:quantity", cursor1.getInt(5)+"");
                     Log.d("OrderDetail:total price", cursor1.getDouble(6)+"");


                 }
                 cursor1.moveToNext();

             }
             Cursor cursor2=shoppingDb.showProducts();
             while(!cursor2.isAfterLast()){
                 Log.d("Products: id", cursor2.getInt(0)+"");
                 Log.d("Products: QUANTITY", cursor2.getInt(5)+"");
                 cursor2.moveToNext();
             }

Cursor cursor3=shoppingDb.showShoppingCart();
             while (!cursor3.isAfterLast()){
                 if(cursor3.getInt(0)==sharedPreferences.getInt(Constants.ID,0)){

                     shoppingDb.deleteShoppingCart(cursor3.getInt(3));
                 }
                 cursor3.moveToNext();

             }

Intent intent=new Intent(OrderActivity.this,MainActivity.class);
             startActivity(intent);
             finish();
        }
        else if(view.getId()==R.id.send_email){
            Log.d("order send", "email ");
            body="you Ordered: \n";
            for(int i=0;i<arrayList.size();i++){
                body=body+arrayList.get(i).getQuantity()+" of "+arrayList.get(i).getProductName()+" with total price "+arrayList.get(i).getTotalPrice()+"\n";

            }
            Date date=new Date();
            body=body+"Date: "+date.toString()+"\n"+"location"+address+"\n"+"total Price:" +price;
            Log.d("email", sharedPreferences.getString(Constants.EMAIL,null));
            sendMessage(sharedPreferences.getString(Constants.EMAIL,null),body);
        }
    }
    public ArrayList<Product> orderedList(){

        arrayList=new ArrayList<>();
        Cursor cursor=shoppingDb.showShoppingCart();

        while (!cursor.isAfterLast()  ){
            Log.d("shoppingcart: productid", cursor.getInt(1)+"");
            Log.d("shoppingcart:customerid", cursor.getInt(0)+"");
            Log.d("shoppingcart:quantity", cursor.getInt(2)+"");
            if(cursor.getInt(0)==sharedPreferences.getInt(Constants.ID,0)){
                Cursor cursor1 = shoppingDb.showProducts();
                Product product=null;
                while (!cursor1.isAfterLast()) {
                    if (cursor1.getInt(0) == cursor.getInt(1) ) {
                        product = new Product(cursor1.getInt(0), cursor1.getString(1), cursor1.getString(2), new Category(cursor1.getInt(3)), cursor1.getDouble(4), 1, cursor1.getInt(6), cursor1.getDouble(7));
                        Log.d("Product id found", cursor1.getInt(0)+"");
                        break;
                    }
                    cursor1.moveToNext();
                }
//                Log.d("quantity in array", product.getQuantity()+"");
             if(product!=null) {  product.setQuantity(cursor.getInt(2));
                arrayList.add(product);
            }}
            cursor.moveToNext();
        }
        return arrayList;
    }

    public void sendMessage(final String email, final String body ){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GmailSender sender = new GmailSender("onlineshoppingzeroteam@gmail.com", "sdsd12345");
                    sender.sendMail("Your Order",
                            body,
                            "onlineshoppingzeroteam@gmail.com",
                            email);
                    dialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                    Log.e("mylog", "Error: " + e.getMessage());
                    dialog.setMessage("error");
                }
            }
        });
        sender.start();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}