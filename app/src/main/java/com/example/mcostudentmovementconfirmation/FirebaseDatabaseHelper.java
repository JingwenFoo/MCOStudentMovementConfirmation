package com.example.mcostudentmovementconfirmation;

import android.accounts.Account;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper<account> {

    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private List<AccountList> account = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<AccountList> account, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();

    }

    public FirebaseDatabaseHelper() {
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("User");

    }

    public void readAccountList(final DataStatus dataStatus) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                account.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Account account;
                    keyNode.getValue(AccountList.class);
                }
                dataStatus.DataIsLoaded(account,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
