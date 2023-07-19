package com.example.votingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {
    private final Context context;
    private final List<StudentCandidateList> studentCandidateList;
    private OnItemClickListener listener;

    public StudentAdapter(Context context, List<StudentCandidateList> studentCandidateList) {
        this.context = context;
        this.studentCandidateList = studentCandidateList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_student_candidate_view_and_vote_items, parent, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentCandidateList studentCandidate = studentCandidateList.get(position);
        holder.bind(studentCandidate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(studentCandidate);
                }
            }
        });

        holder.btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onVoteButtonClick(studentCandidate);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentCandidateList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(StudentCandidateList studentCandidate);
        void onVoteButtonClick(StudentCandidateList studentCandidate);
    }
}
