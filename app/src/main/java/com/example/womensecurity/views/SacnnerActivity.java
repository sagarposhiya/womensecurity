package com.example.womensecurity.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.womensecurity.Dashboard;
import com.example.womensecurity.R;
import com.example.womensecurity.RegisterActivity;
import com.example.womensecurity.databse.DatabaseClient;
import com.example.womensecurity.models.AdharCard;
import com.example.womensecurity.utils.DataAttributes;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SacnnerActivity extends AppCompatActivity {

    Button btnScan;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacnner);
        btnScan = findViewById(R.id.btnScanning);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkCameraPermission()){return;}
                IntentIntegrator integrator = new IntentIntegrator(SacnnerActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan a Aadharcard QR Code");
                integrator.setResultDisplayDuration(100);
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.initiateScan();
            }
        });
    }

    //Runtime permission
    public boolean checkCameraPermission (){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            String temp = scanContent.substring(scanContent.lastIndexOf("<?xml"));

            // process received data
            if (scanContent != null && !scanContent.isEmpty()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = null;
                try {
                    builder = factory.newDocumentBuilder();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                try {
                    Document d1 = builder.parse(new InputSource(new StringReader(temp)));
                    String name = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_NAME_ATTR);
                    String gender = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_GENDER_ATTR);
                    Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                    Log.e("ADHARCARD", "NAME :- " + name +"\n Gender :- "+ gender );

                    String uuid = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_UID_ATTR);
                    String dateOfBirth = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_DOB_ATTR);
                    String careOf = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_CO_ATTR);
                    String district = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_DIST_ATTR);
                    String landmark = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_LAND_ATTR);
                    String house = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_HOUSE_ATTR);
                    String location = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_LOCATION_ATTR);
                    String pinCode = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_PC_ATTR);
                    String postOffice = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_PO_ATTR);
                    String state = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_STATE_ATTR);
                    String street = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_STREET_ATTR);
                    String subDistrict = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_SUBDIST_ATTR);
                    String vtc = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_VTC_ATTR);
                    String email = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_EMAIL_ATTR);
                    String mobile = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_MOBILE_ATTR);
                    String signature = d1.getDocumentElement().getAttribute(DataAttributes.AADHAR_SIG_ATTR);

                    AdharCard adharCard = new AdharCard();
                    adharCard.setName(name);
                    adharCard.setDateOfBirth(dateOfBirth);
                    adharCard.setCareOf(careOf);
                    adharCard.setDistrict(district);
                    adharCard.setLandmark(landmark);
                    adharCard.setHouse(house);
                    adharCard.setLocation(location);
                    adharCard.setPinCode(pinCode);
                    adharCard.setPostOffice(postOffice);
                    adharCard.setState(state);
                    adharCard.setStreet(street);
                    adharCard.setSubDistrict(subDistrict);
                    adharCard.setVtc(vtc);
                    adharCard.setEmail(email);
                    adharCard.setMobile(mobile);
                    adharCard.setSignature(signature);


                    if (gender.equalsIgnoreCase("F")){
                        Toast.makeText(this, "You are able to login", Toast.LENGTH_SHORT).show();
                        saveTask(adharCard);
                        //startActivity(new Intent(MainActivity.this, Dashboard.class));
                    } else {
                        Toast.makeText(this, "You are not able to login", Toast.LENGTH_SHORT).show();
                        saveTask(adharCard);
                        //  startActivity(new Intent(MainActivity.this, Dashboard.class));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                //   processScannedData(scanContent);
            }
        }
    }

    private void saveTask(final AdharCard adharCard) {

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .adharcardDao().deleteAdharCard();

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .adharcardDao()
                        .insert(adharCard);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }
}