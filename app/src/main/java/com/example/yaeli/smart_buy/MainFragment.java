package com.example.yaeli.smart_buy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends android.app.Fragment implements View.OnClickListener {
    TextView about;
    TextView findUs;
    TextView recipes;
    Button products;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView=inflater.inflate(R.layout.fragment_main,container,false);
        about= (TextView) myView.findViewById(R.id.aboutUs);
        findUs=(TextView) myView.findViewById(R.id.findUs);
        recipes= (TextView) myView.findViewById(R.id.recipes);
        products=(Button) myView.findViewById(R.id.compare);
        about.setOnClickListener(this);
        findUs.setOnClickListener(this);
        recipes.setOnClickListener(this);
        products.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.aboutUs):
                Intent intent=new Intent("com.example.yaeli.smart_buy.aboutUsActivity");
                startActivity(intent);
                break;

            case(R.id.findUs):
                Intent intent1=new Intent("com.example.yaeli.smart_buy.MapsActivity");
                startActivity(intent1);
                break;

            case(R.id.recipes):
                Intent intent2=new Intent("com.example.yaeli.smart_buy.recipesActivity");
                startActivity(intent2);
                break;

            case(R.id.compare):
                Intent intent3=new Intent("com.example.yaeli.smart_buy.ProductsActivity");
                startActivity(intent3);
                break;

        }

    }
}
