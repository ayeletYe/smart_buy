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

        email= (EditText) findViewById(R.id.Email);
        password=(EditText) findViewById(R.id.pass);

        /* Setup message box on top of the screen */
        msg_login=(TextView) findViewById(R.id.msg);
        msg_login.setText("Please Login your account");

        /* Make "login" button clickable */
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        /* Firebase Auth instance */
        firebaseAuth=FirebaseAuth.getInstance();

        /* Firebase Database instance */
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public void onClick(View v) {
        /* On click immediately show progress dialog */
        final ProgressDialog progressDialog = ProgressDialog.show(loginActivity.this, "Please wait", "Checking Authentication...", true);

        /* Check email and password with Firebase Auth */
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        /* Hide progress dialog after authentication finished */
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            /* Get the user ID of the successfully logged in user */
                            String userId = task.getResult().getUser().getUid();

                            Toast.makeText(loginActivity.this,"Successfully signed in",Toast.LENGTH_LONG).show();

                            /* Check in DB using user ID what type of user is this; regular or manager */
                            databaseReference.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    /* Convert node to "User" */
                                    User user = dataSnapshot.getValue(User.class);

                                    if (user == null) {
                                        Toast.makeText(loginActivity.this, "Failed to get user!", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    /* Show different activity if manager */
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
                            /* Show error message in box on top of the screen */
                            msg_login.setText("Incorrect user name or password");
                        }
                    }
                });

    }
}
