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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPost extends AppCompatActivity {
    RecyclerView postRecyclerView;
    ArrayList<Model> postList;
    PostAdapter adapter;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        postRecyclerView = (RecyclerView)findViewById(R.id.viewPostRecyclerView);
        postRecyclerView.setHasFixedSize(true);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref = FirebaseDatabase.getInstance().getReference().child("Post");
        postList = new ArrayList<>();
        adapter = new PostAdapter(this,postList);
        postRecyclerView.setAdapter(adapter);

        ref.orderByChild("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Model model = dataSnapshot1.getValue(Model.class);
                    postList.add(model);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}