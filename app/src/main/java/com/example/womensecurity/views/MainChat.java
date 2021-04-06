package com.example.womensecurity.views;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.womensecurity.Adapter.ChatMessageAdapter;
import com.example.womensecurity.R;
import com.example.womensecurity.models.ChatMessage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class MainChat extends AppCompatActivity {

    ListView Listview;
    FloatingActionButton btnsend;
    EditText editText;
    ImageView imageView;
    WebView webView;

//    private Bot bot;
//        public static Chat chat;
    private ChatMessageAdapter adapter;

    protected void onCreate(Bundle saveInstantState) {
        super.onCreate(saveInstantState);
        setContentView(R.layout.activity_main_chat);

        Listview = findViewById(R.id.listview);
        btnsend = findViewById(R.id.flotingbtn);
        editText = findViewById(R.id.edtTextmsg);
        imageView = findViewById(R.id.imgview);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        //WebView webView = new WebView(this);
        webView.loadUrl("https://web-chat.global.assistant.watson.cloud.ibm.com/preview.html?region=eu-gb&integrationID=21962f16-05b5-41c4-b40f-2e6d3cd22964&serviceInstanceID=4759fca9-63f5-4a73-b4e4-9e96007bb3fe");
       // setContentView(webView);

        adapter= new ChatMessageAdapter(this,new ArrayList<ChatMessage>());
        Listview.setAdapter(adapter);


        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message= editText.getText().toString();
//                String response = chat.multisentenceRespond(editText.getText().toString());

                if (TextUtils.isEmpty(message)){
                    Toast.makeText(MainChat.this, "Please Enter query", Toast.LENGTH_SHORT).show();
                    return;
                }

                sendMessage(message);
//                botsReply(response);

                //clear edittext
                editText.setText("");
                //ListView.setSelection(adapter.getCount() -1);
            }
        });
    }

    private void botsReply(String response) {
    }

    private void sendMessage(String message) {
    }

}
