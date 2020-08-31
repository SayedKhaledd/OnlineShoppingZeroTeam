package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, View.OnClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    EditText addressText;
    LocationManager locationManager;
    Button getLocation,ordernow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        addressText = (EditText) findViewById(R.id.marker_location_edit_text);
        getLocation = (Button) findViewById(R.id.current_location_btn);
ordernow=(Button) findViewById(R.id.order_now);
ordernow.setOnClickListener(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, this);

        } catch (SecurityException ex) {
            Toast.makeText(getApplicationContext(), "YOU ARE NOT ALLOWED TO ACCESS THE C.L", Toast.LENGTH_LONG).show();


        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, this);
        }
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 8));
        getLocation.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
      mMap.clear();
        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> addressList;
        Location loc = null;

        try {
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        }
        catch (SecurityException ex){
            Toast.makeText(getApplicationContext(),"YOU ARE NOT ALLOWED TO ACCESS THE C.L",Toast.LENGTH_LONG).show();
        }
        if(loc != null){
            LatLng myPosition = new LatLng(loc.getLatitude(),loc.getLongitude());

            try {
                addressList = coder.getFromLocation(myPosition.latitude,myPosition.longitude,1);
                if(!addressList.isEmpty()){
                    String address = "";
                    for(int i =0; i <=addressList.get(0).getMaxAddressLineIndex();i++)
                        address += addressList.get(0).getAddressLine(i)+" , ";

                    mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location").snippet(address)).setDraggable(true);
                    addressText.setText(address);
                }
            } catch (IOException e) {
                mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location"));

            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));
        }else {
            Toast.makeText(getApplicationContext(),"Please wait untill your position is determine",Toast.LENGTH_LONG).show(); }
        mMap.setOnMarkerDragListener(this);

    if (view.getId()==R.id.order_now){
        Intent intent=new Intent(MapsActivity.this, OrderActivity.class);
        intent.putExtra("Address",addressText.getText().toString());
        Log.d("addressssssss", addressText.getText().toString());
        startActivity(intent);


    }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this,location.getLatitude() + ", " + location.getLongitude(),Toast.LENGTH_LONG).show();

    }



    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Toast.makeText(this,"GPS Enabled",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(this,"GPS Disabled",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> addressList;
        try {
            addressList =coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
            if(!addressList.isEmpty()){
                String address = "";
                for(int i =0; i <=addressList.get(0).getMaxAddressLineIndex();i++)
                    address += addressList.get(0).getAddressLine(i)+" , ";
                addressText.setText(address);
            }else {
                Toast.makeText(getApplicationContext(),"No address for this Location",Toast.LENGTH_LONG)
                        .show();
                addressText.getText().clear();
            }
        }catch (IOException e){
            Toast.makeText(getApplicationContext(),"Can not get the address , Check your network",Toast.LENGTH_LONG).show();
        }
    }
}