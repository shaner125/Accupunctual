package com.example.x15745561.accupunctual;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnAdd, btnJoin, btnLogout, btnViewWorkplace;
    private DatabaseReference current_user_db;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        // set the view now
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnJoin = (Button) findViewById(R.id.btn_join);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        btnViewWorkplace = (Button) findViewById(R.id.btn_viewWorkplace);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        current_user_db = FirebaseDatabase.getInstance().getReference().child("Data").child("Users").child(auth.getUid());
        current_user_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        username = snapshot.child("name").getValue().toString();
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddWorkplace.class));
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,JoinWorkplace.class));
            }
        });

        btnViewWorkplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBundle = new Intent(v.getContext(),ViewWorkplaces.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", username);
                intentBundle.putExtras(bundle);
                v.getContext().startActivity(intentBundle);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }});
    }
}