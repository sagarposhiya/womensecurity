package com.example.womensecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.womensecurity.models.Register;
import com.example.womensecurity.utils.Constants;
import com.example.womensecurity.views.AdminCallList;
import com.example.womensecurity.views.AdminMessageList;
import com.example.womensecurity.views.AdminRattingList;
import com.example.womensecurity.views.AdminUserList;
import com.example.womensecurity.views.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AdminHome extends AppCompatActivity {
    private DrawerLayout drawer;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    private DatabaseReference mDatabase;
    CardView cardWoman,cardCall,cardMessage,cardRatting;
    ArrayList<Register> registers = new ArrayList<>();
    TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        txtTotal = findViewById(R.id.txtTotal);
        getData();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        cardWoman = findViewById(R.id.cardWoman);
        cardCall = findViewById(R.id.cardCall);
        cardRatting = findViewById(R.id.cardRatting);
        cardMessage = findViewById(R.id.cardMessage);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // to make the Navigation drawer icon always appear on the action bar
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(AdminHome.this, AdminHome.class));
                        break;
                    case R.id.nav_account:
                        startActivity(new Intent(AdminHome.this, AdminProfile.class));
                        break;
//            case R.id.nav_story:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new SearchFragment()).commit();
//                break;
//            case R.id.nav_tips:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new SearchFragment()).commit();
//                break;
//            case R.id.nav_rating:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new SearchFragment()).commit();
//                break;
//            case R.id.nav_logout:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new SearchFragment()).commit();
//                break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });


        cardWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, AdminUserList.class));
            }
        });

        cardRatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, AdminRattingList.class));

            }
        });

        cardCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, AdminCallList.class));
            }
        });

        cardMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, AdminMessageList.class));
            }
        });

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
                    txtTotal.setText(registers.size() + "");

                }
            }
        });
    }

}