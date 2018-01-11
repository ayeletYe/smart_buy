package com.example.yaeli.smart_buy;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener{
    private static TextView hello;
    private static Spinner spinner;
    private static Button logout;
    private DatabaseReference mDatabase;
    public String email;
    public String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        hello=(TextView) findViewById(R.id.hello);
        logout=(Button) findViewById(R.id.logout);
        email=getIntent().getExtras().get("Email").toString();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.child("users").getChildren()){

                    if(d.child("Email").getValue().toString().equals(email)){
                        hello.setText("Hello "+d.getKey());
                        userName=d.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        MainFragment mf=new MainFragment();
        fragmentTransaction.add(R.id.fragment_container,mf);

        hello.setOnClickListener(this);
        logout.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.hello):
                Intent intent= new Intent("com.example.yaeli.smart_buy.myAccount");
                //int len=hello.length();
                //intent.putExtra("userName",hello.getText().subSequence(6,len));
                intent.putExtra("userName",userName);
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
