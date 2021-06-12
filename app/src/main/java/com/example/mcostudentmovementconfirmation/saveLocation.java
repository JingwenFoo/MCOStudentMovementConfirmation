package com.example.mcostudentmovementconfirmation;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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
    Uri PathUri;
    int Request_Code =10;
    StorageReference storageReference;
    DatabaseReference locationDbList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);

        etlocation = findViewById(R.id.editSaveLocation);
        upload = findViewById(R.id.btn_Save);
        choose = findViewById(R.id.choose);
        progressBar = findViewById(R.id.progressBar);
        storageReference= FirebaseStorage.getInstance().getReference("ImageQrCode");
        locationDbList = FirebaseDatabase.getInstance().getReference("Location");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Request_Code);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertLocation();
            }
        });

    }

    protected void onActivityResult(int reqeustCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(reqeustCode,resultCode,data);
        if(reqeustCode==Request_Code && resultCode == RESULT_OK && data !=null && data.getData() !=null){
            PathUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),PathUri);
                choose.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //Insert to firebase
    private void insertLocation(){
        if(PathUri !=null) {
            progressBar.setVisibility(View.VISIBLE);
            StorageReference storageReference1 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(PathUri));
            storageReference1.putFile(PathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String nameLocation = etlocation.getText().toString().trim();
                            etlocation.getText().clear();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(saveLocation.this, "Upload Success", Toast.LENGTH_SHORT).show();

                            Model model = new Model(nameLocation, taskSnapshot.getUploadSessionUri().toString());
                            String ImageUploadId = locationDbList.push().getKey();
                            locationDbList.child(ImageUploadId).setValue(model);

                        }
                    });
        }

    }
}