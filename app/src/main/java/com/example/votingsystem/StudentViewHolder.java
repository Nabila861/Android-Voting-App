package com.example.votingsystem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentViewHolder extends RecyclerView.ViewHolder {
    TextView txtName;
    TextView txtCourse;
    TextView txtAchievements;
    TextView txtManifesto;
    Button btnVote; // Add this line

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.apName);
        txtCourse = itemView.findViewById(R.id.apCourse);
        txtAchievements = itemView.findViewById(R.id.apAchievements);
        txtManifesto = itemView.findViewById(R.id.apManifesto);
        btnVote = itemView.findViewById(R.id.btnVote); // Add this line
    }

    public void bind(StudentCandidateList studentCandidate) {
        txtName.setText(studentCandidate.getName());
        txtCourse.setText(studentCandidate.getCourse());
        txtAchievements.setText(studentCandidate.getAchievements());
        txtManifesto.setText(studentCandidate.getManifesto());
    }
}


