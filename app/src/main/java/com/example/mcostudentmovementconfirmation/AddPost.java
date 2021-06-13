package com.example.mcostudentmovementconfirmation;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddPost extends AppCompatActivity {
TextView post;
ImageView cancel, selectedImage;
Button pickImageBtn;
EditText description;
ProgressDialog progressDialog;

Uri imageUri;
String url;
public static final int PICK_IMAGE=100;

FirebaseAuth auth;
DatabaseReference ref;
StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        post = findViewById(R.id.postUpload);
        cancel = findViewById(R.id.cancelPost);
        selectedImage = findViewById(R.id.postImage);
        pickImageBtn = findViewById(R.id.pickImageBtn);
        description = findViewById(R.id.postDescription);
        progressDialog = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("Post images/");

        ActivityResultLauncher<Intent> pickImageResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK)
                        {
                            Intent data = result.getData();
                            imageUri=data.getData();
                            selectedImage.setImageURI(imageUri);
                        }
                        else
                        {
                            Toast.makeText(AddPost.this,"No image is selected",Toast.LENGTH_SHORT).show();
                            selectedImage.setImageResource(R.drawable.ic_baseline_add_24);
                        }
                    }
                });

        pickImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                pickImageResultLauncher.launch(intent);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null)
                {
                    uploadPost();
                }
                else
                {
                    Toast.makeText(AddPost.this,"No image selected",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadPost()
    {
        progressDialog.setTitle("New Post");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if(imageUri !=null)
        {
            StorageReference sRef = storageReference.child(System.currentTimeMillis()+"."+getExtensionFile(imageUri));
            sRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();
                            ref = FirebaseDatabase.getInstance().getReference().child("Post");

                            String postID = ref.push().getKey();
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("postID",postID);
                            map.put("postImage",url);
                            map.put("description",description.getText().toString());
                            map.put("publisher",String.valueOf(preferences.getDataStatus(AddPost.this)));

                            progressDialog.dismiss();
                            ref.child(postID).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(AddPost.this, "Post Uploaded",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddPost.this,AdminPage.class));
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(AddPost.this,"Failed upload post",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPost.this,"Failed "+e.getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            });
        }
    }

    public String getExtensionFile(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return  map.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}