package com.example.yaeli.smart_buy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.io.File;

public class Photo extends AppCompatActivity implements View.OnClickListener{
    private static final int RESULT_LOAD_IMAGE=1;
    private static final int CAMERA_REQUEST=0;
    ImageView image;
    Button uploadBtn;
    Button submit;
    Button camera;
    private File imageFile;
    public Uri pic_location=null;
    private String userName;
    private ProgressDialog mProgress;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        image= (ImageView) findViewById(R.id.image);
        uploadBtn= (Button) findViewById(R.id.upload);
        submit= (Button) findViewById(R.id.submit);
        camera=(Button) findViewById(R.id.camera);
        uploadBtn.setOnClickListener(this);
        camera.setOnClickListener(this);
        submit.setOnClickListener(this);

        //storage=FirebaseStorage.getInstance();
        mStorage= FirebaseStorage.getInstance().getReference();
        userName=getIntent().getExtras().get("userName").toString();
        mProgress=new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.upload):
                Intent galleryIntent= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                //Intent galleryIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

//                StorageReference filepath= mStorage.child("Photos").child(userName);
//                imageFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"photo.jpg");
//                pic_location=Uri.fromFile(imageFile);
//                galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT,pic_location);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;

            case(R.id.camera):
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Intent cameraIntent=new Intent("adnroid.media.action.IMAGE_CAPTURE");
                imageFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"photo.jpg");
                pic_location= FileProvider.getUriForFile(this,getApplicationContext().getPackageName()+".fileprovider" ,imageFile);
                pic_location=Uri.fromFile(imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,pic_location);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);
                break;

            case(R.id.submit):
                if(pic_location!=null){
                    StorageReference filepath = mStorage.child("Photos").child(userName).child(pic_location.getLastPathSegment());
                    filepath.putFile(pic_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(Photo.this, "Upload done", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else{
                    Toast.makeText(Photo.this, "No picture was selected", Toast.LENGTH_SHORT).show();
                }

                Intent intent=new Intent("com.example.yaeli.smart_buy.loginActivity");
                startActivity(intent);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            pic_location = data.getData();
            //StorageReference filepath = mStorage.child("Photos").child(pic_location.getLastPathSegment());
           // mProgress.setMessage("Uploading image...");
            //mProgress.show();
            image.setImageURI(pic_location);




//            filepath.putFile(pic_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(Photo.this, "Upload done", Toast.LENGTH_LONG).show();
//                }
//            });

//            pic_location = data.getData();
//            String str=getContentResolver().getType(pic_location);
//            if (str.equals("image/jpg") || str.equals("image/jpeg") || str.equals("image/png")) {
//                image.setImageURI(pic_location);
//                Toast.makeText(this, "The file was saved at " + imageFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
//            }
//            else {
//                pic_location=null;
//                Toast.makeText(this, "File type should be jpeg, jpg or png ", Toast.LENGTH_LONG).show();
//            }
        }
        else if (requestCode == CAMERA_REQUEST) {

            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

                //Uri uri = data.getData();
                //Bitmap photo = (Bitmap) data.getExtras().get("data");
                //StorageReference filepath = mStorage.child("Photos").child(userName).child("photo.jpg");
                //mProgress.setMessage("Uploading image...");
                //mProgress.show();
                image.setImageURI(pic_location);

//                filepath.putFile(pic_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        mProgress.dismiss();
//                        Toast.makeText(Photo.this, "Successfuly uploaded", Toast.LENGTH_LONG).show();
//                    }
//                });
            }
////            switch(resultCode){
////                case(RESULT_OK):
////                    if (imageFile.exists()) {
////                        image.setImageURI(pic_location);
////                        Toast.makeText(this, "The file was saved at " + imageFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
////                    }
////                    else {
////                        Toast.makeText(this, "There was a problem saving the file ", Toast.LENGTH_LONG).show();
////                    }
////                    break;
////
////                case(RESULT_CANCELED):
////                    break;
////            }
        }


    }

}
