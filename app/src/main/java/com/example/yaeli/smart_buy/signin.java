package com.example.yaeli.smart_buy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.String;

public class signin extends AppCompatActivity implements View.OnClickListener{
    private EditText userName;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText address;
    private EditText city;
    private EditText Email;
    private TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        /* Get text fields from GUI */
        userName = (EditText) findViewById(R.id.UserName);
        password = (EditText) findViewById(R.id.password);
        firstName = (EditText) findViewById(R.id.name);
        lastName = (EditText) findViewById(R.id.lastName);
        address = (EditText) findViewById(R.id.street);
        city = (EditText) findViewById(R.id.city);
        Email = (EditText) findViewById(R.id.Email);
        msg = (TextView) findViewById(R.id.msg);

        /* Make "next" button clickable */
        Button nextBtn = (Button) findViewById(R.id.next);
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        /* Get Firebase Auth reference */
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        /* Get Firebase Database reference */
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        final String email = Email.getText().toString();
        final String pass = password.getText().toString();

        /* Check email is not empty */
        if (email.length() == 0) {
            msg.setText("Invalid Email");
        }
        /* Check password length */
        else if (pass.length() < 8) {
            msg.setText("password must contain at least 8 characters");
        }
        /* Check other required fields */
        else if ((firstName.getText().length() == 0) || (lastName.getText().length() == 0) || (city.getText().length() == 0) || (userName.getText().length() == 0)) {
            msg.setText("check requires fields");
        }
        /* Proceed with signin */
        else {
            /* Fill the non-required address field if it's empty */
            if (address.getText().length() == 0) {
                address.setText("*UNKNOWN*");
                address.setTextColor(Color.RED);
            }

            /* Show progress dialog */
            final ProgressDialog progressDialog = ProgressDialog.show(signin.this, "Please wait", "Registering...", true);

            /* Try to register to Firebase Auth using email and password */
            firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                /* Signin was successful */
                                User user = new User(false, Email.getText().toString(), userName.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), address.getText().toString(), city.getText().toString());
                                /* Get userId from Firebase Auth */
                                String userId = task.getResult().getUser().getUid();

                                /* Save this user to Firebase Database using userId created by Firebase Auth */
                                mDatabase.child("users").child(userId).setValue(user);

                                Toast.makeText(signin.this, "Registered successfully", Toast.LENGTH_LONG).show();

                                /* Open a user photo selection activity */
                                Intent intent = new Intent("com.example.yaeli.smart_buy.Photo");
                                startActivity(intent);
                            } else {
                                /* Signin has failed */
                                FirebaseAuthException e = (FirebaseAuthException) task.getException();
                                String reason = "Unknown!";

                                if (e != null) {
                                    reason = e.getMessage();
                                }

                                /* Hide progress dialog and show a message with the failure message */
                                progressDialog.dismiss();
                                Toast.makeText(signin.this, "Failed Registration: " + reason, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

}
