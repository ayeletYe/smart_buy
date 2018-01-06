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

public class loginActivity extends AppCompatActivity implements View.OnClickListener{
    private static Button login;
    private static EditText email;
    private static EditText password;
    public static TextView msg_login;
    private FirebaseAuth firebaseAuth;


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
    }


    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        final ProgressDialog progressDialog= ProgressDialog.show(loginActivity.this,"Please wait","Checking Authentication...",true);
        (firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(loginActivity.this,"Successfuly signed in",Toast.LENGTH_LONG);
                            Intent intent = new Intent("com.example.yaeli.smart_buy.RegisteredActivity");
                            intent.putExtra("Email",firebaseAuth.getCurrentUser().getEmail().toString());
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(loginActivity.this,"Failed to signed in",Toast.LENGTH_LONG);
                        }
                        msg_login.setText("Incorrect user name or password");

                    }
                });

    }
}
