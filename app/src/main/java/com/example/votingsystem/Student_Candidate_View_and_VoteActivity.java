package com.example.votingsystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import java.util.List;

public class Student_Candidate_View_and_VoteActivity extends AppCompatActivity {

    private static final String TAG = "VoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_candidate_view_and_vote);
// Find the back button and set an OnClickListener to it
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click event
                onBackPressed();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.studentcandidatesRecyclerView);
        DatabaseHelper db = new DatabaseHelper(this);

        // Retrieve the logged-in student's email from the session
        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        String studentEmail = prefs.getString("studentEmail", "");

        // Retrieve the logged-in student's course
        String studentCourse = db.getStudentCourse(studentEmail);

        List<StudentCandidateList> studentCandidateList = db.getApprovedCandidatesByCourse(studentCourse);

        StudentAdapter adapter = new StudentAdapter(this, studentCandidateList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(StudentCandidateList studentCandidate) {
                // This method is not used for voting, so we can leave it empty
            }

            @Override
            public void onVoteButtonClick(StudentCandidateList studentCandidate) {
                // Log the button press
                Log.d(TAG, "Button clicked for candidate: " + studentCandidate.getName());

                String selectedCandidate = studentCandidate.getName();

                SharedPreferences sharedPreferences = getSharedPreferences("VotingSession", MODE_PRIVATE);
                boolean isVotingSessionActive = sharedPreferences.getBoolean("isVotingSessionActive", true);

                if (!isVotingSessionActive) {
                    Toast.makeText(Student_Candidate_View_and_VoteActivity.this, "Voting has stopped.", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean alreadyVoted = db.hasVoted(studentEmail);
                if (alreadyVoted) {
                    Toast.makeText(Student_Candidate_View_and_VoteActivity.this, "You have already voted for a candidate. Please wait for the voting results.", Toast.LENGTH_SHORT).show();
                } else {
                    db.incrementVoteCount(selectedCandidate);
                    db.insertVoteRecord(studentEmail, selectedCandidate, studentCourse);
                    Toast.makeText(Student_Candidate_View_and_VoteActivity.this, "Vote recorded successfully.", Toast.LENGTH_SHORT).show();

                    // Remove the selected candidate from the list
                    studentCandidateList.remove(studentCandidate);
                    adapter.notifyDataSetChanged();

                    // Check if there are remaining candidates
                    if (studentCandidateList.isEmpty()) {
                        // No more candidates, finish the activity
                        finish();
                    } else {
                        // Scroll to the next candidate
                        recyclerView.scrollToPosition(0);
                    }
                }
            }
        });
    }
}



