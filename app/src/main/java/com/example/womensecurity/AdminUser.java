package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class AdminUser extends AppCompatActivity {
Button btnUser, btnAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        btnAdmin= findViewById(R.id.btnAdmin);
        btnUser= findViewById(R.id.btnUser);

    }

    public void onUser(View view) {
        Intent i = new Intent(AdminUser.this,splashscreen.class);
        startActivity(i);
    }

    public void onAdmin(View view) {
        Intent i = new Intent(AdminUser.this,AdminLogin.class);
        startActivity(i);
    }
}