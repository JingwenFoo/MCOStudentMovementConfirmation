package com.example.mcostudentmovementconfirmation;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class UpdatePost extends AppCompatActivity {
    TextView updatePost;
    ImageView cancelUpdate, selectedImageUpdate;
    Button pickImageUpdateBtn;
    EditText descriptionUpdate;
    ProgressDialog progressDialogUpdate;

    Uri imageUriUpdate;
    String urlUpdate;
    public static final int PICK_IMAGE=100;

    FirebaseAuth auth;
    DatabaseReference ref;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);
        updatePost = findViewById(R.id.postUpdate);
        cancelUpdate = findViewById(R.id.cancelPostUpdate);
        selectedImageUpdate = findViewById(R.id.postImageUpdate);
        pickImageUpdateBtn = findViewById(R.id.pickImageUpdateBtn);
        descriptionUpdate = findViewById(R.id.postDescriptionUpdate);
        progressDialogUpdate = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Post");
        storageReference = FirebaseStorage.getInstance().getReference().child("Post images/");

        Intent intent = getIntent();
        String editPostID = ""+intent.getStringExtra("editPostID");

        Query fquery = ref.orderByChild("postID").equalTo(editPostID);
        fquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String imagePost = ""+ds.child("postImage").getValue();
                    String descriptionPost = ""+ds.child("description").getValue();

                    descriptionUpdate.setText(descriptionPost);
                    Picasso.get().load(imagePost).into(selectedImageUpdate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ActivityResultLauncher<Intent> pickImageResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK)
                        {
                            Intent data = result.getData();
                            imageUriUpdate=data.getData();
                            selectedImageUpdate.setImageURI(imageUriUpdate);
                        }
                        else
                        {
                            Toast.makeText(UpdatePost.this,"No image is selected",Toast.LENGTH_SHORT).show();
                            selectedImageUpdate.setImageResource(R.drawable.ic_baseline_add_24);
                        }
                    }
                });

        pickImageUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                pickImageResultLauncher.launch(intent);
            }
        });

        updatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUriUpdate != null)
                {
                    uploadPost();
                }
                else
                {
                    Toast.makeText(UpdatePost.this,"No image selected",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadPost()
    {
        progressDialogUpdate.setTitle("Update Post");
        progressDialogUpdate.setCanceledOnTouchOutside(false);
        progressDialogUpdate.show();

        if(imageUriUpdate !=null)
        {
            StorageReference sRef = storageReference.child(System.currentTimeMillis()+"."+getExtensionFile(imageUriUpdate));
            sRef.putFile(imageUriUpdate).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urlUpdate = uri.toString();

                            long time = System.currentTimeMillis();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:a");
                            String currentTime = sdf.format(System.currentTimeMillis());
                            String postID = ref.push().getKey();
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("time", currentTime);
                            map.put("postImage",urlUpdate);
                            map.put("description",descriptionUpdate.getText().toString());
                            map.put("publisher",String.valueOf(preferences.getDataStatus(UpdatePost.this)));

                            progressDialogUpdate.dismiss();
                            ref.child(postID).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(UpdatePost.this, "Post Updated",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(UpdatePost.this,AdminPage.class));
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(UpdatePost.this,"Failed upload post",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdatePost.this,"Failed "+e.getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialogUpdate.dismiss();
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