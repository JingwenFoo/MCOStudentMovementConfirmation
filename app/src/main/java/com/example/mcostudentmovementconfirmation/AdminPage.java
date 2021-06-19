package com.example.mcostudentmovementconfirmation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminPage extends AppCompatActivity {

Button logout, location, accountList, studentMovementRecord;
FloatingActionButton addPostBtn;
RecyclerView postRecyclerView;
ArrayList<Model> postList;
AdminPostAdapter adapter;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        logout = (Button) findViewById(R.id.btnLogoutAdmin);
        location = (Button) findViewById(R.id.btnLocation);
        accountList = (Button) findViewById(R.id.btnAccount);
        studentMovementRecord = (Button) findViewById(R.id.btnRecord);
        addPostBtn = (FloatingActionButton) findViewById(R.id.fab);
        postRecyclerView = (RecyclerView) findViewById(R.id.postRecyclerView);
        postRecyclerView.setHasFixedSize(true);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref = FirebaseDatabase.getInstance().getReference().child("Post");
        postList = new ArrayList<>();
        adapter = new AdminPostAdapter(this, postList);
        postRecyclerView.setAdapter(adapter);

        ref.orderByChild("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Model model = dataSnapshot1.getValue(Model.class);
                    postList.add(model);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPost = new Intent(AdminPage.this, AddPost.class);
                startActivity(addPost);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(AdminPage.this, MainActivity.class);
                startActivity(in);
                preferences.clearData(AdminPage.this);
                finish();
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(AdminPage.this, ViewLocationList.class);
                startActivity(in);
            }
        });

        accountList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account = new Intent(AdminPage.this, AccountList.class);
                startActivity(account);
            }
        });

        studentMovementRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent record = new Intent(AdminPage.this, HistoryPage.class);
                startActivity(record);
            }
        });

    }
}