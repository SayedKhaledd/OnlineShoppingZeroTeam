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

public class LaptopsFragment extends Fragment implements ProductOnClickListener {
    public static LaptopsFragment newInstance() {
        return new LaptopsFragment();
    }
    ShoppingDb shoppingDb;
    SharedPreferences sharedPreferences;
    ProductsAdapter productsAdapter;
    public  ArrayList<Product> laptopProducts=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        shoppingDb   =new ShoppingDb(getContext());
laptopsProducts();
        try {
            sharedPreferences = this.getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        catch (Exception e){
            Log.d("exception", e.getMessage());
            sharedPreferences=null;
        }
        View view = inflater.inflate(R.layout.fragment_laptops, container, false);
         productsAdapter=new ProductsAdapter(laptopProducts,getContext(),this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyler);
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
            while(!cursor.isAfterLast()){
                if(cursor.getInt(1)==product.getId()&& cursor.getInt(0)==sharedPreferences.getInt(Constants.ID,0)){
                    check=true;

                }
                cursor.moveToNext();
            }

            if(!check){
                ShoppingCart shoppingCart=new ShoppingCart(product.getId(),1,sharedPreferences.getInt(Constants.ID,0));
                shoppingDb.createShoppingCart(shoppingCart);
            }
        }

    }




    public void laptopsProducts(){
        Cursor cursor1=shoppingDb.showProducts();
        if(laptopProducts.size()==0)   while(!cursor1.isAfterLast() ){
            if(cursor1.getInt(3)==2){
                laptopProducts.add(  new Product(cursor1.getInt(0), cursor1.getString(1), cursor1.getString(2), new Category(cursor1.getInt(3)), cursor1.getDouble(4),  cursor1.getInt(5), cursor1.getInt(6), cursor1.getDouble(7)));

            }
            cursor1.moveToNext();

        }
        if(laptopProducts.size()==0){
            Category category=new Category(2);
            category.setName("Laptop");
            shoppingDb.createCategory(category);
            laptopProducts.add(new Product("Laptop Lenovo ThinkPad","With the Lenovo 15.6 ThinkPad E15 Laptop, you'll be able to stay productive while on the go.\n The 10th Gen 1.6 GHz Intel Core i5-10210U Quad-Core processor and 8GB of 2666 MHz of DDR4 RAM provide the performance you need to run productivity apps. It also has a 1TB 5400 rpm 2.5 HDD. ", category,20000,3,R.drawable.lenovolap,"45454545"));
            laptopProducts.add(new Product("Laptop AlienWare ","Dell Alienware 17 is a Windows 10 Home laptop with a 17.30-inch display \n that has a resolution of 1920x1080 pixels.\n It is powered by a Core i7 processor and it comes with 16GB of RAM. \nThe Dell Alienware 17 packs 1TB of HDD storage. Graphics are powered by Intel Integrated  Graphics 5500. ", category,90000,50,R.drawable.alienware,"22202221"));
            laptopProducts.add(new Product("Laptop Dell Latitude Dell Inspiron ","Dell Inspiron 3580 Intel Core i5-8265U 4GB Ram 1TB HDD VGA AMD M520 2GB DOS.\n Dell Latitude 3400 Intel 8th Gen Core i5-8265U Intel UHD 620 Graphics 4GB ", category,85000,50,R.drawable.dell,"hnds8"));

            Cursor cursor=shoppingDb.showProducts();

            for (int i=0;i<laptopProducts.size();i++){
                shoppingDb.createProduct(laptopProducts.get(i));
                laptopProducts.get(i).setId(shoppingDb.getLastProductId());
                laptopProducts.get(i).getCategory().setId(2);
            }

        }


        /*if(laptopProducts==null || laptopProducts.size()==0){
            laptopProducts=new ArrayList<>();
            Category category=new Category("Laptops");
            laptopProducts.add(new Product("laptop dell fager","the most beautifl laptop ever \n buy it now stupid! ", category,2000,3,R.drawable.lap1,0));
            laptopProducts.add(new Product("laptop lenovo fager","the most beautifl laptop ever \n buy it now stupid! ", category,10.5,50,R.drawable.lap2,0));

            Cursor cursor=shoppingDb.showProducts();
         int numofcategories=0;
         Cursor cursor1=shoppingDb.showCategories();

         while(!cursor1.isAfterLast()){
             numofcategories++;
             cursor1.moveToNext();
         }
         Log.d("category size", numofcategories+"");
boolean check =false;
         if(cursor!=null &&cursor.getCount()>numofcategories &&cursor.getCount()>0  ){


                int x=ClothesFragment.clothesProducts.size();
                for(int i=0;i<x&& !cursor.isAfterLast();i++)cursor.moveToNext();
                if(cursor.isAfterLast()) {

                    check=true;
                }
            else {    for(int i=0;i<laptopProducts.size();i++){
                    laptopProducts.get(i).setId( cursor.getInt(0));
                    laptopProducts.get(i).getCategory().setId(cursor.getInt(3));

                    cursor.moveToNext();

                }
            }}
             if(check)  {
                shoppingDb.createCategory(category);
                int categoryId=shoppingDb.getLastCategoryId();
                for(int i=0;i<laptopProducts.size();i++){
                    laptopProducts.get(i).getCategory().setId(categoryId);
                    shoppingDb.createProduct(laptopProducts.get(i));
                    laptopProducts.get(i).setId(shoppingDb.getLastProductId());
                }
            }

        }

   /*  else{
         Cursor cursor=shoppingDb.showProducts();
         int x=ClothesFragment.clothesProducts.size();
         for(int i=0;i<x;i++)cursor.moveToNext();

         for(int i=0;i<laptopProducts.size();i++){

             if(cursor.getInt(5)==0){
                 shoppingDb.deleteProduct(laptopProducts.get(i).getId());
                 laptopProducts.remove(laptopProducts.get(i));
             }
             else{
                 laptopProducts.get(i).setQuantity(cursor.getInt(5));

             }
             productsAdapter.notifyDataSetChanged();

             cursor.moveToNext();

         }


     }
        Cursor cursor = shoppingDb.showProducts();
        int i=0;
        while(!cursor.isAfterLast() &&i<laptopProducts.size()){

            Log.d("List data", laptopProducts.get(i).getProductName());
            Log.d("List data Id", laptopProducts.get(i).getId()+"");
            Log.d("list data category ID", laptopProducts.get(i).getCategory().getId()+"");

            cursor.moveToNext();
            i++;
        }
        checkdata();*/
    }

}
