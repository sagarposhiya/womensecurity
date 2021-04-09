package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.womensecurity.utils.AppUtils;
import com.example.womensecurity.utils.Constants;
import com.example.womensecurity.utils.GPSTracker;
import com.example.womensecurity.views.MainChat;
import com.example.womensecurity.views.MapsActivity;

public class location extends AppCompatActivity {
    TextView txtCountry, txtCode, txtLatitude, txtLongitude;
    TextView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        add=findViewById(R.id.textadd);
        txtCountry=findViewById(R.id.textcountry);
        txtCode=findViewById(R.id.textcode);
        txtLatitude=findViewById(R.id.textlatitude);
        txtLongitude=findViewById(R.id.textlongitude);

        String latitude = AppUtils.getStringPreference(this, Constants.latitude);
        String longitude = AppUtils.getStringPreference(this,Constants.longitude);
        String address = AppUtils.getStringPreference(this,Constants.address);
        String country = AppUtils.getStringPreference(this,Constants.country);
        String code = AppUtils.getStringPreference(this,Constants.code);

        add.setText(address);
        txtCountry.setText(country);
        txtLatitude.setText(latitude);
        txtLongitude.setText(longitude);
        txtCode.setText(code);

    }

    public void onMap(View view){
        Intent i= new Intent(location.this, MapsActivity.class);
        startActivity(i);
    }

    public void refresh(View view) {
        getLocation();
    }

    private void getLocation() {

        GPSTracker gpsTracker = new GPSTracker(this,this);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
            AppUtils.setStringPreference(this, Constants.latitude,stringLatitude);

            String stringLongitude = String.valueOf(gpsTracker.longitude);
            AppUtils.setStringPreference(this, Constants.longitude,stringLongitude);

            String country = gpsTracker.getCountryName(this);
            AppUtils.setStringPreference(this, Constants.country,country);

            String city = gpsTracker.getLocality(this);
            AppUtils.setStringPreference(this, Constants.city,city);

            String postalCode = gpsTracker.getPostalCode(this);

            String code = gpsTracker.getCountryCode(this);
            AppUtils.setStringPreference(this, Constants.code,code);

            String addressLine = gpsTracker.getAddressLine(this);
            AppUtils.setStringPreference(this, Constants.address,addressLine);

            Log.e("LOCATION","Latitude :- " + stringLatitude + "  Longitude :- " + stringLongitude + " \n Country :- " + country + " City :- " + city
                    + "  Address :- " + addressLine);
        }
        else
        {
            gpsTracker.showSettingsAlert();
        }
    }

}