package com.example.yaeli.smart_buy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.google.firebase.analytics.FirebaseAnalytics;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends android.app.Fragment implements View.OnClickListener {
    TextView about;
    TextView findUs;
    TextView recipes;
    Button products;

    private FirebaseAnalytics mFirebaseAnalytics;

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

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.getActivity());

        return myView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        String content;

        switch(v.getId()){
            case(R.id.aboutUs):
                intent = new Intent("com.example.yaeli.smart_buy.aboutUsActivity");
                break;

            case(R.id.findUs):
                intent = new Intent("com.example.yaeli.smart_buy.MapsActivity");
                break;

            case(R.id.recipes):
                intent = new Intent("com.example.yaeli.smart_buy.recipesActivity");
                break;

            case(R.id.compare):
                intent = new Intent("com.example.yaeli.smart_buy.ProductsActivity");
                break;
        }

        if (intent != null) {
            /**
             * Log event of content select
             */
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT, intent.getAction());
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            startActivity(intent);
        }
    }
}
