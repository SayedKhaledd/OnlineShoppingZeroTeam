package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineshopping.Email.GmailSender;

public class EmailVerfication extends AppCompatActivity implements View.OnClickListener {
    Button submitCode, emailSubmit;
    EditText editText,editText2;
    String x;
    ShoppingDb shoppingDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
       /* Intent intent=new Intent(EmailVerfication.this, EmailSerivce.);
        startService(intent);*/
        submitCode =(Button) findViewById(R.id.submit);
        emailSubmit=(Button) findViewById(R.id.emailSubmit);
        shoppingDb=new ShoppingDb(this);
        editText=(EditText) findViewById(R.id.verify);
        editText2=(EditText) findViewById(R.id.email);
        x=1000+(int)(Math.random()*1000)+"";

        submitCode.setOnClickListener(this);
        emailSubmit.setOnClickListener(this);

    }
    private boolean sendMessage(final String email, String x) {
        Cursor cursor=shoppingDb.showCustomers();
        boolean check=false;
        while(!cursor.isAfterLast()){
            if(cursor.getString(0).equals(email)){
                check=true;
                break;
            }
            cursor.moveToNext();
        }
        if(check) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Sending Email");
            dialog.setMessage("Please wait");
            dialog.show();
            final String finalX = x;
            Thread sender = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        GmailSender sender = new GmailSender("onlineshoppingzeroteam@gmail.com", "sdsd12345");
                        sender.sendMail("Password Reset",
                                "to reset your password please copy this code \n" + finalX + " for verification",
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
            return true;
        }
        else return false;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.emailSubmit){
            if(! sendMessage(editText2.getText().toString(),x)){
                Toast.makeText(this ,"Email not found",Toast.LENGTH_LONG).show();

            }

        }
        if(view.getId()==R.id.submit){
            String m=editText.getText().toString();
            if(m.equals(x)){
                Intent intent=new Intent(EmailVerfication.this,RestPassword.class);
                Toast.makeText(getApplicationContext(),"nice",Toast.LENGTH_LONG).show();
                intent.putExtra("Email",editText2.getText().toString());
                startActivity(intent);
                finish();
            }
            else  Toast.makeText(getApplicationContext(),"not nice",Toast.LENGTH_LONG).show();
        }
    }
}
