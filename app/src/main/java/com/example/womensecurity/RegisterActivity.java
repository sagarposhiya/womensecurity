package com.example.womensecurity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.service.autofill.Validator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.womensecurity.databse.DatabaseClient;
import com.example.womensecurity.models.AdharCard;
import com.example.womensecurity.models.Register;
import com.example.womensecurity.utils.AppUtils;
import com.example.womensecurity.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O_MR1)
public class RegisterActivity extends AppCompatActivity implements Validator{

    ImageButton btndate;
    EditText txtdate;
    private int mYear, mMonth, mDay;
    @NotEmpty
    @Length(min = 3, max = 10)
    private EditText inputFirst, inputLast;

    @NotEmpty
    @Length(min = 3, max = 80)
    private EditText inputAdd, inputemail;


    @NotEmpty
    @Pattern(regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    private EditText inputmobile, inputeme1, inputeme2, inputeme3;

    @Min(18)
    @Max(120)
    private EditText inputdob;

    @NotEmpty
    @Password
    @Pattern(regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
    private EditText inputpass;

    @Checked
    private CheckBox checkBoxAgree;

    private Button btnadd;
    ProgressBar progressBar;

    private DatabaseReference databaseReference;
    boolean isAllFieldsChecked = false;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btndate=(ImageButton)findViewById(R.id.date);
        txtdate=(EditText)findViewById(R.id.inputdob);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        inputFirst = findViewById(R.id.inputFirst);
        inputLast = findViewById(R.id.inputLast);
        inputAdd = findViewById(R.id.inputAdd);
        inputmobile = findViewById(R.id.inputmobile);
        inputemail= findViewById(R.id.inputemail);
        inputeme1 = findViewById(R.id.inputeme1);
        inputeme2 = findViewById(R.id.inputeme2);
        inputeme3 = findViewById(R.id.inputeme3);
        inputdob = findViewById(R.id.inputdob);
        inputpass = findViewById(R.id.inputpass);
        checkBoxAgree = findViewById(R.id.checkBoxAgree);
        btnadd = findViewById(R.id.btnadd);
        progressBar=findViewById(R.id.progressBar);

        getTasks();

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname,lastname,address,mobileno,email,emer1,emer2,emer3,dob,password;
                firstname=String.valueOf(inputFirst.getText());
                lastname=String.valueOf(inputLast.getText());
                address=String.valueOf(inputAdd.getText());
                mobileno=String.valueOf(inputmobile.getText());
                email=String.valueOf(inputemail.getText());
                emer1=String.valueOf(inputeme1.getText());
                emer2=String.valueOf(inputeme2.getText());
                emer3=String.valueOf(inputeme3.getText());
                dob=String.valueOf(inputdob.getText());
                password=String.valueOf(inputpass.getText());
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    progressBar.setVisibility(View.VISIBLE);

                    Register register = new Register();
                    register.setAddress(address);
                    register.setDate(dob);
                    register.setEmail(email);
                    register.setEmer1(emer1);
                    register.setEmer2(emer2);
                    register.setEmer3(emer3);
                    register.setFirstName(firstname);
                    register.setLastName(lastname);
                    register.setMobile(mobileno);
                   // register.setPassword(password);

                    String userId = AppUtils.getStringPreference(RegisterActivity.this, Constants.userId);
                    mDatabase.child("users").child(userId).setValue(register);

                    saveUser(register);

                }
                else{
                    Toast.makeText(getApplicationContext(),"All field required", Toast.LENGTH_SHORT).show();
                }

//                isAllFieldsChecked = CheckAllFields();
//                if (isAllFieldsChecked){
//                    Intent i = new Intent(RegisterActivity.this, Dashboard.class);
//                    startActivity(i);
//                }
//                else {
//                    overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
//                }

            }
        });
    }

    private void saveUser(final Register register) {

        class saveUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .registerDao()
                        .insert(register);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                AppUtils.setBooleanPreference(RegisterActivity.this,Constants.isRegistered,true);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        saveUser st = new saveUser();
        st.execute();
    }

    private void getTasks() {
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
                if (tasks.size() > 0){
                    AdharCard adharCard = tasks.get(0);
                    String s = adharCard.getName();
                    String[] arr = s.split(" ");

                    for ( String ss : arr) {
                        System.out.println(ss);
                    }

                    for (int i = 0; i < arr.length; i++) {
                        switch (i){
                            case 0:
                                inputFirst.setText(arr[i]);
                                break;
                            case 1:

                                break;
                            case 2:
                                inputLast.setText(arr[i]);
                                break;
                        }
                    }

                    inputdob.setText(adharCard.getDateOfBirth());
                    inputemail.setText(adharCard.getEmail());
                    inputmobile.setText(adharCard.getMobile());
                    inputAdd.setText(adharCard.getHouse() + "," + adharCard.getStreet() + " "+ adharCard.getLocation() + " "+ adharCard.getPinCode() + " " + adharCard.getSubDistrict());
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private boolean CheckAllFields() {
        if (inputFirst.length() == 0) {
            inputFirst.setError("This field is required");
            return false;
        }
        if (inputLast.length() == 0) {
            inputLast.setError("This field is required");
            return false;
        }

        if (inputAdd.length() == 0) {
            inputAdd.setError("Address is required");
            return false;
        }
        if (inputmobile.length() == 0) {
            inputmobile.setError("Mobile Number is Required");
            return false;
        }
        if (inputmobile.length() == 0) {
            inputmobile.setError("Email is Required");
            return false;
        }
        if (inputeme1.length() == 0) {
            inputeme1.setError("Emergency Number 1 is Required");
            return false;
        }
        if (inputeme2.length() == 0) {
            inputeme2.setError("Emergency Number 2 is Required");
            return false;
        }
        if (inputeme3.length() == 0) {
            inputeme3.setError("Emergency Number 3 is Required");
            return false;
        }
        if (inputdob.length() == 0) {
            inputdob.setError("Date of Birth is Required");
            return false;
        }
        if (inputpass.length() == 0) {
            inputpass.setError("Password is required");
            return false;
        }
        else if (inputpass.length() < 8) {
            inputpass.setError("Password must be minimum 8 characters");
            return false;
        }
        if (inputmobile.length() < 10) {
            inputmobile.setError("mobile number can not be more than 10 digit.");
            return false;
        }
        if (inputeme1.length() < 10) {
            inputeme1.setError("mobile number can not be more than 10 digit.");
            return false;
        }
        if (inputeme2.length() < 10) {
            inputeme2.setError("mobile number can not be more than 10 digit.");
            return false;
        }
        if (inputeme3.length() < 10) {
            inputeme3.setError("mobile number can not be more than 10 digit.");
            return false;
        }
        // after all validation return true.
        return true;
    }

    public void ondateClick(View view) {

        if (view == btndate){

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog= new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    },mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}

//!firstname.equals("")&&!lastname.equals("")&&!address.equals("")&&!mobileno.equals("")&&!email.equals("")&&!emer1.equals("")&&
//        !emer2.equals("")&&!emer3.equals("")&&!dob.equals("")&&!password.equals("")