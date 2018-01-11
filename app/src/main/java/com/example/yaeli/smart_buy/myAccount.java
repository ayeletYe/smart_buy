package com.example.yaeli.smart_buy;

<<<<<<< HEAD
import android.content.Intent;
=======
>>>>>>> ecd261c4e308c2f460db46a6c464c06ced5b33fd
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
<<<<<<< HEAD
=======

import java.io.File;
>>>>>>> ecd261c4e308c2f460db46a6c464c06ced5b33fd

import java.io.File;

public class myAccount extends AppCompatActivity implements View.OnClickListener {
    TextView firstName;
    TextView lastName;
    TextView street;
    TextView city;
    TextView Email;
    ImageView img;
    DatabaseReference database;
<<<<<<< HEAD
    private String email;
    StorageReference storage;
    String userId;
    //boolean isAdmin=false;
    String photo;
    TextView msg;
    TextView upload;
=======
    private String userName;
    StorageReference storage;
>>>>>>> ecd261c4e308c2f460db46a6c464c06ced5b33fd


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        firstName=(TextView) findViewById(R.id.name);
        lastName=(TextView) findViewById(R.id.lastName);
        street=(TextView) findViewById(R.id.street);
        city=(TextView) findViewById(R.id.city);
        Email=(TextView) findViewById(R.id.email);
        img=(ImageView) findViewById(R.id.pic);
        msg=(TextView) findViewById(R.id.msg);
        upload=(TextView) findViewById(R.id.upload);

<<<<<<< HEAD
        upload.setOnClickListener(this);
        storage= FirebaseStorage.getInstance().getReference();
        email=getIntent().getExtras().get("Email").toString();
=======
        storage= FirebaseStorage.getInstance().getReference();
        userName=getIntent().getExtras().get("userName").toString();
>>>>>>> ecd261c4e308c2f460db46a6c464c06ced5b33fd
        database=FirebaseDatabase.getInstance().getReference();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.child("users").getChildren()){
                    if(d.child("Email").getValue().equals(email)){
                        firstName.setText("First Name: "+d.child("firstName").getValue().toString());
                        lastName.setText("Last Name: "+d.child("lastName").getValue().toString());
                        street.setText("Street: "+d.child("address").getValue().toString());
                        city.setText("City: "+d.child("city").getValue().toString());
                        Email.setText("Email: "+d.child("Email").getValue().toString());
                        userId=d.getKey();
                        photo=d.child("photo").getValue().toString();
//                        if(d.child("isAdmin").getValue().toString().equals("true")){
//                            //Toast.makeText(myAccount.this,"hello",Toast.LENGTH_LONG).show();
//                            isAdmin=true;
//                        }

                        if(photo.equals("true")){
                            StorageReference photoRef=storage.child("Photos").child(userId).child("photo.jpg");
                            Glide.with(myAccount.this).using(new FirebaseImageLoader()).load(photoRef).into(img);
                        }
                        else{
                            msg.setText("you don't have a photo");
                            upload.setText("Click here to upload");
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

<<<<<<< HEAD
    }

    @Override
    public void onClick(View view) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.child("users").getChildren()){
                    if(d.child("Email").getValue().toString().equals(email)){
                        photo=d.child("photo").getValue().toString();
                        userId=d.getKey();
                    }
                }
                if(photo.equals("false")){
                    Intent intent=new Intent("com.example.yaeli.smart_buy.Photo");
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                }
=======
        StorageReference photoRef=storage.child("Photos").child(userName).child("photo.jpg");
        Glide.with(this).using(new FirebaseImageLoader()).load(photoRef).into(img);
        //Uri file=Uri.fromFile(new File(photoRef.getPath()));
        //img.setImageURI(file);




>>>>>>> ecd261c4e308c2f460db46a6c464c06ced5b33fd

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
