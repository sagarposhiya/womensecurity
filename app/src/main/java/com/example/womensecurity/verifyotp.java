package com.example.womensecurity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.womensecurity.utils.AppUtils;
import com.example.womensecurity.utils.Constants;
import com.example.womensecurity.views.SacnnerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


public class verifyotp extends AppCompatActivity {

    Button verifyOTP;
    EditText otpcode;
    private String OTP;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Verify OTP");
        progressDialog.setMessage("Please Wait");

        verifyOTP = findViewById(R.id.btnVerifyOTP);
        otpcode = findViewById(R.id.inputcode);

        firebaseAuth = FirebaseAuth.getInstance();

        OTP = getIntent().getStringExtra("verificationId");
        verifyOTP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String verification_code = otpcode.getText().toString();
                if (!verification_code.isEmpty()) {
                    progressDialog.show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP, verification_code);
                    signIn(credential);
                } else {
                    Toast.makeText(verifyotp.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signIn(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete( Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    AppUtils.setStringPreference(verifyotp.this, Constants.userId,task.getResult().getUser().getUid());
                    AppUtils.setBooleanPreference(verifyotp.this,Constants.isLogin,true);
                    sendToMain();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(verifyotp.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if (currentUser != null) {
//            sendToMain();
//        }
    }
    private void sendToMain() {
        startActivity(new Intent(verifyotp.this, SacnnerActivity.class));
        finish();
    }
}
