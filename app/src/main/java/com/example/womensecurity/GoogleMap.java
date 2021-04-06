package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMap extends FragmentActivity implements OnMapReadyCallback {


    com.google.android.gms.maps.GoogleMap mapAPI;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAPI);

        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        mapAPI = googleMap;
        LatLng Surat = new LatLng(21.231599, 72.891680);

        mapAPI.addMarker(new MarkerOptions().position(Surat).title("Surat"));

        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(Surat));
    }
}