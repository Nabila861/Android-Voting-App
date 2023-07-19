package com.example.votingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.votingsystem.databinding.ActivityLoginBinding;
import com.example.votingsystem.databinding.ActivitySignupStudentBinding;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding bindingStudent;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingStudent = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bindingStudent.getRoot());

        databaseHelper = new DatabaseHelper(this);
        bindingStudent.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = bindingStudent.loginEmail.getText().toString();
                String password = bindingStudent.loginPassword.getText().toString();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isAdmin = databaseHelper.checkAdminEmailPassword(email, password);
                    boolean isStudent = databaseHelper.checkEmailPassword(email, password);

                    if (isAdmin) {
                        Toast.makeText(LoginActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(intent);
                    }
                    else {
                        if (isStudent) {
                            // Store the student's email in shared preferences
                            SharedPreferences.Editor editor = getSharedPreferences("MyApp", MODE_PRIVATE).edit();
                            editor.putString("studentEmail", email);
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "StudentActivity login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        bindingStudent.SignupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Show signup options
                showSignupOptions();

            }

            private void showSignupOptions() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Signup as")
                        .setItems(R.array.signup_options, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        // Signup as student
                                        Intent studentSignupIntent = new Intent(LoginActivity.this, SignupActivity_Student.class);
                                        startActivity(studentSignupIntent);
                                        break;
                                    case 1:
                                        // Signup as candidate
                                        Intent candidateSignupIntent = new Intent(LoginActivity.this, SignupActivity_candidate.class);
                                        startActivity(candidateSignupIntent);
                                        break;
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}