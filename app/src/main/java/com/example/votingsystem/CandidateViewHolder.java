package com.example.votingsystem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CandidateViewHolder extends RecyclerView.ViewHolder {
    TextView cnuName;
    TextView cnuCourse;
    TextView cnuAchievements;
    TextView cnuManifesto;
    Button btnSelect;
    Button btnReject;

    public CandidateViewHolder(@NonNull View itemView) {
        super(itemView);
        cnuName = itemView.findViewById(R.id.cnuName);
        cnuCourse = itemView.findViewById(R.id.cnuCourse);
        cnuAchievements = itemView.findViewById(R.id.cnuAchievements);
        cnuManifesto = itemView.findViewById(R.id.cnuManifesto);
        btnSelect = itemView.findViewById(R.id.btnCandidateApprove);
        btnReject = itemView.findViewById(R.id.btnCandidateReject);
    }


    public void bind(Candidate candidate) {
        cnuName.setText(candidate.getName());
        cnuCourse.setText(candidate.getCourse());
        cnuAchievements.setText(candidate.getAchievements());
        cnuManifesto.setText(candidate.getManifesto());
    }
}
