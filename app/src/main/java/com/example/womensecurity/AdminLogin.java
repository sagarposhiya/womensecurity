package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    }

    public void signUp(View view){
        Intent intent= new Intent(AdminLogin.this, AdminRegister.class);
        startActivity(intent);
    }
    public void login(View view){
        Intent intent= new Intent(AdminLogin.this, AdminHome.class);
        startActivity(intent);
    }
    public void forgotPass(View view){
        Intent i= new Intent(AdminLogin.this, AdminForgotPass.class);
        startActivity(i);
    }
}