package com.example.yaeli.smart_buy;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{
    private static Button login;
    private static EditText email;
    private static EditText password;
    public static TextView msg_login;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
<<<<<<< HEAD
    //boolean isAdmin=false;
=======
    boolean isAdmin=false;
>>>>>>> ecd261c4e308c2f460db46a6c464c06ced5b33fd


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button) findViewById(R.id.login);
        email= (EditText) findViewById(R.id.Email);
        password=(EditText) findViewById(R.id.pass);
        msg_login=(TextView) findViewById(R.id.msg);
        msg_login.setText("Please Login your account");
        login.setOnClickListener(this);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        //final ProgressDialog progressDialog= ProgressDialog.show(loginActivity.this,"Please wait","Checking Authentication...",true);
        (firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(loginActivity.this,"Successfuly signed in",Toast.LENGTH_LONG);
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
<<<<<<< HEAD
                                    for(DataSnapshot d:dataSnapshot.child("users").getChildren()){
                                        if(d.child("Email").getValue().toString().equals(email.getText().toString())) {
                                            if (d.child("isAdmin").getValue().toString().equals("true")) {
                                                //isAdmin = true;
                                                Intent intent = new Intent("com.example.yaeli.smart_buy.managerActivity");
                                                intent.putExtra("Email", email.getText().toString());
                                                startActivity(intent);

                                            }
                                            else{
                                                Intent intent = new Intent("com.example.yaeli.smart_buy.RegisteredActivity");
                                                intent.putExtra("Email", email.getText().toString());
                                                startActivity(intent);
                                            }
                                        }

                                    }
=======
                                    for(DataSnapshot d:dataSnapshot.child("admin").getChildren()){
                                        if(d.child("Email").getValue().equals(email.getText().toString())){
                                            isAdmin=true;
                                            Intent intent=new Intent("com.example.yaeli.smart_buy.managerActivity");
                                            intent.putExtra("Email",email.getText().toString());
                                            startActivity(intent);

                                        }

                                    }
                                    if(!isAdmin){
                                        Intent intent = new Intent("com.example.yaeli.smart_buy.RegisteredActivity");
                                        intent.putExtra("Email",email.getText().toString());
                                        startActivity(intent);
                                    }
>>>>>>> ecd261c4e308c2f460db46a6c464c06ced5b33fd

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {


                                }
                            });

                        }
                        else{
                            //Toast.makeText(loginActivity.this,"Failed to signed in",Toast.LENGTH_LONG);

                            System.out.println(task.getException());
                            msg_login.setText("Incorrect user name or password");
                            progressDialog.dismiss();
                        }



                    }
                });

    }
}
