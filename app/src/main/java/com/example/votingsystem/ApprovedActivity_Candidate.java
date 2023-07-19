package com.example.votingsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// ApprovedActivity_Candidate.java
public class ApprovedActivity_Candidate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_candidates);

        // Find the back button and set an OnClickListener to it
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click event
                onBackPressed();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewApprovedCandidates);
        DatabaseHelper db = new DatabaseHelper(this);
        List<ApprovedCandidate> approvedCandidates = db.getAllApprovedCandidates();

        ApprovedCandidateAdapter adapter = new ApprovedCandidateAdapter(this, approvedCandidates);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
