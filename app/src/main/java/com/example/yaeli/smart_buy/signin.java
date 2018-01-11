package com.example.yaeli.smart_buy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.String;

public class signin extends AppCompatActivity implements View.OnClickListener{
    private static EditText userName;
    private static EditText password;
    private static EditText firstName;
    private static EditText lastName;
    private static EditText address;
    private static EditText city;
    private static EditText Email;
    private static TextView msg;
    public boolean userExist;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private static Button nextBtn;

    //public static UserLocalStore userLocalStore=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        nextBtn= (Button) findViewById(R.id.next);
        userName= (EditText) findViewById(R.id.UserName);
        password= (EditText) findViewById(R.id.password);
        firstName= (EditText) findViewById(R.id.name);
        lastName= (EditText) findViewById(R.id.lastName);
        address= (EditText) findViewById(R.id.street);
        city= (EditText) findViewById(R.id.city);
        Email= (EditText) findViewById(R.id.Email);
        msg= (TextView) findViewById(R.id.msg);
        //userLocalStore=new UserLocalStore(this);
        nextBtn.setOnClickListener(this);
        //userExist=false;

        firebaseAuth=FirebaseAuth.getInstance();



    }



    @Override
    public void onClick(View v) {
        mDatabase= FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref;
        final String email = Email.getText().toString();
        final String pass = password.getText().toString();
        if (email.length() == 0) {
            msg.setText("Invalid Email");
        }
        if (pass.length() < 8) {
            msg.setText("password must contain at least 4 characters");
        }

        else {

            if ((firstName.getText().length() == 0) || (lastName.getText().length() == 0) || (city.getText().length() == 0) || (userName.getText().length() == 0)) {
                msg.setText("check requires fields");
            }

            else {
                if (address.getText().length() == 0) {
                    address.setText("*UNKNOWN*");
                    address.setTextColor(Color.RED);
                }
            }

//            final String name=userName.getText().toString();
//            mDatabase.addValueEventListener(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    for(DataSnapshot _user:dataSnapshot.child("users").getChildren()){
//                        //Toast.makeText(signin.this, _user.getKey(), Toast.LENGTH_LONG).show();
//                        if(_user.getKey().equals(name))
//                            userExist=true;
//                            break;
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Toast.makeText(signin.this, "Failed to read value", Toast.LENGTH_LONG).show();
//                }
//            });

            //if(!userExist) {

                final ProgressDialog progressDialog = ProgressDialog.show(signin.this, "Please wait", "Registering...", true);
                (firebaseAuth.createUserWithEmailAndPassword(email, pass))
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    User user = new User(false,Email.getText().toString(),userName.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), address.getText().toString(), city.getText().toString());
                                    String userId=mDatabase.push().getKey();
                                    mDatabase.child("users").child(userId).setValue(user);
                                    Toast.makeText(signin.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent("com.example.yaeli.smart_buy.Photo");
                                    intent.putExtra("userId",userId);
                                    startActivity(intent);
                                } else {
                                    FirebaseAuthException e = (FirebaseAuthException) task.getException();
                                    Toast.makeText(signin.this, "Failed Registration: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.hide();
                                    return;
                                }
                            }
                        });
//            }
//            else{
//                msg.setText("user name already exist!");
//            }

        }

    }

}
