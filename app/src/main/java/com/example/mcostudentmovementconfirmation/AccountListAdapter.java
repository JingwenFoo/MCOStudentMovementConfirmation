package com.example.mcostudentmovementconfirmation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountViewHolder> {
ArrayList<Student> accountList;
Context context;

    public AccountListAdapter(ArrayList<Student> accountList, Context context) {
        this.accountList = accountList;
        this.context = context;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.studid_name_layout,parent,false);
        return new AccountListAdapter.AccountViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.studentID.setText(accountList.get(position).getStudentID());
        holder.studentName.setText(accountList.get(position).getName());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MainActivity2.class);
                intent.putExtra("stdID", String.valueOf(accountList.get(position).getStudentID()));
                intent.putExtra("stdName",String.valueOf(accountList.get(position).getName()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder{
        TextView studentName, studentID;
        public View mainLayout;
        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.display_name);
            studentID = itemView.findViewById(R.id.display_studid);
            mainLayout = itemView;
        }
    }

}