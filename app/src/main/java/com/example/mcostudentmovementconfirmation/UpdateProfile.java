package com.example.mcostudentmovementconfirmation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {
TextView stdID, stdIC, password;
EditText stdName, phone, email, state;
Button changePassword, updateProfileBtn;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        stdID = findViewById(R.id.textSID);
        stdIC = findViewById(R.id.textSIC);
        password = findViewById(R.id.textSPassword);
        stdName = findViewById(R.id.edittextName);
        phone = findViewById(R.id.edittextPhone);
        email = findViewById(R.id.edittextEmail);
        state = findViewById(R.id.edittextState);
        changePassword = findViewById(R.id.btnChangePasswrd);
        updateProfileBtn = findViewById(R.id.updateProfileBtn);

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

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("User").child(stdID.getText().toString()).child("name").setValue(stdName.getText().toString());
                ref.child("User").child(stdID.getText().toString()).child("email").setValue(email.getText().toString());
                ref.child("User").child(stdID.getText().toString()).child("phone").setValue(phone.getText().toString());
                ref.child("User").child(stdID.getText().toString()).child("state").setValue(state.getText().toString());
                Toast.makeText(UpdateProfile.this,"Profile updated successfully",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(UpdateProfile.this, ViewProfile.class);
                startActivity(in);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_change_password,null);
                EditText currentPassword = view.findViewById(R.id.editPassword);
                EditText newPassword = view.findViewById(R.id.editcPassword);
                Button updatePassword = view.findViewById(R.id.btnUpdatePasswrd);

                updatePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String oldPassword = currentPassword.getText().toString().trim();
                        String newPasswrd = newPassword.getText().toString().trim();

                        if(oldPassword.isEmpty())
                        {
                            Toast.makeText(UpdateProfile.this,"Enter your current password",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(newPasswrd.length()<6)
                        {
                            Toast.makeText(UpdateProfile.this,"Password length must be at least 6 characters...",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ref.child("User").child(stdID.getText().toString()).child("password").setValue(newPasswrd);
                        Toast.makeText(UpdateProfile.this,"Password updated successfully",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(UpdateProfile.this, ViewProfile.class);
                        startActivity(in);
                    }
                });

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }
}