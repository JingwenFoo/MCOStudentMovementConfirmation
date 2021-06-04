package com.example.mcostudentmovementconfirmation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
EditText username, password;
Button btn_Login, btn_Register;
ImageView fingerprint;

DatabaseReference ref;
Executor executor;
BiometricPrompt biometricPrompt;
BiometricPrompt.PromptInfo promptInfo;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        btn_Login = (Button) findViewById(R.id.btnLogin);
        btn_Register = (Button) findViewById(R.id.btn2);
        fingerprint = (ImageView) findViewById(R.id.fingerprint);
        ref = FirebaseDatabase.getInstance().getReference();

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                if(username.getText().toString().equals("Admin"))
                {
                    preferences.setDataLogin(MainActivity.this,true);
                    preferences.setDataStatus(MainActivity.this,username.getText().toString());
                    Intent in = new Intent(MainActivity.this, AdminPage.class);
                    startActivity(in);
                }
                else {
                    preferences.setDataLogin(MainActivity.this, true);
                    preferences.setDataStatus(MainActivity.this, username.getText().toString());
                    Intent in = new Intent(MainActivity.this, StudentPage.class);
                    startActivity(in);
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Cancel / Use password")
                .build();

        fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().length()!=0){
                    String User = username.getText().toString();
                    ref.child("User").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            if(dataSnapshot1.child(User).exists()) {
                                biometricPrompt.authenticate(promptInfo);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Invalid username",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
               else
                {
                    Toast.makeText(MainActivity.this,"Please enter username",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this, Register.class);
                startActivity(register);
            }
        });



        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().length() != 0 && password.getText().length() != 0) {
                    String Username = username.getText().toString();
                    String Password = password.getText().toString();


                    ref.child("User").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(Username).exists())
                            {
                                if(dataSnapshot.child(Username).child("password").getValue(String.class).equals(Password))
                                {
                                    if(dataSnapshot.child(Username).child("userType").getValue(Integer.class).equals(1))
                                    {
                                        preferences.setDataLogin(MainActivity.this,true);
                                        preferences.setDataStatus(MainActivity.this,"Admin");
                                        Intent in = new Intent(MainActivity.this,AdminPage.class);
                                        startActivity(in);
                                    }
                                    else
                                    {
                                        preferences.setDataLogin(MainActivity.this,true);
                                        preferences.setDataStatus(MainActivity.this,Username);
                                        Intent in = new Intent(MainActivity.this, StudentPage.class);
                                        startActivity(in);
                                    }
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this,"Invalid password",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Invalid username",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Missing data",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    protected void onStart()
    {
        super.onStart();
        if(preferences.getDataLogin(this))
        {
            if(preferences.getDataStatus(this).equals("Admin"))
            {
                Intent in = new Intent(MainActivity.this,AdminPage.class);
                startActivity(in);
                finish();
            }
            else
            {
                Intent in = new Intent(MainActivity.this,StudentPage.class);
                startActivity(in);
                finish();
            }
        }
    }


}