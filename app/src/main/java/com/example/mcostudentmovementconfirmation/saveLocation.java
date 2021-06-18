package com.example.mcostudentmovementconfirmation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.sql.Savepoint;

public class saveLocation extends AppCompatActivity {

    ImageView choose;
    Button upload;
    EditText etlocation;
    ProgressBar progressBar;
    Uri imageUrl = null;
    FirebaseDatabase mDatabase;
    FirebaseStorage mStorage;
    DatabaseReference myRef;
    private static final int Gallery_Code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);

        etlocation = findViewById(R.id.editSaveLocation);
        upload = findViewById(R.id.btn_Save);
        choose = findViewById(R.id.choose);
        progressBar = findViewById(R.id.progressBar);

        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference().child("Location");
        mStorage = FirebaseStorage.getInstance();

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);
            }
        });

    }

    @Override
    protected void onActivityResult(int reqeustCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(reqeustCode, resultCode, data);
        if (reqeustCode == Gallery_Code && resultCode == RESULT_OK) {
            imageUrl = data.getData();
            choose.setImageURI(imageUrl);
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String el = etlocation.getText().toString().trim();

                if (!(el.isEmpty() && imageUrl!= null))
                {

                    progressBar.setVisibility(View.VISIBLE);

                    StorageReference filepath = mStorage.getReference().child("imageQrCode").child(imageUrl.getLastPathSegment());
                    filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {

                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task)
                                {
                                    String t = task.getResult().toString();

                                    DatabaseReference newPost = myRef.push();

                                    newPost.child("imageName").setValue(el);
                                    newPost.child("ImageUrl").setValue(task.getResult().toString());
                                    progressBar.setVisibility(View.GONE);

                                }
                            });
                        }
                    });
                }
            }
        });
    }

}

