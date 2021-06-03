package com.example.mcostudentmovementconfirmation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
EditText email, stdID, stdName, stdIC, phone, passwrd;
Button registerBtn, backToLoginBtn;
Student student;
FirebaseAuth mAuth;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.editTextStdEmail);
        stdID = (EditText) findViewById(R.id.editTextStdID);
        stdName = (EditText) findViewById(R.id.editTextStdName);
        stdIC = (EditText) findViewById(R.id.editTextStdIC);
        phone = (EditText) findViewById(R.id.editTextPhone);
        passwrd = (EditText) findViewById(R.id.editTextPasswrd);
        backToLoginBtn = (Button) findViewById(R.id.btnBackToLogin);
        registerBtn = (Button) findViewById(R.id.btnRegisterAccount);
        Spinner mySpinner = (Spinner) findViewById(R.id.state_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Register.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.state));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        student = new Student();
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stdID.getText().length() != 0 && stdName.getText().length() != 0 && stdIC.getText().length() != 0 && phone.getText().length() != 0 && passwrd.getText().length() != 0) {
                   mAuth.createUserWithEmailAndPassword(email.getText().toString(), passwrd.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               String uid = task.getResult().getUser().getUid();
                               Student student = new Student(uid, email.getText().toString(), stdID.getText().toString(), passwrd.getText().toString(), stdName.getText().toString(), stdIC.getText().toString(), phone.getText().toString(), String.valueOf(mySpinner.getSelectedItem()),0);

                               ref.child("User").child(student.getStudentID()).setValue(student);
                               Toast.makeText(Register.this, "Register successfully", Toast.LENGTH_SHORT).show();
                           } else {
                               Toast.makeText(Register.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }

                    });
                }
                else{
                    Toast.makeText(Register.this, "Missing data", Toast.LENGTH_SHORT).show();
                }

            }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(Register.this, MainActivity.class);
                startActivity(login);
            }
        });
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        this.finish();
    }
}