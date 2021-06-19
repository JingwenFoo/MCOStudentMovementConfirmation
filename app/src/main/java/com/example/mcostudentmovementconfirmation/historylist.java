package com.example.mcostudentmovementconfirmation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class historylist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    HistoryAdapter historyAdapter;
    ArrayList<history> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historylist);

        recyclerView=findViewById(R.id.historyList);
        database= FirebaseDatabase.getInstance().getReference("StudentMovement");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list= new ArrayList<>();
        historyAdapter= new HistoryAdapter(this,list);
        recyclerView.setAdapter(historyAdapter);
        database = FirebaseDatabase.getInstance().getReference().child("StudentMovement");
        Query query = database.orderByChild("studentID").equalTo(preferences.getDataStatus(this));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    history History=dataSnapshot.getValue(history.class);
                    list.add(History);
                }

                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}