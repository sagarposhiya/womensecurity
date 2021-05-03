package com.example.womensecurity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.womensecurity.R;
import com.example.womensecurity.models.Call;
import com.example.womensecurity.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CallSubAdapter extends RecyclerView.Adapter<CallSubAdapter.ViewHolder> {

    Context context;
    ArrayList<Call> calls;

    public CallSubAdapter(Context context, ArrayList<Call> calls){
        this.context = context;
        this.calls = calls;
    }

    @NonNull
    @Override
    public CallSubAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_sub,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallSubAdapter.ViewHolder holder, int position) {
        Call call = calls.get(position);
        holder.txtNumber.setText(position+1 + ". " +call.getToCall());
        String dateString = new SimpleDateFormat("MM/dd/yyyy hh:mm ").format(new Date(Long.parseLong(call.getCallTime())));
        holder.txtDate.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return calls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumber;
        TextView txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
