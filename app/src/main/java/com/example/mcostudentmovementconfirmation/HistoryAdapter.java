package com.example.mcostudentmovementconfirmation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context context;
    ArrayList<history>list;

    public HistoryAdapter(Context context, ArrayList<history> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        history History=list.get(position);
        holder.checkIn.setText(History.getCheckIn());
        holder.studentID.setText(History.getStudentID());
        holder.time.setText(History.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView checkIn,studentID,time;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            checkIn=itemView.findViewById(R.id.viewCI);
            studentID=itemView.findViewById(R.id.viewSID);
            time=itemView.findViewById(R.id.viewTIME);


        }
    }
}

