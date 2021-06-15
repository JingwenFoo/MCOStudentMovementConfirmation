package com.example.mcostudentmovementconfirmation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentPage extends AppCompatActivity {
Button logout, profile, post,checkIn;
TextView textName, textID;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);
        logout = (Button) findViewById(R.id.btnLogout);
        profile = (Button) findViewById(R.id.btnProfile);
        ref = FirebaseDatabase.getInstance().getReference();
        textName = (TextView) findViewById(R.id.textViewName);
        textID = (TextView) findViewById(R.id.textViewID);
        post = (Button) findViewById(R.id.btnPost);
        checkIn=(Button)findViewById(R.id.btnCheckIn);

        textID.setText(String.valueOf(preferences.getDataStatus(this)));

        ref.child("User").child(textID.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Student name = dataSnapshot.getValue(Student.class);
               textName.setText(String.valueOf(name.getName()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(StudentPage.this, ViewProfile.class);
                startActivity(profile);
            }
        });

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(StudentPage.this, ScanPage.class);
                startActivity(profile);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent post = new Intent(StudentPage.this, ViewPost.class);
                startActivity(post);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(StudentPage.this, MainActivity.class);
                startActivity(in);
                preferences.clearData(StudentPage.this);
                finish();
            }
        });
    }

}