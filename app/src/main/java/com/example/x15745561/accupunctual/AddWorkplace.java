package com.example.x15745561.accupunctual;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddWorkplace extends AppCompatActivity {

    private Button btnBack, btnSaveWorkplace;
    private FirebaseAuth auth;
    private EditText inputWorkplaceName, inputAddr1, inputAddr2, inputCity, inputWorkplaceKey;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;
    private DatabaseReference current_user_db ;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workplace);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Data");
        current_user_db = mDatabase.child("Users").child(auth.getUid());
        inputWorkplaceName = (EditText) findViewById(R.id.workplaceName);
        inputWorkplaceKey = (EditText) findViewById(R.id.WorkplaceKey);
        inputAddr1 = (EditText) findViewById(R.id.AddressLine1);
        inputAddr2 = (EditText) findViewById(R.id.AddressLine2);
        inputCity = (EditText) findViewById(R.id.Addresscity);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnBack = (Button) findViewById(R.id.btn_back);
        btnSaveWorkplace = (Button) findViewById(R.id.btn_saveWorkplace);

        auth = FirebaseAuth.getInstance();

        current_user_db.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            Log.e("TAG", "" + snapshot.getValue()); // your name values you will get here
                            name = snapshot.getValue().toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", " it's null.");
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

        btnSaveWorkplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String workPlaceName = inputWorkplaceName.getText().toString().trim();
                final String workplaceKey = inputWorkplaceKey.getText().toString().trim();
                final String addr1 = inputAddr1.getText().toString().trim();
                final String addr2 = inputAddr2.getText().toString().trim();
                final String city = inputCity.getText().toString().trim();


                if (TextUtils.isEmpty(workPlaceName)) {
                    Toast.makeText(getApplicationContext(), "Please enter workplace name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(workplaceKey)) {
                    Toast.makeText(getApplicationContext(), "Please enter workplace key!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(addr1)) {
                    Toast.makeText(getApplicationContext(), "Please enter Address line 1!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(city)) {
                    Toast.makeText(getApplicationContext(), "Please enter city!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (workplaceKey.length() < 3) {
                    Toast.makeText(getApplicationContext(), "WorkplaceKey must have at least 3 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                                DatabaseReference current_workplace_db = mDatabase.child("Workplaces").child(workPlaceName);
                                current_workplace_db.child("WorkplaceKey").setValue(workplaceKey);
                                current_workplace_db.child("Managers").child(name).setValue(true);
                                current_workplace_db.child("addr1").setValue(addr1);
                                current_workplace_db.child("addr2").setValue(addr2);
                                current_workplace_db.child("city").setValue(city);
                                progressBar.setVisibility(View.GONE);
                                finish();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

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
