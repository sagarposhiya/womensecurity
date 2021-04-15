package com.example.womensecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;


public class AdminHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        drawer = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.nav_open, R.string.nav_close);

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminDashboardFragment()).commit();
                break;
//            case R.id.nav_account:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new FavoritesFragment()).commit();
//                break;
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
}