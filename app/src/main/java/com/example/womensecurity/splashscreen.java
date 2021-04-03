package com.example.womensecurity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class splashscreen extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slashscreen);

        final EditText inputMobile= findViewById(R.id.inputMobile);
        final Button btnGetOTP= findViewById(R.id.btnGetOTP);

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.RECORD_AUDIO},
                    1001);
            return;
        }

        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(splashscreen.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                btnGetOTP.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" +inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        splashscreen.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential){
                                progressBar.setVisibility(View.GONE);
                                btnGetOTP.setVisibility(View.VISIBLE);
                            }

                            public void onVerificationFailed(@NonNull FirebaseException e){
                                progressBar.setVisibility(View.GONE);
                                btnGetOTP.setVisibility(View.VISIBLE);
                                Toast.makeText(splashscreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                btnGetOTP.setVisibility(View.VISIBLE);
                                Intent intent=new Intent(splashscreen.this, verifyotp.class);
                                intent.putExtra("mobile", inputMobile.getText().toString());
                                intent.putExtra("verificationId", verificationId);
                                startActivity(intent);
                            }
                        }
                );
            }
        });
    }
}
