package com.example.womensecurity.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.womensecurity.R;

public class AdminCallList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_call_list);

        getSupportActionBar().setTitle("Calls Reports");
    }
}