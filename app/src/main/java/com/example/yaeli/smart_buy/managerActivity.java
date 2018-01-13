package com.example.yaeli.smart_buy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class managerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String userId = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        /* Make button clickable */
        msg=(TextView) findViewById(R.id.msg);
        Button products = (Button) findViewById(R.id.products);
        Button recipes = (Button) findViewById(R.id.recipe);
        Button logout = (Button) findViewById(R.id.logout);

        products.setOnClickListener(this);
        recipes.setOnClickListener(this);
        msg.setOnClickListener(this);
        logout.setOnClickListener(this);

        /* Get the user ID from Firebase Auth */
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            userId = fbUser.getUid();
        }

        if (userId == null) {
            Toast.makeText(this, "Failed to get User ID!", Toast.LENGTH_LONG).show();
            return;
        }

        /* Get current user from "users" table using "userId" */
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /* Convert node to "User" */
                User user = dataSnapshot.getValue(User.class);

                if (user == null) {
                    Toast.makeText(managerActivity.this, "Failed to get user!", Toast.LENGTH_LONG).show();
                    return;
                }

                msg.setText("Hello Manager "+user.getUserName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        /* Choose the correct activity to open according to clicked button */
        switch (v.getId()) {
            case (R.id.products):
                intent = new Intent("com.example.yaeli.smart_buy.inStockProductsActivity");
                break;

            case (R.id.recipe):
                intent = new Intent("com.example.yaeli.smart_buy.addRecipeActivity");
                break;

            case (R.id.msg):
                intent = new Intent("com.example.yaeli.smart_buy.myAccount");
                break;

            case R.id.logout:
                /* Signout user from Firebase Auth */
                FirebaseAuth.getInstance().signOut();

                intent = new Intent("com.example.yaeli.smart_buy.MainActivity");
                break;

        }

        if (intent != null)
            startActivity(intent);
    }
}
