package com.example.yaeli.smart_buy;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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


public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String userId = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        hello=(TextView) findViewById(R.id.hello);
        Button logout = (Button) findViewById(R.id.logout);

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            userId = fbUser.getUid();
        }

        if (userId == null) {
            Toast.makeText(this, "Failed to get User ID!", Toast.LENGTH_LONG).show();
            return;
        }

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user == null) {
                    Toast.makeText(RegisteredActivity.this, "Failed to get user!", Toast.LENGTH_LONG).show();
                    return;
                }

                hello.setText("Hello "+user.getUserName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        MainFragment mf=new MainFragment();
        fragmentTransaction.add(R.id.fragment_container,mf);
        fragmentTransaction.commit();

        hello.setOnClickListener(this);
        logout.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.hello):
                Intent intent= new Intent("com.example.yaeli.smart_buy.myAccount");
                startActivity(intent);
                break;

            case(R.id.logout):
                FirebaseAuth.getInstance().signOut();

                Intent intent1= new Intent("com.example.yaeli.smart_buy.MainActivity");
                startActivity(intent1);
                break;
        }


    }
}
