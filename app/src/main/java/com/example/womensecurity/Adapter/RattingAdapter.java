package com.example.womensecurity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.womensecurity.R;
import com.example.womensecurity.models.User;

import java.util.ArrayList;

public class RattingAdapter extends RecyclerView.Adapter<RattingAdapter.ViewHolder> {

    Context context;
    ArrayList<User> users;
    public RattingAdapter(Context context, ArrayList<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public RattingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ratting,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RattingAdapter.ViewHolder holder, int position) {
            User user = users.get(position);
            holder.txtName.setText(user.getUserName());
            if (user.getRatting().size() > 0) {
                holder.txtRatting.setText(user.getRatting().get(0).getRatting());
            } else {
                holder.txtRatting.setText("-");
            }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtRatting;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtRatting = itemView.findViewById(R.id.txtRatting);
        }
    }
}
