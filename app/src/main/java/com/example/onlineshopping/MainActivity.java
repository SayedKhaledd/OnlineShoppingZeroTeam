package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping.Fragments.HomeFragment;
import com.example.onlineshopping.Fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DrawerLayout drawerLayout;
    public static Boolean homeFragment;
    private SharedPreferences sharedPreferences;
    private CircleImageView profileImageDrawer;
    private final int IMAGE_PICK_CODE = 8888;
    private ShoppingDb shoppingDb;
    String email, name;
    TextView nameDrawer,emailDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = false;

        shoppingDb = new ShoppingDb(this);


        Intent intent = getIntent();
        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        email=getEmail();
        String uriStr = getUriStr();
        name =getname();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home__);
        }

        View headerView = navigationView.inflateHeaderView(R.layout.nev_header);
        profileImageDrawer = headerView.findViewById(R.id.profile_image_drawer);
        nameDrawer = headerView.findViewById(R.id.user_name_Drawer);
        emailDrawer = headerView.findViewById(R.id.user_email_Drawer);
        profileImageDrawer.setOnClickListener(this);
        if(email != null && name!=null){
            nameDrawer.setText(name);
            emailDrawer.setText(email);
        }

        if (uriStr != null){

            profileImageDrawer.setImageURI(Uri.parse(uriStr));

        }

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri imageUri = data.getData();
                shoppingDb.updateCustomerPic(getIdCus(email),imageUri.toString());
                profileImageDrawer.setImageURI(imageUri);
            }
        }
    }
    public  String getEmail(){
        String email = sharedPreferences.getString(Constants.EMAIL,null);
        return email;
    }
    public String getUriStr(){
        String uri = "";
        Cursor cursor = shoppingDb.showCustomers();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(0).equals(email)){
                uri = cursor.getString(3);
            }
            cursor.moveToNext();
        }

        return uri;
    }
    public String getname(){
        String name = "";
        Cursor cursor = shoppingDb.showCustomers();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(0).equals(email)){
                name = cursor.getString(4);
            }
            cursor.moveToNext();
        }

        return name;
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


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START) || homeFragment) {

            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

                drawerLayout.closeDrawer(GravityCompat.START);
            }
            if (homeFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            }
        } else
            super.onBackPressed();

    }

    //  @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;

            case R.id.home__:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.exist:
                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                editor1.clear();
                editor1.apply();
                finish();
                break;
            case R.id.log_out:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(MainActivity.this,LogIn.class);
                startActivity(intent);
                finish();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart_menu:
                Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
                return true;
            case R.id.search_menu:
                Intent intent2 = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent2);
                return true;}


        return false;
    }


}
