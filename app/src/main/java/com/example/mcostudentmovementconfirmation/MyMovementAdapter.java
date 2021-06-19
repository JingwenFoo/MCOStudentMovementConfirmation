package com.example.mcostudentmovementconfirmation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyMovementAdapter extends RecyclerView.Adapter {
    Context context;

    ArrayList<Movement> list;



    public MyMovementAdapter(Context context, ArrayList<Movement> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Movement movement = list.get(position);
        holder.checkIn.setText(movement.getCheckIn());
        holder.studentID.setText(movement.getStudentID());
        holder.time.setText(movement.getTime());


    }


    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView checkIn, studentID, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkIn = itemView.findViewById(R.id.tvcheckIn);
            studentID = itemView.findViewById(R.id.tvstudentID);
            time = itemView.findViewById(R.id.tvtime);

        }
    }

}


