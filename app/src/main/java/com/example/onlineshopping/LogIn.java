package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping.Model.Customer;

import java.io.Serializable;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    EditText email, password;
    Button login;
    TextView createOne, skip, forget;
    String emailString, passwordString;
    ShoppingDb shoppingDb;
    CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.log_in);
        createOne = (TextView) findViewById(R.id.create_one);
        skip = (TextView) findViewById(R.id.skip);
        checkBox = (CheckBox) findViewById(R.id.remember_me);
        forget = (TextView) findViewById(R.id.forget);
        login.setOnClickListener(this);
        createOne.setOnClickListener(this);
        skip.setOnClickListener(this);
        shoppingDb = new ShoppingDb(this);
        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constants.REMEMBER_ME, false)) {
            goToMainActivity(getIdCus(emailString));
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.EMAIL, null);
            editor.putBoolean(Constants.REMEMBER_ME, checkBox.isChecked());
            editor.putInt(Constants.ID, 0);

            editor.apply();
        }
        TextView returned = findViewById(R.id.returned);
        forget.setOnClickListener(this);
        Intent intent = getIntent();
        String result = intent.getStringExtra("Text");
        returned.setText(result);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.log_in) {
            emailString = email.getText().toString();
            passwordString = password.getText().toString();
            Customer customer = new Customer(emailString, passwordString);
            customer.setId(shoppingDb.getLastCustomerId());
            if (check(emailString, passwordString)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.EMAIL, emailString);
                editor.putBoolean(Constants.REMEMBER_ME, checkBox.isChecked());
                editor.putInt(Constants.ID, getIdCus(emailString));

                editor.apply();

                goToMainActivity(getIdCus(emailString));
            } else {
                Toast.makeText(this, "wrong log in", Toast.LENGTH_LONG).show();
            }
        } else if (view.getId() == R.id.create_one) {
            Intent intent = new Intent(LogIn.this, SignUp.class);
            startActivity(intent);
        } else if (view.getId() == R.id.skip) {
            goToMainActivity(getIdCus(emailString));

        }
        if (view.getId() == R.id.forget) {
            Intent intent = new Intent(this, EmailVerfication.class);
            startActivity(intent);
        }
    }

    public boolean check(String email, String password) {

        if (!email.isEmpty() && !password.isEmpty()) {
            Cursor cursor = shoppingDb.showCustomers();
            while (!cursor.isAfterLast()) {
                if (cursor.getString(0).equals(email) && cursor.getString(1).equals(password)) {
                    return true;
                } else {
                    cursor.moveToNext();

                }
            }


        }
        return false;

    }

    private void goToMainActivity(int id) {
        Intent intent = new Intent(LogIn.this, MainActivity.class);
        //intent.putExtra("My Customer", (Serializable) customer);
        intent.putExtra("ID", id);
        startActivity(intent);
        finish();
    }

    public int getIdCus(String email) {
        int id = -1;
        Cursor cursor = shoppingDb.showCustomers();

        while (!cursor.isAfterLast()) {
            if (cursor.getString(0).equals(email)) {
                id = cursor.getInt(2);
            }
            cursor.moveToNext();
        }
        return id;
    }
}