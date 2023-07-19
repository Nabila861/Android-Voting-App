package com.example.votingsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.votingsystem.databinding.ActivitySignupCandidateBinding;
import android.view.View;
import android.widget.ImageButton;
public class SignupActivity_candidate extends AppCompatActivity {

    ActivitySignupCandidateBinding bindingCandidate;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindingCandidate = ActivitySignupCandidateBinding.inflate(getLayoutInflater());
        setContentView(bindingCandidate.getRoot());
// Find the back button and set an OnClickListener to it
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click event
                onBackPressed();
            }
        });
        databaseHelper = new DatabaseHelper(this);

        // Spinner element
        Spinner spinner = bindingCandidate.signupCourseCandidate;

        // Spinner Drop down elements
        String[] courses = new String[]{
                "Faculty of Advanced Mathematics",
                "Faculty of Advanced Physics",
                "Faculty of Traditional Chinese Medicine",
                "Faculty of Artificial Intelligence"
        };

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        bindingCandidate.signupButtonCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = bindingCandidate.signupNameCandidate.getText().toString();
                String email = bindingCandidate.signupEmailCandidate.getText().toString();
                String password = bindingCandidate.signupPasswordCandidate.getText().toString();
                String course = bindingCandidate.signupCourseCandidate.getSelectedItem().toString(); // update here
                String achievements = bindingCandidate.Achievements.getText().toString();
                String manifesto = bindingCandidate.manifesto.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || achievements.isEmpty() || manifesto.isEmpty() || course.isEmpty()) {
                    Toast.makeText(SignupActivity_candidate.this,"All fields are mandatory",Toast.LENGTH_SHORT).show();
                }
                else if (password.length() < 8) {
                    Toast.makeText(SignupActivity_candidate.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                }
                else if (!isValidEmail(email)) {
                    Toast.makeText(SignupActivity_candidate.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkUserEmail = databaseHelper.checkCandidateEmail(email);

                    if (checkUserEmail) {
                        Toast.makeText(SignupActivity_candidate.this,"You have already registered",Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean insert = databaseHelper.insertCandidateData(email, password, name, course, achievements, manifesto);

                        if (insert) {
                            Toast.makeText(SignupActivity_candidate.this,"Registration successful",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignupActivity_candidate.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            private boolean isValidEmail(String email) {
                return Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
        });
    }
}
