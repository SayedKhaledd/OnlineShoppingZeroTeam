package com.example.onlineshopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.example.onlineshopping.Fragments.ClothesFragment;
import com.example.onlineshopping.Model.Product;
import com.example.onlineshopping.Model.ShoppingCart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements ProductOnClickListener, TextWatcher {
    private final int VOICE_REQUEST = 1999;
    private List<Product> productList;
    private ShoppingDb shoppingDb;
    private ProductsAdapter productsAdapter;
    EditText searchText;
    SharedPreferences sharedPreferences;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        searchText = findViewById(R.id.search_edit_text);
        shoppingDb = new ShoppingDb(this);
        productList = new ArrayList<>();
        productsAdapter = new ProductsAdapter(productList, this, this);
        searchText.addTextChangedListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(productsAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.voice:
                id = R.id.voice;
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, VOICE_REQUEST);
                return true;
            case R.id.barcode:
                id = R.id.barcode;
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setPrompt("SCAN");
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
                return true;
        }


        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (id == R.id.barcode) {
            IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (Result != null) {
                if (Result.getContents() == null) {
                    Log.d("MainActivity", "cancelled scan");
                    Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("MainActivity", "Scanned");
                    Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                    searchText.setText(Result.getContents());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == VOICE_REQUEST && resultCode == RESULT_OK) {
                if (data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result != null) {
                        searchText.setText(result.get(0));

                    }
                }
            }
        }

    }


    @Override
    public void productAddOnClickListener(Product product) {
        if(sharedPreferences==null || sharedPreferences.getInt(Constants.ID,0)==0 || sharedPreferences.getString(Constants.EMAIL,null)==null||  sharedPreferences.getString(Constants.EMAIL,null).equals("")){
            if (sharedPreferences.getString(Constants.EMAIL, null)==null ||sharedPreferences.getString(Constants.EMAIL, null).equals("") && sharedPreferences.getInt(Constants.ID,0)==0  ) {
                Intent intent=new Intent(this, LogIn.class);
                Log.d("shopping cart", "please log in ");
                Toast.makeText(this,"Please log in first",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }

        }

        else if (product.getQuantity()==0){
            Toast.makeText(this,"this item is empty right now , check later",Toast.LENGTH_LONG).show();

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



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        showProduct();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void showProduct() {

        String key = searchText.getText().toString();
        if (!key.isEmpty()) {
            productList.clear();
            Log.d("key",key);
            Cursor cursor = shoppingDb.showProducts();
            while (!cursor.isAfterLast()) {
                Log.d("Important", cursor.getInt(0)+"");
                Log.d("Important", cursor.getString(1));
                Log.d("Important", cursor.getString(2));
                Log.d("Important", cursor.getInt(3)+"");
                Log.d("Important", cursor.getDouble(4)+"");
                Log.d("Important", cursor.getInt(5)+"");
                Log.d("Important", cursor.getInt(6)+"");

                if (cursor.getString(1).contains(key) || cursor.getString(8).equals(key)) {
                    productList.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(4), cursor.getInt(5), cursor.getInt(6)));
                    Log.d("Important", cursor.getString(1));
                }
                cursor.moveToNext();


            }
            Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_LONG);
            productsAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_LONG);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}