package com.example.x15745561.accupunctual;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewWorkplaces extends AppCompatActivity {

    //a list to store all the products
    List<Workplace> workplaceList = new ArrayList();

    //the recyclerview
    RecyclerView recyclerView;
    private Button btnBack;
    private DatabaseReference current_workplace_db ;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workplaces);

        btnBack = (Button) findViewById(R.id.btn_back);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if(!extrasBundle.isEmpty()){
            username = extrasBundle.getString("name");
        }
        final WorkPlaceAdapter adapter = new WorkPlaceAdapter(this, workplaceList, username);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);


        //initializing the productlist

        current_workplace_db = FirebaseDatabase.getInstance().getReference().child("Data").child("Workplaces");
        current_workplace_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            workplaceList.clear();
                            Log.e("TAG", "" + snapshot.getValue()); // your name values you will get here
                            for (DataSnapshot child : snapshot.getChildren()) {
                                if((child.child("Staff").child("Managers").child(username).exists()) || (child.child("Staff").child("general").child(username).exists())){
                                    Workplace tmpWorkplace = new Workplace(child.getKey(), child.child("addr1").getValue().toString());
                                    workplaceList.add(tmpWorkplace);
                                    adapter.notifyDataSetChanged();
                                    Log.e("TAG", "" + child.getValue());
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Log.e("TAG", " it's null. dumb dumb");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled", " cancelled");
            }

        }).toString();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}