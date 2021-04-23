package com.example.womensecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.womensecurity.views.HomeActivity;
import com.google.android.material.navigation.NavigationView;


public class AdminHome extends AppCompatActivity {
    private DrawerLayout drawer;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer,toolbar, R.string.nav_open, R.string.nav_close);

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // to make the Navigation drawer icon always appear on the action bar
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
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
}

}