package com.example.votingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.votingsystem.databinding.ActivitySignupStudentBinding;
import android.view.View;
import android.widget.ImageButton;
import android.util.Patterns;

public class SignupActivity_Student extends AppCompatActivity {

    ActivitySignupStudentBinding bindingStudent;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       bindingStudent=ActivitySignupStudentBinding.inflate(getLayoutInflater());
        setContentView(bindingStudent.getRoot());
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
        Spinner spinner = bindingStudent.signupCourseStudent;

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

        bindingStudent.signupButtonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = bindingStudent.signupNameStudent.getText().toString();

                String email = bindingStudent.signupEmailStudent.getText().toString();
                String password = bindingStudent.signupPasswordStudent.getText().toString();
                String confirmPassword = bindingStudent.signupStudentConfirm.getText().toString();
                String course = bindingStudent.signupCourseStudent.getSelectedItem().toString();


                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || course.isEmpty()) {
                    // Display an error message or show a toast indicating missing fields
                    //return;
                    Toast.makeText(SignupActivity_Student.this,"All fields are mandatory",Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(SignupActivity_Student.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                }
                else if (!isValidEmail(email)) {
                    Toast.makeText(SignupActivity_Student.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }

                else{

                    if(password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if (!checkUserEmail){
                            Boolean insert=databaseHelper.insertStudentData(email,password, name, course);

                            if (insert) {
                              Toast.makeText(SignupActivity_Student.this,"Signup successfully",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(SignupActivity_Student.this,"Signup Failed",Toast.LENGTH_SHORT).show();


                            }

                        } else{
                            Toast.makeText(SignupActivity_Student.this,"User already Exists, Please login",Toast.LENGTH_SHORT).show();


                        }
                    } else{
                        Toast.makeText(SignupActivity_Student.this,"Invalid Password",Toast.LENGTH_SHORT).show();

                    }

                }

            }

            private boolean isValidEmail(String email) {
                return Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
        });


        bindingStudent.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}