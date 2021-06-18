package com.example.mcostudentmovementconfirmation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccountList extends AppCompatActivity {
    RecyclerView myListView;
    ArrayList<Student> myArrayList;
    AccountListAdapter adapter;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountlist);
        myListView = (RecyclerView) findViewById(R.id.listview1);
        myListView.setHasFixedSize(true);
        myListView.setLayoutManager(new LinearLayoutManager(this));
        myArrayList = new ArrayList<>();
        adapter = new AccountListAdapter(myArrayList,this);

        myListView.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance().getReference().child("User");
        Query query = ref.orderByChild("userType").equalTo(0);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                           Student studentList = dataSnapshot1.getValue(Student.class);

                           myArrayList.add(studentList);
                       }
                       adapter.notifyDataSetChanged();

               }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
