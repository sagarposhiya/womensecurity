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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.womensecurity.databse.DatabaseClient;
import com.example.womensecurity.models.AdharCard;
import com.example.womensecurity.models.Call;
import com.example.womensecurity.models.Message;
import com.example.womensecurity.models.Register;
import com.example.womensecurity.models.User;
import com.example.womensecurity.utils.AppUtils;
import com.example.womensecurity.utils.Constants;
import com.example.womensecurity.utils.GPSTracker;
import com.example.womensecurity.views.HomeActivity;
import com.example.womensecurity.views.MainChat;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {
    ImageView siren;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private static final int CALL_PHONE = 101;
    Register register;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    NavigationView navigationView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //if(!checkCallPermission()){return;}
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getUser();

        checkCallPermission();
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer

        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.nav_view);
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
                Toast.makeText(Dashboard.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        siren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startListening();
//               Toast.makeText(Dashboard.this, "Clicked", Toast.LENGTH_SHORT).show();
//                mediaPlayer.start();
//                  startListening();
            }
        });

        getLocation();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(Dashboard.this, Dashboard.class));
                        break;
                    case R.id.nav_account:
                        startActivity(new Intent(Dashboard.this, User_Profile.class));
                        break;
//            case R.id.nav_story:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new SearchFragment()).commit();
//                break;
//            case R.id.nav_tips:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new SearchFragment()).commit();
//                break;
                    case R.id.nav_rating:
                        startActivity(new Intent(Dashboard.this, User_Ratting.class));
                        break;
//            case R.id.nav_logout:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new SearchFragment()).commit();
//                break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void getUser() {

        class GetTasks extends AsyncTask<Void, Void, List<AdharCard>> {

            @Override
            protected List<AdharCard> doInBackground(Void... voids) {
                List<AdharCard> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .adharcardDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<AdharCard> tasks) {
                super.onPostExecute(tasks);
                if (tasks.size() > 0) {
                    AdharCard adharCard = tasks.get(0);
                    AppUtils.setStringPreference(Dashboard.this, Constants.UserName, adharCard.getName());
                    addUser(adharCard);

                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void addUser(AdharCard adharCard) {
        String userId = AppUtils.getStringPreference(Dashboard.this, Constants.userId);

        User user = new User();
        user.setUserId(userId);
        user.setUserName(adharCard.getName());

        mDatabase.child(Constants.USERS).child(userId).setValue(user);
    }

    private void startListening() {

        if (SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {

            final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

            final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault());

            mSpeechRecognizer.setRecognitionListener(new SpeechRecognitionListener());

            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        } else {
            Toast.makeText(this, "App does not support", Toast.LENGTH_SHORT).show();
        }


//        String speechLan = AppUtils.getStringPreference(this, Constants.SPEECH_CODE);
//        if (TextUtils.isEmpty(speechLan)) {
//            speechLan = "en-US";
//        }
//        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
//        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        //mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//       // mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
//      //  mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speck Now");
//
//        String languagePref = "en-US";//as you have downloaded US english model
//        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePref);
//        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, languagePref);
//        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, languagePref);
//
//        SpeechRecognitionListener listener = new SpeechRecognitionListener();
//        mSpeechRecognizer.setRecognitionListener(listener);
//
//            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

    }

//    @Override
//    public boolean onNavigationItemSelected("" MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.nav_home:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new DashboardFragment()).commit();
//                break;
////            case R.id.nav_account:
////                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
////                        new FavoritesFragment()).commit();
////                break;
////            case R.id.nav_story:
////                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
////                        new SearchFragment()).commit();
////                break;
////            case R.id.nav_tips:
////                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
////                        new SearchFragment()).commit();
////                break;
////            case R.id.nav_rating:
////                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
////                        new SearchFragment()).commit();
////                break;
////            case R.id.nav_logout:
////                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
////                        new SearchFragment()).commit();
////                break;
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//            return false;
//        }

    protected class SpeechRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(Dashboard.this, "Listening", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
            //   Toast.makeText(Dashboard.this, "onBeginningOfSpeech", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            //  Toast.makeText(Dashboard.this, "onRmsChanged", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            //  Toast.makeText(Dashboard.this, "onBufferReceived", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEndOfSpeech() {
            //Toast.makeText(Dashboard.this, "onEndOfSpeech", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int error) {
            Log.e("ERROR", error + " ");
            Toast.makeText(Dashboard.this, "speak something!! " + error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String result = matches.get(0);
            if (result.contains("help") || result.contains("save")) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(Dashboard.this, R.raw.sirenplay);
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

        GPSTracker gpsTracker = new GPSTracker(this, this);

        if (gpsTracker.getIsGPSTrackingEnabled()) {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
            if (stringLatitude.contains("0.0")) {
                AppUtils.setStringPreference(this, Constants.latitude, "21.1702");
            } else {
                AppUtils.setStringPreference(this, Constants.latitude, stringLatitude);
            }

            String stringLongitude = String.valueOf(gpsTracker.longitude);
            if (stringLongitude.contains("0.0")) {
                AppUtils.setStringPreference(this, Constants.longitude, "72.8311");
            } else {
                AppUtils.setStringPreference(this, Constants.longitude, stringLongitude);
            }

            String country = gpsTracker.getCountryName(this);
            if (country == null) {
                AppUtils.setStringPreference(this, Constants.country, "India");
            } else {
                AppUtils.setStringPreference(this, Constants.country, country);
            }

            String city = gpsTracker.getLocality(this);
            if (city == null) {
                AppUtils.setStringPreference(this, Constants.city, "Surat");
            } else {
                AppUtils.setStringPreference(this, Constants.city, city);
            }

            String postalCode = gpsTracker.getPostalCode(this);

            String code = gpsTracker.getCountryCode(this);
            if (code == null) {
                AppUtils.setStringPreference(this, Constants.code, "IN");
            } else {
                AppUtils.setStringPreference(this, Constants.code, code);
            }

            String addressLine = gpsTracker.getAddressLine(this);
            if (addressLine == null) {
                AppUtils.setStringPreference(this, Constants.address, "Mota varachcha,Surat");
            } else {
                AppUtils.setStringPreference(this, Constants.address, addressLine);
            }

            Log.e("LOCATION", "Latitude :- " + stringLatitude + "  Longitude :- " + stringLongitude + " \n Country :- " + country + " City :- " + city
                    + "  Address :- " + addressLine);
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Runtime permission
    public boolean checkCallPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS}, CALL_PHONE);
            return false;
        }
        return true;
    }

    public void onSecurityCall(View view) {
        Intent in = new Intent(Dashboard.this, SecurityCall.class);
        startActivity(in);
    }

    public void onemergencycall(View view) {
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
                callIntent1.setData(Uri.parse("tel:" + numberOne));

                if (ActivityCompat.checkSelfPermission(Dashboard.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent1);
                addEmegencyCall(numberOne);
            }
        });

        txtTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String numberTwo = txtTwo.getText().toString();

                Intent callIntent2 = new Intent(Intent.ACTION_CALL);
                callIntent2.setData(Uri.parse("tel:" + numberTwo));

                if (ActivityCompat.checkSelfPermission(Dashboard.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent2);
                addEmegencyCall(numberTwo);
            }
        });

        txtThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberThree = txtThree.getText().toString();

                Intent callIntent3 = new Intent(Intent.ACTION_CALL);
                callIntent3.setData(Uri.parse("tel:" + numberThree));

                if (ActivityCompat.checkSelfPermission(Dashboard.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent3);
                addEmegencyCall(numberThree);
            }
        });

        dialog.show();
    }

    private void addEmegencyCall(String numberOne) {


        String userId = AppUtils.getStringPreference(Dashboard.this, Constants.userId);
        String id = AppUtils.getStringPreference(Dashboard.this, Constants.CallCount);
        if (id == ""){
            id = "0";
        } else {
            int temp = Integer.valueOf(id);
            temp = temp + 1;
            id = String.valueOf(temp);
        }
        AppUtils.setStringPreference(Dashboard.this,Constants.CallCount,id);

        Call call = new Call();
        call.setToCall(numberOne);
        call.setCallTime(String.valueOf(new Date().getTime()));

        mDatabase.child(Constants.USERS).child(userId).child(Constants.Calls).child(id).setValue(call);

    }

    public void onlocation(View view) {
        Intent i = new Intent(Dashboard.this, location.class);
        startActivity(i);
    }

    public void onChat(View view) {
        Intent i = new Intent(Dashboard.this, MainChat.class);
        startActivity(i);
    }

    public void onRecord(View View) {
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

    public void onEmergencyMessage(View view) {

        // Create an alert builder
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setTitle("Emergency Message");

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
                AppUtils.sendSMS(numberOne, AppUtils.getMessageFormate(Dashboard.this));
                addMessage(numberOne, AppUtils.getMessageFormate(Dashboard.this));
            }
        });

        txtTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String numberTwo = txtTwo.getText().toString();

                AppUtils.sendSMS(numberTwo, AppUtils.getMessageFormate(Dashboard.this));
                addMessage(numberTwo, AppUtils.getMessageFormate(Dashboard.this));
            }
        });

        txtThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberThree = txtThree.getText().toString();
                AppUtils.sendSMS(numberThree, AppUtils.getMessageFormate(Dashboard.this));
                addMessage(numberThree, AppUtils.getMessageFormate(Dashboard.this));
            }
        });

        dialog.show();
    }

    private void addMessage(String numberOne, String messageFormate) {

        String userId = AppUtils.getStringPreference(Dashboard.this, Constants.userId);
        String id = AppUtils.getStringPreference(Dashboard.this, Constants.MessageCount);
        if (id == ""){
            id = "0";
        } else {
            int temp = Integer.valueOf(id);
            temp = temp + 1;
            id = String.valueOf(temp);
        }
        AppUtils.setStringPreference(Dashboard.this,Constants.MessageCount,id);

//        ArrayList<Message> messages = new ArrayList<>();
//        for (int i = 0; i < 5 ; i++) {

            Message message = new Message();
            message.setToMsg(numberOne);
            message.setMsgTime(String.valueOf(new Date().getTime()));
            message.setMessage(messageFormate);
//            messages.add(message);
//        }

        mDatabase.child(Constants.USERS).child(userId).child(Constants.Messages).child(id).setValue(message);
//
//        User user = new User();
//        user.setMessages(messages);

//        mDatabase.child(Constants.USERS).child(userId).setValue(user);
    }

    // Do something with the data
    // coming from the AlertDialog
    private void sendDialogDataToActivity(String data) {
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
                List<Register> taskList1 = taskList;
                return taskList1;
            }

            @Override
            protected void onPostExecute(List<Register> tasks) {
                super.onPostExecute(tasks);
                if (tasks.size() > 0) {
                    register = tasks.get(0);
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();

    }
}