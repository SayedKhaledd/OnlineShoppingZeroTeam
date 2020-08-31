package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.onlineshopping.Model.Customer;

import java.util.Calendar;

public class SignUp extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText name, email, password;
    Button submit, birthday;
    String nameString, emailString, passwordString;
    ShoppingDb shoppingDb;
    String birthdatee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        shoppingDb = new ShoppingDb(this);
        birthday = (Button) findViewById(R.id.birthday);
        birthday.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {
            nameString = name.getText().toString();
            emailString = email.getText().toString();
            passwordString = password.getText().toString();
            if (addCustomer(nameString, emailString, passwordString)) {
                Intent intent = new Intent(this, LogIn.class);
                startActivity(intent);
                finish();

            }
        }
        if (view.getId() == R.id.birthday) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
    }

    public boolean addCustomer(String nameString, String emailString, String passwordString) {
        Customer customer = new Customer(nameString, emailString, passwordString);
        //customer.setBirthdate(birthdatee);
        shoppingDb.createCustomer(customer);
        Log.d("customer id", shoppingDb.getLastCustomerId() + "");
        return true;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = day + "/" + (month + 1) + "/" + year;
        birthday.setText(date);
        birthdatee = date;
    }
}
