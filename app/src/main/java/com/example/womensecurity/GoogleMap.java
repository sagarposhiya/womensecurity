 package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.womensecurity.utils.AppUtils;
import com.example.womensecurity.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;

 public class GoogleMap extends FragmentActivity implements OnMapReadyCallback {


   com.google.android.gms.maps.GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {

//        String latitude = AppUtils.getStringPreference(this, Constants.latitude);
//        String longitude = AppUtils.getStringPreference(this,Constants.latitude);
//
//
//        NumberFormat formatter = new DecimalFormat("##.00");

        mMap = googleMap;
       // LatLng Surat = new LatLng(21.231599, 72.891680);
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Surat"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}