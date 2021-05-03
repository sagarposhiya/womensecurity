package com.example.womensecurity.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.womensecurity.Adapter.CallMainAdapter;
import com.example.womensecurity.R;
import com.example.womensecurity.models.Call;
import com.example.womensecurity.models.Register;
import com.example.womensecurity.models.User;
import com.example.womensecurity.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminCallList extends AppCompatActivity {

    private DatabaseReference mDatabase;
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Call> calls = new ArrayList<>();
    RecyclerView recyclerview;
    CallMainAdapter callMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_call_list);

        getSupportActionBar().setTitle("Calls Reports");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        getData();
      //  getCalls();
    }

    private void getCalls() {
        mDatabase.child(Constants.USERS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    for (DataSnapshot snapshot : task.getResult().getChildren()){
                        User user = snapshot.getValue(User.class);
                        users.add(user);
                    }
                    //txtTotal.setText(registers.size() + "");

                }
            }
        });
    }

    private void getData() {
        mDatabase.child(Constants.USERS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    for (DataSnapshot snapshot : task.getResult().getChildren()){
                        User user = snapshot.getValue(User.class);
                        users.add(user);
                    }
                    Log.e("USERS",users.size() + "");
                    //txtTotal.setText(registers.size() + "");
                    callMainAdapter = new CallMainAdapter(AdminCallList.this,users);
                    recyclerview.setAdapter(callMainAdapter);

                }
            }
        });
    }
}