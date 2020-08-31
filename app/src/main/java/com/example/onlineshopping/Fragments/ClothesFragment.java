package com.example.onlineshopping.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.Constants;
import com.example.onlineshopping.LogIn;
import com.example.onlineshopping.Model.Category;
import com.example.onlineshopping.Model.Product;
import com.example.onlineshopping.Model.ShoppingCart;
import com.example.onlineshopping.ProductOnClickListener;
import com.example.onlineshopping.ProductsAdapter;
import com.example.onlineshopping.R;
import com.example.onlineshopping.ShoppingDb;

import java.util.ArrayList;

public class ClothesFragment extends Fragment implements ProductOnClickListener {
    public  ArrayList<Product> clothesProducts=new ArrayList<>();
Dialog rankDialog;
RatingBar ratingBar;
    ShoppingDb shoppingDb;
    SharedPreferences sharedPreferences;
    ProductsAdapter productsAdapter;

    public static ClothesFragment newInstance() {
        return new ClothesFragment();
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        shoppingDb=new ShoppingDb(getContext());
        clothesProducts();
       try {
           sharedPreferences = this.getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
       }
       catch (Exception e){
           Log.d("exception", e.getMessage());
           sharedPreferences=null;
       }
        if(shoppingDb.clothesProducts==null){
            Log.d("NULL","nulll");
        }
        else{
            Log.d("not NULL"," not nulll");
        }
        View view = inflater.inflate(R.layout.fragment_clothes, container, false);
         productsAdapter=new ProductsAdapter(clothesProducts,getContext(),this);
     //    checkdata();
        Cursor cursor2=shoppingDb.showProducts();
        while(!cursor2.isAfterLast()){
            Log.d("Products: id", cursor2.getInt(0)+"");
            Log.d("Products: QUANTITY", cursor2.getInt(5)+"");
            cursor2.moveToNext();
        }


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(productsAdapter);

        return view;
    }

    @Override
    public void productAddOnClickListener(Product product) {
        if(sharedPreferences==null || sharedPreferences.getInt(Constants.ID,0)==0 || sharedPreferences.getString(Constants.EMAIL,null)==null||  sharedPreferences.getString(Constants.EMAIL,null).equals("")){
            if (sharedPreferences.getString(Constants.EMAIL, null)==null ||sharedPreferences.getString(Constants.EMAIL, null).equals("") && sharedPreferences.getInt(Constants.ID,0)==0  ) {
                Intent intent=new Intent(getContext(), LogIn.class);
                Log.d("shopping cart", "please log in ");
                Toast.makeText(getContext(),"Please log in first",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }

        }

        else if (product.getQuantity()==0){
            Toast.makeText(getContext(),"this item is empty right now , check later",Toast.LENGTH_LONG).show();

        }

      else{


          Cursor cursor=shoppingDb.showShoppingCart();
        boolean check=false;
        while(!cursor.isAfterLast()){
            if(cursor.getInt(1)==product.getId()&& cursor.getInt(0)==sharedPreferences.getInt(Constants.ID,0)){
                check=true;
                Log.d("found the product",product.getId()+"\n"+ cursor.getInt(0));
            }
cursor.moveToNext();
        }

        if(!check){

            ShoppingCart shoppingCart=new ShoppingCart(product.getId(),1,sharedPreferences.getInt(Constants.ID,0));
            Log.d("Added", "ID:"+sharedPreferences.getInt(Constants.ID,0)+"\n product id"+product.getId());
            shoppingDb.createShoppingCart(shoppingCart);
        }
      }

    }


    public void clothesProducts(){

        Cursor cursor1=shoppingDb.showProducts();
        if(clothesProducts.size()==0) while(!cursor1.isAfterLast() ){
            if(cursor1.getInt(3)==1){
                clothesProducts.add(  new Product(cursor1.getInt(0), cursor1.getString(1), cursor1.getString(2), new Category(cursor1.getInt(3)), cursor1.getDouble(4), cursor1.getInt(5), cursor1.getInt(6), cursor1.getDouble(7)));

            }
            cursor1.moveToNext();

        }
        if(clothesProducts.size()==0){
            Category category=new Category(1);
            category.setName("Clothes");
            shoppingDb.createCategory(category);
            clothesProducts.add(new Product("T-Shirt ", "A simple cotton design embellished with short sleeves, a crew neck \nand a chest print embroidered with beads and studs." , category, 50, 3, R.drawable.pngwave,"0123-4567"));
            clothesProducts.add(new Product("Sweet Shirt ", "Gray printed cotton sweatshirt. \n Perfect for casual wear, this men's sweatshirt is easy to wear with jeans or track pants.  ", category, 200, 50, R.drawable.sweet,"1959595952"));
            clothesProducts.add(new Product("Jacket ", "A legendary look gets back to its roots with the Nike Sportswear Windrunner Jacket.\n It features water-repellent details to give you coverage in rough weather.", category, 500, 30,R.drawable.jacket,"223"));

            Cursor cursor=shoppingDb.showProducts();
            for (int i=0;i<clothesProducts.size();i++){
                shoppingDb.createProduct(clothesProducts.get(i));
                clothesProducts.get(i).setId(shoppingDb.getLastProductId());

                clothesProducts.get(i).getCategory().setId(1);
            }

        }



    }


public void checkdata(){
     {
        Cursor cursor=shoppingDb.showProducts();

        for(int i=0;i<clothesProducts.size();i++){

            if(cursor.getInt(5)==0){
                shoppingDb.deleteProduct(clothesProducts.get(i).getId());
                clothesProducts.remove(clothesProducts.get(i));
            }
            else{
                clothesProducts.get(i).setQuantity(cursor.getInt(5));

            }
         //   productsAdapter.notifyDataSetChanged();
            cursor.moveToNext();

        }


    }
}
}
