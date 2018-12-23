package com.example.x15745561.accupunctual;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.res.Configuration;
import java.util.Locale;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

public class JoinWorkplace extends AppCompatActivity {

    private Button btnBack, btnJoin;
    private FirebaseAuth auth;
    private EditText inputWorkplaceName, inputWorkplaceKey;
    private DatabaseReference mDatabase;
    private DatabaseReference current_user_db;
    private DatabaseReference current_workplace_db;
    private String name;
    private Map<String, String> workPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_workplace);
        workPlaces = new HashMap<>();
        name = "";
        inputWorkplaceName = (EditText) findViewById(R.id.workplaceName);
        inputWorkplaceKey = (EditText) findViewById(R.id.JoinPassword);
        btnBack = (Button) findViewById(R.id.btn_back);
        btnJoin = (Button) findViewById(R.id.btn_joinWorkplace);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Data");
        current_user_db = mDatabase.child("Users").child(auth.getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.child("Users").child(auth.getUid()).child("name").getValue() != null) { ;
                        try {
                            name = snapshot.child("Users").child(auth.getUid()).child("name").getValue().toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", " it's null.");
                    }
                    for(DataSnapshot workplaceSnapShot : snapshot.child("Workplaces").getChildren()){
                        workPlaces.put(workplaceSnapShot.getKey(), workplaceSnapShot.child("WorkplaceKey").getValue().toString());
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

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String workPlaceName = inputWorkplaceName.getText().toString().trim().toLowerCase();
                final String workplaceKey = inputWorkplaceKey.getText().toString().trim().toLowerCase();
                final String workerName = name;
                current_workplace_db = mDatabase.child("Workplaces").child(workPlaceName);

                if (TextUtils.isEmpty(workPlaceName)) {
                    Toast.makeText(getApplicationContext(), "Please enter workplace name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(workplaceKey)) {
                    Toast.makeText(getApplicationContext(), "Please enter workplace key!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (workPlaces.get(workPlaceName) != null){
                    if (workPlaces.get(workPlaceName).equals(workplaceKey)){
                        current_user_db.child("Workplaces").child(workPlaceName).setValue(true);
                        current_workplace_db.child("Staff").child("general").child(name).setValue(true);
                        current_workplace_db.child("clockedout").child(name).setValue("00:00:00");
                        Toast.makeText(getApplicationContext(), "You have joined :"+workPlaceName+"!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Workplace Key entered incorrectly!", Toast.LENGTH_SHORT).show();
                    }

                }

                else {
                    Toast.makeText(getApplicationContext(), "Workplace Name entered incorrectly!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
