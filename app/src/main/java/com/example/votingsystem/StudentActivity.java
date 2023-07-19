package com.example.votingsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;


public class StudentActivity extends AppCompatActivity {

    private TextView studentEmailTextView;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        studentEmailTextView = findViewById(R.id.studentEmail);
        Button logoutButton = findViewById(R.id.studentlogoutButton);
        Button viewCandidatesButton = findViewById(R.id.studentcandidateListButton);
        Button dashboardButton = findViewById(R.id.studentdashboardButton);
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("VotingSession", MODE_PRIVATE);

        // Fetch student email from shared preferences and set it to the TextView
        String studentEmail = getSharedPreferences("MyApp", MODE_PRIVATE).getString("studentEmail", "");
        studentEmailTextView.setText(studentEmail);

        // Handle logout button click
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();  // This closes the StudentActivity
            }
        });

        // Handle view candidates button click
        viewCandidatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the voting session has started
                if (isAdminVotingSessionStarted()) {
                    Intent intent = new Intent(StudentActivity.this, Student_Candidate_View_and_VoteActivity.class);
                    startActivity(intent);
                } else {
                    // Display a toast message indicating that the voting has not started
                    Toast.makeText(StudentActivity.this, "Voting has not yet started. Please wait for the admin to select candidates.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle dashboard button click
        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to check if the voting session has started by accessing the isVotingSessionStarted value from shared preferences
    private boolean isAdminVotingSessionStarted() {
        // Retrieve the value of isVotingSessionStarted from shared preferences
        boolean isVotingSessionStarted = sharedPreferences.getBoolean("isVotingSessionStarted", false);
        return isVotingSessionStarted;
    }
}
