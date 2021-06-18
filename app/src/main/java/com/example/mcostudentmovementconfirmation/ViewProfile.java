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

public class ViewProfile extends AppCompatActivity {
TextView stdName, stdID, stdIC, phone, email, state, password;
Button editProfileBtn;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        stdName = findViewById(R.id.textName);
        stdID = findViewById(R.id.textLocation);
        stdIC = findViewById(R.id.textIC);
        phone = findViewById(R.id.textPhone);
        email = findViewById(R.id.textEmail);
        state = findViewById(R.id.textState);
        password = findViewById(R.id.textPassword);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        ref = FirebaseDatabase.getInstance().getReference();

        stdID.setText(String.valueOf(preferences.getDataStatus(this)));

        ref.child("User").child(stdID.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                stdName.setText(String.valueOf(student.getName()));
                stdIC.setText(String.valueOf(student.getIc()));
                phone.setText(String.valueOf(student.getPhone()));
                email.setText(String.valueOf(student.getEmail()));
                state.setText(String.valueOf(student.getState()));
                password.setText(String.valueOf(student.getPassword()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfile = new Intent(ViewProfile.this, UpdateProfile.class);
                startActivity(editProfile);
            }
        });

    }
}