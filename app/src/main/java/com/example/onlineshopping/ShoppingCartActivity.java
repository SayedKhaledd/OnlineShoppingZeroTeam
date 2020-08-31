package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.onlineshopping.Fragments.ClothesFragment;
import com.example.onlineshopping.Model.Category;
import com.example.onlineshopping.Model.Product;
import com.example.onlineshopping.Model.ShoppingCart;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity implements OrdersOnclickListener, View.OnClickListener {
    RecyclerView recyclerView;
    OrdersAdapter ordersAdapter;
    Button gotomaps;
    ShoppingDb shoppingDb;
    SharedPreferences sharedPreferences;
    ArrayList<Product> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Shopping Cart");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        shoppingDb=new ShoppingDb(this);

        ArrayList<Product> products=showedList();
        ordersAdapter =new OrdersAdapter(products,this,this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(ordersAdapter);
        gotomaps=(Button) findViewById(R.id.gotomaps);
        gotomaps.setOnClickListener(this);
     //   button.setOnClickListener(this);


Cursor cursor=shoppingDb.showShoppingCart();
        Log.d("SharedId", sharedPreferences.getInt(Constants.ID,0)+"");
while (!cursor.isAfterLast() && cursor.getInt(0)==sharedPreferences.getInt(Constants.ID,0)){
    Log.d("shoppingcart: productid", cursor.getInt(1)+"");
    Log.d("shoppingcart:customerid", cursor.getInt(0)+"");
    Log.d("shoppingcart:quantity", cursor.getInt(2)+"");
cursor.moveToNext();
}
if(arrayList!=null)
for(int i=0;i<arrayList.size();i++){
    Log.d("arrayelement:id",arrayList.get(i).getId() +"");
    Log.d("arrayelement:id",arrayList.get(i).getQuantity() +"");
    Log.d("arrayelement:id",arrayList.get(i).getProductName() +"");

}

    }

    @Override
    public void plusOnClickListener(Product product) {
        Cursor cursor=shoppingDb.showProducts();
        int quantity=0;
        while(!cursor.isAfterLast()){
            if(cursor.getInt(0)==product.getId()){
                quantity=cursor.getInt(5);
            break;
            }
cursor.moveToNext();
        }
        if(!((product.getQuantity()+1)>quantity)){
        product.setQuantity(product.getQuantity()+1);
        ShoppingCart shoppingCart=new ShoppingCart(product.getId(),product.getQuantity(),sharedPreferences.getInt(Constants.ID,0));
   shoppingCart.setShoppingCartId(shoppingDb.getShoppingCartId(shoppingCart));
        shoppingDb.updateShoppingCart(shoppingCart);
            Log.d("product quantityordered", product.getQuantity()+"");
            ordersAdapter.notifyDataSetChanged();}
    }

    @Override
    public void minusOnClickListener(Product product) {
        if(product.getQuantity()>1)

            product.setQuantity(product.getQuantity()-1);
ShoppingCart shoppingCart=       new ShoppingCart(product.getId(),product.getQuantity(),sharedPreferences.getInt(Constants.ID,0));

        shoppingCart.setShoppingCartId(shoppingDb.getShoppingCartId(shoppingCart));

        shoppingDb.updateShoppingCart(shoppingCart);
        ordersAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteOnClickListener(Product product) {

      arrayList.remove(product);
      int id=0;

      Cursor cursor=shoppingDb.showShoppingCart();
      while(!cursor.isAfterLast()){
          if(cursor.getInt(1)==product.getId()&& cursor.getInt(0)==sharedPreferences.getInt(Constants.ID,0)){
              id=cursor.getInt(3);
              Log.d("deleted", id+"");
              break;
          }
           cursor.moveToNext();

      }
      shoppingDb.deleteShoppingCart(id);
          ordersAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.gotomaps){
            Intent intent=new Intent(ShoppingCartActivity.this, MapsActivity.class);
            startActivity(intent);

        }
    }
    public ArrayList<Product> showedList(){

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
                    Log.d("Product name found", cursor1.getString(1)+"");

                    break;
                }
                cursor1.moveToNext();
            }
                if(product!=null){product.setQuantity(cursor.getInt(2));
                arrayList.add(product);
            }}
            cursor.moveToNext();
        }
    return arrayList;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}