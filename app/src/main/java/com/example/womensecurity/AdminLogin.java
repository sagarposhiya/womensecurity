package com.example.womensecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.womensecurity.utils.AppUtils;
import com.example.womensecurity.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AdminLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextInputEditText username;
    TextInputEditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getInit();
    }

    private void getInit() {
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
    }

    public void signUp(View view){
        Intent intent= new Intent(AdminLogin.this, AdminRegister.class);
        startActivity(intent);
    }
    public void login(View view){
        String email = username.getText().toString();
        String password = pass.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(AdminLogin.this, "Please Enter Email address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)){
            Toast.makeText(AdminLogin.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        } else {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(AdminLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                AppUtils.setBooleanPreference(AdminLogin.this, Constants.isAdmin,true);
                                AppUtils.setBooleanPreference(AdminLogin.this, Constants.isLogin,true);
                                startActivity(new Intent(AdminLogin.this,AdminHome.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(AdminLogin.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public void forgotPass(View view){
        Intent i= new Intent(AdminLogin.this, AdminForgotPass.class);
        startActivity(i);
    }
}