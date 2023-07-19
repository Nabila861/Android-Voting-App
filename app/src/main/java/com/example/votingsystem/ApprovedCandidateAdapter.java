package com.example.votingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ApprovedCandidateAdapter extends RecyclerView.Adapter<ApprovedCandidateViewHolder> {
    private final Context context;
    private final List<ApprovedCandidate> approvedCandidates;

    public ApprovedCandidateAdapter(Context context, List<ApprovedCandidate> approvedCandidates) {
        this.context = context;
        this.approvedCandidates = approvedCandidates;
    }


    @NonNull
    @Override
    public ApprovedCandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.approved_candidate_items, parent, false);
        return new ApprovedCandidateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedCandidateViewHolder holder, int position) {
        ApprovedCandidate candidate = approvedCandidates.get(position);
        holder.bind(candidate);

    }

    @Override
    public int getItemCount() {
        return approvedCandidates.size();
    }
}
