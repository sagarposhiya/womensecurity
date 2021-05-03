package com.example.womensecurity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.womensecurity.R;
import com.example.womensecurity.models.User;

import java.util.ArrayList;

public class CallMainAdapter extends RecyclerView.Adapter<CallMainAdapter.ViewHolder> {
    Context context;
    ArrayList<User> users;
    public CallMainAdapter(Context context, ArrayList<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public CallMainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_main,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallMainAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.txtName.setText(user.getUserName());
        CallSubAdapter adapter = new CallSubAdapter(context,user.getCalls());
        holder.rvSub.setLayoutManager(new LinearLayoutManager(context));
        holder.rvSub.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtNameUnder;
        RecyclerView rvSub;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtNameUnder = itemView.findViewById(R.id.txtNameUnder);
            rvSub = itemView.findViewById(R.id.rvSub);

        }
    }
}
