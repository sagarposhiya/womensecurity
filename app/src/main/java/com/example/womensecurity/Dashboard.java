package com.example.womensecurity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.womensecurity.databse.DatabaseClient;
import com.example.womensecurity.models.AdharCard;
import com.example.womensecurity.models.Register;

import java.util.List;

public class Dashboard extends AppCompatActivity
{
    ImageView siren;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Register register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getTasks();
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // play siren
        siren = (ImageView) findViewById(R.id.siren);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.sirenplay);

        siren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });
    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSecurityCall(View view){
        Intent in=new Intent(Dashboard.this, SecurityCall.class);
        startActivity(in);
    }
    public void onemergencycall(View view)
    {
        // Create an alert builder
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setTitle("Emergency Contact");

        // set the custom layout
        final View customLayout
                = getLayoutInflater()
                .inflate(
                        R.layout.dialogbox,
                        null);
        builder.setView(customLayout);

        // create and show
        // the alert dialog
        AlertDialog dialog
                = builder.create();

        TextView txtOne = customLayout.findViewById(R.id.txtOne);
        TextView txtTwo = customLayout.findViewById(R.id.txtTwo);
        TextView txtThree = customLayout.findViewById(R.id.txtThree);

        if (register != null) {
            txtOne.setText(register.getEmer1());
            txtTwo.setText(register.getEmer2());
            txtThree.setText(register.getEmer3());
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        dialog.show();
    }

    // Do something with the data
    // coming from the AlertDialog
    private void sendDialogDataToActivity(String data)
    {
        Toast.makeText(this,
                data,
                Toast.LENGTH_SHORT)
                .show();
    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<Register>> {

            @Override
            protected List<Register> doInBackground(Void... voids) {
                List<Register> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .registerDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<Register> tasks) {
                super.onPostExecute(tasks);
                if (tasks.size() > 0){
                    register = tasks.get(0);
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}