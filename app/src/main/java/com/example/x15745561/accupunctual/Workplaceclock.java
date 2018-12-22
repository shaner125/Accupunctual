package com.example.x15745561.accupunctual;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Workplaceclock extends AppCompatActivity {

    private String workplaceName;
    private Button btnBack, btnClockIn, btnClockOut, btnManagement,btnStartBreak, btnEndBreak;
    private DatabaseReference current_user_db, current_workplace_db;
    private FirebaseAuth auth;
    private String name;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workplaceclock);
        title = (TextView) findViewById(R.id.workplace_title_id);
        auth = FirebaseAuth.getInstance();
        
        btnBack = (Button) findViewById(R.id.btn_back);
        btnClockIn = (Button) findViewById(R.id.btn_clockin);
        btnClockOut = (Button) findViewById(R.id.btn_clockout);
        btnClockOut.setVisibility(View.GONE);
        btnManagement = (Button) findViewById(R.id.btn_management);
        btnManagement.setVisibility(View.GONE);
        current_user_db = FirebaseDatabase.getInstance().getReference().child("Data").child("Users").child(auth.getUid());


        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if(!extrasBundle.isEmpty()){
            workplaceName = extrasBundle.getString("workplacename");
            name = extrasBundle.getString("username");
            title.setText(workplaceName);
        }
        current_workplace_db = FirebaseDatabase.getInstance().getReference().child("Data").child("Workplaces").child(workplaceName);
        current_workplace_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.child("Staff").child("Managers").child(name).exists()) {
                        btnManagement.setVisibility(View.VISIBLE);
                    } else {
                        Log.e("TAG", "Not a manager.");
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
        btnClockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                current_workplace_db.child("clockedout").child(name).removeValue();
                current_workplace_db.child("clockedin").child(name).setValue(mdformat.format(calendar.getTime()));
                btnClockIn.setVisibility(View.GONE);
                btnClockOut.setVisibility(View.VISIBLE);
            }
        });
        btnClockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                current_workplace_db.child("clockedin").child(name).removeValue();
                current_workplace_db.child("clockedout").child(name).setValue(mdformat.format(calendar.getTime()));
                btnClockOut.setVisibility(View.GONE);
                btnClockIn.setVisibility(View.VISIBLE);
            }
        });
    }
}
