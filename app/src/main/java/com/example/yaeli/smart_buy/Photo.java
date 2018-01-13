package com.example.yaeli.smart_buy;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class Photo extends AppCompatActivity implements View.OnClickListener{
    private static final int RESULT_LOAD_IMAGE=1;
    private static final int CAMERA_REQUEST=0;

    private ImageView image;
    private Uri pic_location=null;
    private String userId;
    private User user;
    private StorageReference mStorage;
    private DatabaseReference databaseReference;
    private boolean isPic;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        image = (ImageView) findViewById(R.id.image);

        /* Make buttons clickable */
        Button uploadBtn = (Button) findViewById(R.id.upload);
        Button submit = (Button) findViewById(R.id.submit);
        Button camera = (Button) findViewById(R.id.camera);
        uploadBtn.setOnClickListener(this);
        camera.setOnClickListener(this);
        submit.setOnClickListener(this);

        isPic=false;

        /* Firebase Storage reference */
        mStorage= FirebaseStorage.getInstance().getReference();
        /* Firebase Databse reference */
        databaseReference = FirebaseDatabase.getInstance().getReference();
        /* Firebase Analytics reference */
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        /* Get the user ID from Firebase Auth */
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            userId = fbUser.getUid();
        }

        if (userId == null) {
            Toast.makeText(this, "Failed to get User ID!", Toast.LENGTH_LONG).show();
            return;
        }

        /* Get current user using user ID from "users" table */
        databaseReference.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /* Convert node to "User" */
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.upload):
                /* Open Android default image selection from gallery activity */
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;

            case(R.id.camera):
                /* Open Android default camera activity */
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "photo.jpg");
                pic_location= FileProvider.getUriForFile(this,getApplicationContext().getPackageName()+".fileprovider" , imageFile);
                pic_location=Uri.fromFile(imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,pic_location);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);
                break;

            case(R.id.submit):
                if(pic_location!=null && isPic){
                    final StorageReference filepath = mStorage.child("Photos").child(userId).child("photo.jpg");

                    filepath.putFile(pic_location)
                    /* Successful upload */
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            /* Show success message */
                            Toast.makeText(Photo.this, "Upload done", Toast.LENGTH_LONG).show();

                            /* Save in DB that now the user has a photo */
                            user.setPhoto(true);
                            databaseReference.child("users").child(userId).setValue(user);

                            /*
                             * Log event of user added photo
                             */
                            Bundle bundle = new Bundle();
                            bundle.putString("username", user.getUserName());
                            bundle.putString("url", filepath.getPath());
                            mFirebaseAnalytics.logEvent("photo_added", bundle);
                        }
                    })
                    /* Failure on upload */
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Photo.this, "Failed to upload!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(Photo.this, "No picture was selected", Toast.LENGTH_SHORT).show();
                }

                /* Go to login activity */
                Intent intent=new Intent("com.example.yaeli.smart_buy.loginActivity");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            /* Image loaded from gallery */
            pic_location = data.getData();
            isPic = true;
            image.setImageURI(pic_location);
        }
        else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            /* Image loaded from camera */
            isPic = true;
            image.setImageURI(pic_location);
        }
    }

}
