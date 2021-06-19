package com.example.mcostudentmovementconfirmation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyMovementAdapter extends RecyclerView.Adapter<MyMovementAdapter.MyViewHolder> {
    Context context;

    ArrayList<history> list;



    public MyMovementAdapter(Context context, ArrayList<history> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.admin_student_movement,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.checkIn.setText(list.get(position).getCheckIn());
        holder.studentID.setText(list.get(position).getStudentID());
        holder.time.setText(list.get(position).getTime());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView checkIn, studentID, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkIn = itemView.findViewById(R.id.vCI);
            studentID = itemView.findViewById(R.id.vSID);
            time = itemView.findViewById(R.id.vTIME);

        }
    }

}


