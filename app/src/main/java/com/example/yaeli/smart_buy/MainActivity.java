package com.example.yaeli.smart_buy;

import android.content.Intent;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends  AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Make "signin" and "login" button clickable */
        Button loginBtn = (Button) findViewById(R.id.Login);
        Button signInBtn = (Button) findViewById(R.id.Signin);
        loginBtn.setOnClickListener(this);
        signInBtn.setOnClickListener(this);

        /* Setup "Main" fragment */
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mf = new MainFragment();
        fragmentTransaction.add(R.id.fragment_container, mf);
        fragmentTransaction.commit();
    }



    @Override
    public void onClick(View v) {
        /* Open activity depending on clicked button */
        switch(v.getId()){
            case(R.id.Signin):
                Intent intent= new Intent("com.example.yaeli.smart_buy.signin");
                startActivity(intent);
                break;

            case(R.id.Login):
                Intent intent1=new Intent("com.example.yaeli.smart_buy.loginActivity");
                startActivity(intent1);
                break;
        }
    }
}
