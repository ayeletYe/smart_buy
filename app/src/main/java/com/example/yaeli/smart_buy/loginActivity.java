package com.example.yaeli.smart_buy;

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
    private EditText email;
    private EditText password;
    private TextView msg_login;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = (Button) findViewById(R.id.login);
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
        final ProgressDialog progressDialog= ProgressDialog.show(loginActivity.this,"Please wait","Checking Authentication...",true);
        (firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            String userId = task.getResult().getUser().getUid();

                            Toast.makeText(loginActivity.this,"Successfully signed in",Toast.LENGTH_LONG).show();

                            databaseReference.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);

                                    if (user == null) {
                                        Toast.makeText(loginActivity.this, "Failed to get user!", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    if (user.isAdmin()) {
                                        Intent intent = new Intent("com.example.yaeli.smart_buy.managerActivity");
                                        startActivity(intent);

                                    }
                                    else{
                                        Intent intent = new Intent("com.example.yaeli.smart_buy.RegisteredActivity");
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        }
                        else{
                            msg_login.setText("Incorrect user name or password");
                        }

                    }
                });

    }
}
