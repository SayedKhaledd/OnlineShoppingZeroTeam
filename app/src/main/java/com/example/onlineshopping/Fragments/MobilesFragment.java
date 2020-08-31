package com.example.onlineshopping.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MobilesFragment extends Fragment implements ProductOnClickListener {
    ShoppingDb shoppingDb;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    ProductsAdapter productsAdapter;
    public  ArrayList<Product> mobileProducts=new ArrayList<>();

    public static MobilesFragment newInstance() {
        return new MobilesFragment();
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        shoppingDb=new ShoppingDb(getContext());
mobilesProducts();
        try {
            sharedPreferences = this.getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        catch (Exception e){
            Log.d("exception", e.getMessage());
            sharedPreferences=null;
        }
        View view = inflater.inflate(R.layout.fragment_mobiles, container, false);
         productsAdapter=new ProductsAdapter(mobileProducts,getContext(),this);

         recyclerView = (RecyclerView) view.findViewById(R.id.recyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(productsAdapter);
        return view;
    }

    @Override
    public void productAddOnClickListener(Product product) {
        if(sharedPreferences==null){
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
        else{  Cursor cursor=shoppingDb.showShoppingCart();
            boolean check=false;
            Log.d("product id clicked", product.getId()+"");
            while(!cursor.isAfterLast()){
                if(cursor.getInt(1)==product.getId()&& cursor.getInt(0)==sharedPreferences.getInt(Constants.ID,0)){
                    Log.d("not added", "product id"+cursor.getInt(1)+"customer id"+sharedPreferences.getInt(Constants.ID,0));
                    check=true;

                }
                cursor.moveToNext();
            }

            if(!check){
                ShoppingCart shoppingCart=new ShoppingCart(product.getId(),1,sharedPreferences.getInt(Constants.ID,0));
                Log.d("product id added", product.getId()+"");
                shoppingDb.createShoppingCart(shoppingCart);
            }
        }



    }



    public void mobilesProducts(){
 Cursor cursor1=shoppingDb.showProducts();
        if(mobileProducts.size()==0) while(!cursor1.isAfterLast()  ){
     if(cursor1.getInt(3)==3){

         mobileProducts.add(  new Product(cursor1.getInt(0), cursor1.getString(1), cursor1.getString(2), new Category(cursor1.getInt(3)), cursor1.getDouble(4),  cursor1.getInt(5), cursor1.getInt(6), cursor1.getDouble(7)));

     }
     cursor1.moveToNext();

 }
 if(mobileProducts.size()==0){
  Category category=new Category(3);
  category.setName("Mobile");
  shoppingDb.createCategory(category);
  mobileProducts.add(new Product("Mobile Samsung S8+","Samsung Galaxy S8 Plus smartphone runs on Android v7.0 (Nougat) operating system.\n The phone is powered by Octa core (2.3 GHz, Quad core, M2 Mongoose + 1.7 GHz, Quad core, Cortex A53) processor.\n It runs on the Samsung Exynos 9 Octa 8895 Chipset. It has 4 GB RAM and 64 GB internal storage. ", category,25415,30,R.drawable.samsung_s8,"NMN-8455"));
mobileProducts.add(new Product("Mobile Xiaomi Mi4 ","Xiaomi Mi4 smartphone runs on Android v4.4.3 (Kitkat) operating system. \nThe phone is powered by Quad core, 2.5 GHz, Krait 400 processor. It runs on the Qualcomm Snapdragon 801 MSM8974AC Chipset.\n It has 3 GB RAM and 16 GB internal storage.", category,3000,50,R.drawable.xaiomi_mi4,"NMN-8")  );
     mobileProducts.add(new Product("Mobile Xiaomi Redmi Note 4","XIAOMI REDMI NOTE 4 launched in India in May 2017 at a starting price of Rs 9,999. The smartphone comes in three variants based on RAM and in-built storage.\n These include 2GB RAM and 32GB internal storage, 3GB RAM and 32GB internal storage; and 4GB RAM and 64GB ", category,3000,50,R.drawable.xaiomi_redimi_note4,"NMKN-8")  );

     Cursor cursor=shoppingDb.showProducts();

for (int i=0;i<mobileProducts.size();i++){
    shoppingDb.createProduct(mobileProducts.get(i));
    mobileProducts.get(i).getCategory().setId(3);
    Log.d("product ids", shoppingDb.getLastProductId()+"");
mobileProducts.get(i).setId(shoppingDb.getLastProductId());
}

/*cursor=shoppingDb.showProducts();
int i=0;
while (!cursor.isAfterLast()){
    if(cursor.getInt(3)==3){
        mobileProducts.get(i).setId(cursor.getInt(0));
        Log.d("iddddddddddddddddddds", mobileProducts.get(i).getId()+"");
        i++;

    }
    cursor.moveToNext();

}*/

 }

        for(int i=0;i<mobileProducts.size();i++){

            Log.d("products id", mobileProducts.get(i).getId()+"");
        }



/*
        if(mobileProducts==null || mobileProducts.size()==0){
            mobileProducts=new ArrayList<>();
            Category category=new Category("Mobiles");
            mobileProducts.add(new Product("mobile samsung fager","the most beautifl samsung ever \n buy it now stupid! ", category,25415,30,R.drawable.samsung,0));
            mobileProducts.add(new Product("mobile lenovo fager","the most beautifl leonvo ever \n buy it now stupid! ", category,121,50,R.drawable.lenovo,0));

            Cursor cursor=shoppingDb.showProducts();
            Cursor cursor1=shoppingDb.showCategories();
            int numofcategories=0;
            while(!cursor1.isAfterLast()){
                numofcategories++;
                   cursor1.moveToNext();
            }
            Log.d("category size", numofcategories+"");

            boolean check =false;
            if(cursor!=null &&cursor.getCount()>numofcategories &&cursor.getCount()>0  ){


                int x=ClothesFragment.clothesProducts.size()+LaptopsFragment.laptopProducts.size();
                for(int i=0;i<x&& !cursor.isAfterLast();i++)cursor.moveToNext();
                if(cursor.isAfterLast()) {

                    check=true;
                }
                else {    for(int i=0;i<mobileProducts.size();i++){
                    mobileProducts.get(i).setId( cursor.getInt(0));
                    mobileProducts.get(i).getCategory().setId(cursor.getInt(3));

                    cursor.moveToNext();

                }
                }}
            if(check)  {
                shoppingDb.createCategory(category);
                int categoryId=shoppingDb.getLastCategoryId();
                for(int i=0;i<mobileProducts.size();i++){
                    mobileProducts.get(i).getCategory().setId(categoryId);
                    shoppingDb.createProduct(mobileProducts.get(i));
                    mobileProducts.get(i).setId(shoppingDb.getLastProductId());
                }
            }

        }
      /*  else{
            Cursor cursor=shoppingDb.showProducts();
            int x=ClothesFragment.clothesProducts.size()+LaptopsFragment.laptopProducts.size();
            for(int i=0;i<x;i++)cursor.moveToNext();

            for(int i=0;i<mobileProducts.size();i++){

                if(cursor.getInt(5)==0){
                    shoppingDb.deleteProduct(mobileProducts.get(i).getId());
                    mobileProducts.remove(mobileProducts.get(i));

                }
                else{
                    mobileProducts.get(i).setQuantity(cursor.getInt(5));

                }
productsAdapter.notifyDataSetChanged();
                cursor.moveToNext();

            }


        }
        Cursor cursor = shoppingDb.showProducts();
        int i=0;
        while(!cursor.isAfterLast() &&i<mobileProducts.size()){

            Log.d("List data", mobileProducts.get(i).getProductName());
            Log.d("List data ID", mobileProducts.get(i).getId()+"");
            Log.d("list data category ID", mobileProducts.get(i).getCategory().getId()+"");
            cursor.moveToNext();
            i++;
        }
        checkdata();
 */   }

}
