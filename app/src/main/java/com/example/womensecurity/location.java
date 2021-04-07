package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.womensecurity.utils.AppUtils;
import com.example.womensecurity.utils.Constants;
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
}