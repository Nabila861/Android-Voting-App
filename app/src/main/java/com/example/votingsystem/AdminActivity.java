package com.example.votingsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    private TextView adminEmailTextView;
    private DatabaseHelper databaseHelper;
    private Button startVotingButton;

    private Button endVotingButton;

    private boolean isStartVotingClicked = false;
    private boolean isVotingSessionStarted = false;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminEmailTextView = findViewById(R.id.adminEmail);
        Button logoutButton = findViewById(R.id.logoutButton);
        Button listCandidatesButton = findViewById(R.id.candidateListButton);
        Button approvedCandidatesButton = findViewById(R.id.chosenCandidatesButton);
        Button dashboardButton = findViewById(R.id.dashboardButton);
        startVotingButton = findViewById(R.id.startVotingButton);
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("VotingSession", MODE_PRIVATE);

        // Fetch admin email and set it to the TextView
        String adminEmail = databaseHelper.getAdminEmail();
        adminEmailTextView.setText(adminEmail);

        // Retrieve the voting session state from shared preferences
        isStartVotingClicked = sharedPreferences.getBoolean("isStartVotingClicked", false);
        isVotingSessionStarted = sharedPreferences.getBoolean("isVotingSessionStarted", false);

        // Handle logout button click
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove only the specific preferences related to the admin's session
                sharedPreferences.edit().remove("adminEmail").apply();

                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();  // This closes the AdminActivity
            }
        });


        // Handle list candidates button click
        listCandidatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, WaitingActivity_Candidate.class);
                startActivity(intent);
            }
        });

        approvedCandidatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Approved Candidates button clicked"); // Console log for button click
                Intent intent = new Intent(AdminActivity.this, ApprovedActivity_Candidate.class);
                startActivity(intent);
            }
        });

        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Entering Dashboard"); // Console log for button click
                Intent intent = new Intent(AdminActivity.this, DashboardActivity.class);
                intent.putExtra("isStartVotingClicked", isStartVotingClicked);
                intent.putExtra("isVotingSessionStarted", isVotingSessionStarted);
                startActivity(intent);
            }
        });

        // Handle Start Voting Session button click
        // Handle Start Voting Session button click
        startVotingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartVotingAllowed()) {
                    isStartVotingClicked = true;
                    isVotingSessionStarted = true;
                    updateVotingProcessText();
                    saveVotingSessionState();

                    // Save the voting session status in SharedPreferences
                    sharedPreferences.edit().putBoolean("isVotingSessionStarted", true).apply();
                } else {
                    System.out.println("Start Voting Button Clicked, but no candidates have been approved by the admin");
                    Toast.makeText(AdminActivity.this, "Voting cannot start since no candidates have been approved by the admin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button endVotingButton = findViewById(R.id.endVotingButton);
        endVotingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("VotingSession", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isVotingSessionActive", false);
                editor.apply();
            }
        });



        // Update the initial state of the voting process text
        updateVotingProcessText();
    }

    private boolean isStartVotingAllowed() {
        // Check if there is at least one entry in the approved_candidates table
        int approvedCandidatesCount = databaseHelper.getApprovedCandidatesCount();
        return approvedCandidatesCount > 0;
    }

    private void updateVotingProcessText() {
        // Nothing to do here, as the text is updated in the DashboardActivity
    }

    private void saveVotingSessionState() {
        // Save the voting session state in shared preferences
        sharedPreferences.edit()
                .putBoolean("isStartVotingClicked", isStartVotingClicked)
                .putBoolean("isVotingSessionStarted", isVotingSessionStarted)
                .apply();
    }
}
