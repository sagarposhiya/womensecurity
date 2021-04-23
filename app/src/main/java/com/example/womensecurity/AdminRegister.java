package com.example.womensecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class AdminRegister extends AppCompatActivity {

    private static final String TAG = "AdminRegister";

    private FirebaseAuth mAuth;
    TextInputEditText inputname;
    TextInputEditText inputmobile;
    TextInputEditText inputemail;
    TextInputEditText inputpass;
    Button btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        getInit();

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputemail.getText().toString();
                String password = inputpass.getText().toString();
                String name = inputname.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(AdminRegister.this, "Please Enter Email address", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)){
                    Toast.makeText(AdminRegister.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {

                    mAuth.createUserWithEmailAndPassword(email, password)
                       .addOnCompleteListener(AdminRegister.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete( Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name).build();

                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete( Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "User profile updated.");
                                                        }
                                                    }
                                                });



                                        AppUtils.setBooleanPreference(AdminRegister.this, Constants.isRegistered,true);
                                        startActivity(new Intent(AdminRegister.this,AdminLogin.class));
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(AdminRegister.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void getInit() {
        mAuth = FirebaseAuth.getInstance();
        inputname = findViewById(R.id.inputname);
        inputmobile = findViewById(R.id.inputmobile);
        inputemail = findViewById(R.id.inputemail);
        inputpass = findViewById(R.id.inputpass);
        btnadd = findViewById(R.id.btnadd);
    }

}