package com.example.womensecurity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.womensecurity.databse.DatabaseClient;
import com.example.womensecurity.models.AdharCard;
import com.example.womensecurity.models.Register;
import com.example.womensecurity.utils.AppUtils;
import com.example.womensecurity.utils.Constants;
import com.example.womensecurity.utils.GPSTracker;
import com.example.womensecurity.views.MainChat;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity
{
    ImageView siren;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private static final int CALL_PHONE = 101;
    Register register;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //if(!checkCallPermission()){return;}

        checkCallPermission();;
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
        LinearLayout llSiren = findViewById(R.id.llSiren);
       // final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.sirenplay);

        llSiren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListening();
            }
        });

        siren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mediaPlayer.start();
              //  startListening();
            }
        });

        getLocation();
    }

    private void startListening() {

        String speechLan = AppUtils.getStringPreference(this, Constants.SPEECH_CODE);
        if (TextUtils.isEmpty(speechLan)) {
            speechLan = "en-US";
        }
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speck Now");

        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        mSpeechRecognizer.setRecognitionListener(listener);

        //if (!mIslistening) {
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
      //  }

    }

    protected class SpeechRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(Dashboard.this, "Listening", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {
            Log.e("ERROR",error + " ");
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String result = matches.get(0);
            if (result.contains("help") || result.contains("save")){
                final MediaPlayer mediaPlayer = MediaPlayer.create(Dashboard.this,R.raw.sirenplay);
                mediaPlayer.start();
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }

    private void getLocation() {

        GPSTracker gpsTracker = new GPSTracker(this,this);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
            AppUtils.setStringPreference(this, Constants.latitude,stringLatitude);

            String stringLongitude = String.valueOf(gpsTracker.longitude);
            AppUtils.setStringPreference(this, Constants.longitude,stringLongitude);

            String country = gpsTracker.getCountryName(this);
            AppUtils.setStringPreference(this, Constants.country,country);

            String city = gpsTracker.getLocality(this);
            AppUtils.setStringPreference(this, Constants.city,city);

            String postalCode = gpsTracker.getPostalCode(this);

            String code = gpsTracker.getCountryCode(this);
            AppUtils.setStringPreference(this, Constants.code,code);

            String addressLine = gpsTracker.getAddressLine(this);
            AppUtils.setStringPreference(this, Constants.address,addressLine);

            Log.e("LOCATION","Latitude :- " + stringLatitude + "  Longitude :- " + stringLongitude + " \n Country :- " + country + " City :- " + city
                + "  Address :- " + addressLine);
        }
        else
        {
            gpsTracker.showSettingsAlert();
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Runtime permission
    public boolean checkCallPermission (){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS}, CALL_PHONE);
            return false;
        }
        return true;
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

        final TextView txtOne = customLayout.findViewById(R.id.txtOne);
        final TextView txtTwo = customLayout.findViewById(R.id.txtTwo);
        final TextView txtThree = customLayout.findViewById(R.id.txtThree);

        if (register != null) {
            txtOne.setText(register.getEmer1());
            txtTwo.setText(register.getEmer2());
            txtThree.setText(register.getEmer3());
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

        txtOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberOne = txtOne.getText().toString();
                // Call
                Intent callIntent1 = new Intent(Intent.ACTION_CALL);
                callIntent1.setData(Uri.parse("tel:"+numberOne));

                if (ActivityCompat.checkSelfPermission(Dashboard.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent1);
            }
        });

        txtTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String numberTwo = txtTwo.getText().toString();

                Intent callIntent2= new Intent(Intent.ACTION_CALL);
                callIntent2.setData(Uri.parse("tel:"+numberTwo));

                if (ActivityCompat.checkSelfPermission(Dashboard.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent2);
            }
        });

        txtThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberThree = txtThree.getText().toString();

                Intent callIntent3= new Intent(Intent.ACTION_CALL);
                callIntent3.setData(Uri.parse("tel:"+numberThree));

                if (ActivityCompat.checkSelfPermission(Dashboard.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent3);
            }
        });

        dialog.show();
    }
    public void onlocation(View view){
            Intent i=new Intent(Dashboard.this,location.class);
            startActivity(i);
    }

    public void onChat(View view){
        Intent i= new Intent(Dashboard.this, MainChat.class);
        startActivity(i);
    }

    public void onRecord(View View){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 100);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultcode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultcode, data);
        if (resultcode == RESULT_OK && requestCode == 1) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            VideoView videoView = new VideoView(this);
            assert data != null;
            videoView.setVideoURI(data.getData());
            videoView.start();
            builder.setView(videoView).show();
        }
    }

    public void onEmergencyMessage(View view){

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

        final TextView txtOne = customLayout.findViewById(R.id.txtOne);
        final TextView txtTwo = customLayout.findViewById(R.id.txtTwo);
        final TextView txtThree = customLayout.findViewById(R.id.txtThree);

        if (register != null) {
            txtOne.setText(register.getEmer1());
            txtTwo.setText(register.getEmer2());
            txtThree.setText(register.getEmer3());
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

        txtOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberOne = txtOne.getText().toString();
                AppUtils.sendSMS(numberOne,AppUtils.getMessageFormate(Dashboard.this));
            }
        });

        txtTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String numberTwo = txtTwo.getText().toString();

                AppUtils.sendSMS(numberTwo,AppUtils.getMessageFormate(Dashboard.this));
            }
        });

        txtThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberThree = txtThree.getText().toString();
                AppUtils.sendSMS(numberThree,AppUtils.getMessageFormate(Dashboard.this));
            }
        });

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