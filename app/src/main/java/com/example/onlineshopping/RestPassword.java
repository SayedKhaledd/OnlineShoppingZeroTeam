package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RestPassword extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
    Button button;
    ShoppingDb shoppingDb;
    SharedPreferences sharedPreferences;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_password);
        editText=(EditText) findViewById(R.id.password);
        button=(Button)findViewById(R.id.change);
        button.setOnClickListener(this);
        shoppingDb=new ShoppingDb(this);
        Intent intent=getIntent();
        email=intent.getStringExtra("Email");
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.change){
            String password= editText.getText().toString();
            sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);

            shoppingDb.updatePassword(getIdCus(email),password);
            Intent intent=new Intent(RestPassword.this,LogIn.class);
            startActivity(intent);
            finish();
        }
    }
    public  int getIdCus(String email) {
        int id = -1;
        Cursor cursor = shoppingDb.showCustomers();

        while (!cursor.isAfterLast()) {
            if (cursor.getString(0).equals(email)){
                id = cursor.getInt(2);
            }
            cursor.moveToNext();
        }
        return id;
    }
}