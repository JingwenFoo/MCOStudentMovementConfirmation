package com.example.mcostudentmovementconfirmation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPage extends AppCompatActivity {
Button logout, location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        logout = (Button) findViewById(R.id.btnLogoutAdmin);
        location = (Button) findViewById(R.id.btnLocation);

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
    }
}