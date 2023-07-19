package com.example.votingsystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import java.util.List;
import java.util.HashMap;

public class WaitingActivity_Candidate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_candidate);  // Changed here
// Find the back button and set an OnClickListener to it
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click event
                onBackPressed();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCandidates);
        DatabaseHelper db = new DatabaseHelper(this);
        List<Candidate> candidates = db.getAllCandidates();

        HashMap<String, Integer> selectedCounts = new HashMap<>();

        CandidateAdapter adapter = new CandidateAdapter(this, candidates, selectedCounts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
