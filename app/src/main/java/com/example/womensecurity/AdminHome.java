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

import com.example.womensecurity.views.AdminCallList;
import com.example.womensecurity.views.AdminMessageList;
import com.example.womensecurity.views.AdminUserList;
import com.example.womensecurity.views.HomeActivity;
import com.google.android.material.navigation.NavigationView;


public class AdminHome extends AppCompatActivity {
    private DrawerLayout drawer;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    CardView cardWoman,cardCall,cardMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        cardWoman = findViewById(R.id.cardWoman);
        cardCall = findViewById(R.id.cardCall);
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

}