package com.example.yaeli.smart_buy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class signin_btnFragment extends android.app.Fragment implements View.OnClickListener {
    TextView signin_btn;

    public signin_btnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView=inflater.inflate(R.layout.fragment_signin_btn,container,false);
        signin_btn= (TextView) myView.findViewById(R.id.sign_btn);
        signin_btn.setOnClickListener(this);

        return myView;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_signin_btn, container, false);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent("com.example.yaeli.smart_buy.signin");
        startActivity(intent);
    }
}
