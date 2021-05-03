package com.example.womensecurity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.womensecurity.R;
import com.example.womensecurity.models.Register;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    ArrayList<Register> registers;
    public UserAdapter(Context context, ArrayList<Register> registers){
        this.context = context;
        this.registers = registers;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        Register register = registers.get(position);
        holder.txtUserName.setText(register.getFirstName() + " " + register.getLastName());
    }

    @Override
    public int getItemCount() {
        return registers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
        }
    }
}
