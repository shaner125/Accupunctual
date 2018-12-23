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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.res.Configuration;
import java.util.Locale;

public class managementActivity extends AppCompatActivity {

    //a list to store all the products
    List<User> userList = new ArrayList();

    //the recyclerview
    RecyclerView recyclerView;
    private Button btnBack;
    private DatabaseReference current_workplace_db ;
    private String workplaceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        btnBack = (Button) findViewById(R.id.btn_back);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if(!extrasBundle.isEmpty()){
            workplaceName = extrasBundle.getString("workplaceName");
        }
        final UserClockAdapter adapter = new UserClockAdapter(this, userList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);


        //initializing the productlist

        current_workplace_db = FirebaseDatabase.getInstance().getReference().child("Data").child("Workplaces").child(workplaceName);
        current_workplace_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            userList.clear();
                            Log.e("TAG", "" + snapshot.getValue()); // your name values you will get here
                            if(snapshot.child("clockedin").exists()){
                            for (DataSnapshot child : snapshot.child("clockedin").getChildren()) {
                                Log.e("TAG", "GKJHLSGHFKJDSGHFJD");
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                                User tmpUser = new User(child.getKey(),"Clocked In", mdformat.format(calendar.getTime()));

                                if(!userList.isEmpty()){
                                    userList.add(0,tmpUser);
                                    adapter.notifyDataSetChanged();
                                    Log.e("TAG", "GKJHLSGHFKJDSGHFJD");
                                }
                                else {
                                    userList.add(tmpUser);
                                    adapter.notifyDataSetChanged();
                                    Log.e("TAG", "SCHMOOLI");
                                }
                                }
                            }
                            else if(snapshot.child("clockedout").exists()){
                                for (DataSnapshot child : snapshot.child("clockedout").getChildren()) {
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                                    User tmpUser = new User(child.getKey(),"Clocked Out", mdformat.format(calendar.getTime()));
                                    userList.add(tmpUser);
                                    adapter.notifyDataSetChanged();
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