package com.example.x15745561.accupunctual;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class JoinWorkplace extends AppCompatActivity {

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
        setContentView(R.layout.activity_join_workplace);

        btnBack = (Button) findViewById(R.id.btn_back);

        auth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
