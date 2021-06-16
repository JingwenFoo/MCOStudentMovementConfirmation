package com.example.mcostudentmovementconfirmation;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SearchAccount extends AppCompatActivity {


    private DatabaseReference myRef;
    private String ValueDatabase;
    private String refinedData;
    private ListView listdata;
    private TextView searchView;
    private TextView textViewSearch;
    private StorageReference storageReference;
    private AlertDialog.Builder mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        storageReference = FirebaseStorage.getInstance().getReference().child("User");

        searchView = findViewById(R.id.textStudID);
        textViewSearch = findViewById(R.id.textViewSearch);
        myRef.addValueEventListener(new ValueEventListener() {
            private ListView listView;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ValueDatabase = dataSnapshot.getValue().toString();
                refinedData = ValueDatabase.substring(1,ValueDatabase.length()-1);

                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SearchAccount.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.state));
                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
