package com.example.yaeli.smart_buy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView mListView;
    private ArrayList<String> mProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        mProducts = new ArrayList<>();

        mListView = (ListView) findViewById(R.id.listViewProducts);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mProducts);
        mListView.setAdapter(arrayAdapter);

        final String[] featuresEng ={"name","producer","calories","protein","carbohydrates","sugars","total fat","saturated fat","trans fat" ,"cholesterol","sodium"};
        final String[] featuresHe  ={"שם",  "יצרן",    "קלוריות", "חלבונים","פחמימות" ,     "סוכרים","שומנים" ,  "שומן רווי",    "שומן טרנס"  ,"קולסטרול",  "נתרן"};

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.child("products").getChildren()){

                    String value="";
                    for(int  i= 0; i<featuresEng.length; i++){
                        System.out.println(value);
                        System.out.println(featuresEng[i]);
                        System.out.println(d.hasChild(featuresEng[i]));
                        if(d.hasChild(featuresEng[i])) {
                            value += featuresHe[i] + ": " + d.child(featuresEng[i]).getValue()+"\n";
                        }
                    }
                    mProducts.add(value);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
