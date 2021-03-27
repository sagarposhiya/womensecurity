package com.example.womensecurity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.womensecurity.R;
import com.example.womensecurity.models.ChatMessage;

import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    private static final int MY_MESSAGE=0;
    private static final int BOT_MESSAGE=1;

    public ChatMessageAdapter(@NonNull Context context, List<ChatMessage> data) {
        super(context, R.layout.activity_user_query,data);
    }

    public int getItemViewType(int position) {

        ChatMessage item = getItem(position);

        if (item.isMine() && !item.isImage()) {
            return MY_MESSAGE;
        } else {
            return BOT_MESSAGE;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        int viewType = getItemViewType(position);

        if (viewType == MY_MESSAGE) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.activity_user_query, parent, false);

            TextView textView = convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        }
        else if (viewType == BOT_MESSAGE) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.activity_bot_reply, parent, false);

            TextView textView = convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        }

        convertView.findViewById(R.id.chatmessageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked....", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public int getViewTypeCount(){
        return 2;
    }
}
