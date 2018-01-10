package com.example.yaeli.smart_buy;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class myAccount extends AppCompatActivity {
    TextView firstName;
    TextView lastName;
    TextView street;
    TextView city;
    TextView Email;
    ImageView img;
    DatabaseReference database;
    private String userName;
    StorageReference storage;


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

        storage= FirebaseStorage.getInstance().getReference();
        userName=getIntent().getExtras().get("userName").toString();
        database=FirebaseDatabase.getInstance().getReference();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.child("users").getChildren()){
                    if(d.getKey().equals(userName)){
                        firstName.setText("First Name: "+d.child("firstName").getValue().toString());
                        lastName.setText("Last Name: "+d.child("lastName").getValue().toString());
                        street.setText("Street: "+d.child("address").getValue().toString());
                        city.setText("City: "+d.child("city").getValue().toString());
                        Email.setText("Email: "+d.child("Email").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        StorageReference photoRef=storage.child("Photos").child(userName + ".jpg");
        Glide.with(this).using(new FirebaseImageLoader()).load(photoRef).into(img);
        //Uri file=Uri.fromFile(new File(photoRef.getPath()));
        //img.setImageURI(file);







//        String name=signin.userLocalStore.getUserData().firstname;
//        firstName.setText("First Name: "+name);
//        String last_name=signin.userLocalStore.getUserData().lastName;
//        lastName.setText("Last Name: "+last_name);
//        String _street=signin.userLocalStore.getUserData().address;
//        street.setText("Street: "+_street);
//        String _city=signin.userLocalStore.getUserData().city;
//        city.setText("City: "+_city);
//        String _email=signin.userLocalStore.getUserData().Email;
//        Email.setText("Email: "+_email);
//        if(Photo.pic_location!=null){
//            img.setImageURI(Photo.pic_location);
//        }

    }
}
