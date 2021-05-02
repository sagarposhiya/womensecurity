package com.example.womensecurity.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.womensecurity.R;

public class AdminMessageList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_message_list);
        getSupportActionBar().setTitle("Message Reports");
    }
}