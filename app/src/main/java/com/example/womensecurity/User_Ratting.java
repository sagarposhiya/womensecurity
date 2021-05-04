package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.womensecurity.models.Call;
import com.example.womensecurity.models.Ratting;
import com.example.womensecurity.utils.AppUtils;
import com.example.womensecurity.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.Date;

public class User_Ratting extends AppCompatActivity {
    private static final String TAG = "App";
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__ratting);
        mDatabase = FirebaseDatabase.getInstance().getReference();

       final SmileRating smilerating = findViewById(R.id.smile_rating);
        smilerating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {

                int level = smilerating.getRating();

                switch (smiley){
                    case SmileRating.BAD:
                        Log.i(TAG, "Bad");
                        break;
                    case SmileRating.GOOD:
                        Log.i(TAG, "Good");
                        break;
                    case SmileRating.GREAT:
                        Log.i(TAG, "Great");
                        break;
                    case SmileRating.OKAY:
                        Log.i(TAG, "Okay");
                        break;
                    case SmileRating.TERRIBLE:
                        Log.i(TAG, "Terrible");
                        break;
                }
            }
        });

        smilerating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                Toast.makeText(User_Ratting.this, "Selected Rating", Toast.LENGTH_SHORT).show();
                switch (level){
                    case SmileRating.BAD:
                        Log.i(TAG, "Bad");
                        addRatting("Bad");
                        break;
                    case SmileRating.GOOD:
                        Log.i(TAG, "Good");
                        addRatting("Good");
                        break;
                    case SmileRating.GREAT:
                        Log.i(TAG, "Great");
                        addRatting("Great");
                        break;
                    case SmileRating.OKAY:
                        Log.i(TAG, "Okay");
                        addRatting("Okay");
                        break;
                    case SmileRating.TERRIBLE:
                        Log.i(TAG, "Terrible");
                        addRatting("Terrible");
                        break;
                }
            }
        });
    }

    private void addRatting(String rattingValue) {

        String userId = AppUtils.getStringPreference(User_Ratting.this, Constants.userId);
        String id = AppUtils.getStringPreference(User_Ratting.this, Constants.RattingCount);
        if (id == ""){
            id = "0";
        } else {
            int temp = Integer.valueOf(id);
            temp = temp + 1;
            id = String.valueOf(temp);
        }
        AppUtils.setStringPreference(User_Ratting.this,Constants.RattingCount,id);

        Ratting ratting = new Ratting();
        ratting.setRatting(rattingValue);
        ratting.setTime(String.valueOf(new Date().getTime()));

        mDatabase.child(Constants.USERS).child(userId).child(Constants.RATTING).child("0").setValue(ratting);

    }
}