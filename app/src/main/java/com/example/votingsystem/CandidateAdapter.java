package com.example.votingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateViewHolder> {
    private final Context context;
    private final List<Candidate> candidates;
    private final HashMap<String, Integer> selectedCounts;

    public CandidateAdapter(Context context, List<Candidate> candidates, HashMap<String, Integer> selectedCounts) {
        this.context = context;
        this.candidates = candidates;
        this.selectedCounts = selectedCounts;
    }

    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.candidate_items, parent, false);
        return new CandidateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateViewHolder holder, int position) {
        Candidate candidate = candidates.get(position);
        holder.bind(candidate);

        holder.btnReject.setOnClickListener(v -> {
            // Remove the candidate from the list
            candidates.remove(position);
            // Update the RecyclerView
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, candidates.size());
        });

        holder.btnSelect.setOnClickListener(v -> {
            String course = candidate.getCourse();
            if (selectedCounts.containsKey(course) && selectedCounts.get(course) >= 3) {
                Toast.makeText(context, "Cannot select more than 3 candidates from the same course.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Remove the candidate from the list
            candidates.remove(position);
            // Update the RecyclerView
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, candidates.size());

            // Increment the selected count for the course
            int count = selectedCounts.containsKey(course) ? selectedCounts.get(course) : 0;
            selectedCounts.put(course, count + 1);

            // Insert the selected candidate into the 'approved_candidates' table
            DatabaseHelper db = new DatabaseHelper(context);
            db.insertApprovedCandidateData(candidate);
        });
    }



    @Override
    public int getItemCount() {
        return candidates.size();
    }
}
