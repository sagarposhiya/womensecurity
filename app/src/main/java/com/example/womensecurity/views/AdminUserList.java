package com.example.womensecurity.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.womensecurity.Adapter.UserAdapter;
import com.example.womensecurity.R;
import com.example.womensecurity.models.Register;
import com.example.womensecurity.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminUserList extends AppCompatActivity {

    RecyclerView recyclerview;
    private DatabaseReference mDatabase;
    ArrayList<Register> registers = new ArrayList<>();
    UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);
        getSupportActionBar().setTitle("User Reports");
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getData();
    }

    private void getData() {
        mDatabase.child(Constants.REGISTERED).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    for (DataSnapshot snapshot : task.getResult().getChildren()){
                        Register register = snapshot.getValue(Register.class);
                        registers.add(register);
                    }
                    adapter = new UserAdapter(AdminUserList.this,registers);
                    recyclerview.setAdapter(adapter);
                   // txtTotal.setText(registers.size() + "");

                }
            }
        });
    }
}