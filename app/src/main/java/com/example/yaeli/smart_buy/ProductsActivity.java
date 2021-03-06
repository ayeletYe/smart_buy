package com.example.yaeli.smart_buy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        /*
         * Link ArrayList to GUI
         */
        final ArrayList<String> mProducts = new ArrayList<>();
        ListView mListView = (ListView) findViewById(R.id.listViewProducts);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mProducts);
        mListView.setAdapter(arrayAdapter);

        final String[] featuresEng = {"name", "producer", "calories", "protein", "carbohydrates", "sugars", "total fat", "saturated fat", "trans fat", "cholesterol", "sodium"};
        final String[] featuresHe = {"שם", "יצרן", "קלוריות", "חלבונים", "פחמימות", "סוכרים", "שומנים", "שומן רווי", "שומן טרנס", "קולסטרול", "נתרן"};

        /*
         * Get all products from "products" table
         */
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.child("products").getChildren()){

                    /* Each product is a long string (with line breaks) that goes into the ArrayList */
                    String value="";
                    for(int  i= 0; i<featuresEng.length; i++){
                        if(d.hasChild(featuresEng[i])) {
                            value += featuresHe[i] + ": " + d.child(featuresEng[i]).getValue()+"\n";
                        }
                    }

                    mProducts.add(value);
                }

                /* Notify GUI about the change in the array */
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
