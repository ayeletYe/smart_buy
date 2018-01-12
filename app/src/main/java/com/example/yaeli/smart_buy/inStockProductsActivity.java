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

public class inStockProductsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView mListView;
    private ArrayList<String> mProductsInStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_stock_products);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        mProductsInStock = new ArrayList<>();

        mListView = (ListView) findViewById(R.id.listViewStock);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mProductsInStock);
        mListView.setAdapter(arrayAdapter);

        final String[] featuresEng ={"name","quantity"};
        final String[] featuresHe  ={"שם",  "מוצרים בחנות"};

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.child("stock").getChildren()){

                    String value="";
                    for(int  i= 0; i<featuresEng.length; i++){
                        System.out.println(value);
                        System.out.println(featuresEng[i]);
                        System.out.println(d.hasChild(featuresEng[i]));
                        if(d.hasChild(featuresEng[i])) {
                            value += featuresHe[i] + ": " + d.child(featuresEng[i]).getValue()+"\n";
                        }
                    }
                    mProductsInStock.add(value);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
