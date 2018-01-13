package com.example.yaeli.smart_buy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class myAccount extends AppCompatActivity implements View.OnClickListener {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String userId;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        /* Get the text fields that will be filled later from DB */
        final TextView firstName=(TextView) findViewById(R.id.name);
        final TextView lastName=(TextView) findViewById(R.id.lastName);
        final TextView street=(TextView) findViewById(R.id.street);
        final TextView city=(TextView) findViewById(R.id.city);
        final TextView Email=(TextView) findViewById(R.id.email);
        final ImageView img=(ImageView) findViewById(R.id.pic);
        final TextView msg=(TextView) findViewById(R.id.msg);
        final TextView upload=(TextView) findViewById(R.id.upload);

        /* Make upload message clickable */
        upload.setOnClickListener(this);

        /* Get Firebase Storage */
        final StorageReference storage = FirebaseStorage.getInstance().getReference();

        /* Get the user ID from Firebase Auth */
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            userId = fbUser.getUid();
        } else {
            userId = null;
        }

        if (userId == null) {
            Toast.makeText(this, "Failed to get User ID!", Toast.LENGTH_LONG).show();
            return;
        }

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /* Convert node to "User" */
                user = dataSnapshot.getValue(User.class);

                if (user == null) {
                    Toast.makeText(myAccount.this, "Failed to get user!", Toast.LENGTH_LONG).show();
                    return;
                }

                firstName.setText("First Name: " + user.getFirstName());
                lastName.setText("Last Name: " + user.getLastName());
                street.setText("Street: " + user.getAddress());
                city.setText("City: " + user.getCity());
                Email.setText("Email: " + user.getEmail());

                if (user.isPhoto()) {
                    /* Show user photo using Glide */
                    StorageReference photoRef = storage.child("Photos").child(userId).child("photo.jpg");
                    Glide.with(myAccount.this).using(new FirebaseImageLoader()).load(photoRef).into(img);
                } else {
                    /* Show a message that no photo. It will be clickable so he can upload one */
                    msg.setText("you don't have a photo");
                    upload.setText("Click here to upload");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        /* The click comes from the "upload" message */
        if (!user.isPhoto()) {
            Intent intent = new Intent("com.example.yaeli.smart_buy.Photo");
            startActivity(intent);
        }
    }
}
