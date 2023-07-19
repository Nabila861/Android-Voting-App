package com.example.votingsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;

import android.os.Bundle;

public class DashboardActivity extends AppCompatActivity {

    private BarChart barChart;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click event
                onBackPressed();
            }
        });

        barChart = findViewById(R.id.bar_chart);
        databaseHelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("VotingSession", MODE_PRIVATE);
        boolean isVotingSessionActive = sharedPreferences.getBoolean("isVotingSessionActive", true);
        boolean isVotingSessionStarted = sharedPreferences.getBoolean("isVotingSessionStarted", false);

        TextView votingProcessText = findViewById(R.id.voting_process_text);

        if (!isVotingSessionActive) {
            votingProcessText.setText("Voting Process: Closed");
        } else if (isVotingSessionStarted) {
            votingProcessText.setText("Voting Process: Underway");
        } else {
            votingProcessText.setText("Voting Process: Not Started");
        }

        displayBarChart();
        displayWinners();
    }

    private String getShortFacultyName(String fullFacultyName) {
        switch (fullFacultyName) {
            case "Faculty of Advanced Physics":
                return " (AP)";
            case "Faculty of Advanced Mathematics":
                return " (AM)";
            case "Faculty of Artificial Intelligence":
                return " (AI)";
            case "Faculty of Traditional Chinese Medicine":
                return " (TCM)";
            // Add more cases here for other faculties
            default:
                return fullFacultyName; // If no short form is found, return the full name
        }
    }
    private void displayBarChart() {
        HashMap<String, HashMap<String, Integer>> candidateVotes = databaseHelper.getCandidateVotes();

        List<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        int i = 0;
        for (Map.Entry<String, HashMap<String, Integer>> entry : candidateVotes.entrySet()) {
            String name = entry.getKey();
            for (Map.Entry<String, Integer> innerEntry : entry.getValue().entrySet()) {
                String course = innerEntry.getKey();
                int voteCount = innerEntry.getValue();

                entries.add(new BarEntry(i, voteCount));
                labels.add(name + getShortFacultyName(course));
                i++;
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Candidates");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);

        // Set labels for the x and y axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setLabelRotationAngle(90); // To prevent overlapping of x-axis labels

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setGranularity(1f); // set the granularity to 1

        barChart.getAxisRight().setEnabled(false); // To hide the right y-axis

// Set the legend
        barChart.getLegend().setEnabled(true);

        barChart.animateY(2000);
    }

    private void displayWinners() {
        SharedPreferences sharedPreferences = getSharedPreferences("VotingSession", MODE_PRIVATE);
        boolean isVotingSessionActive = sharedPreferences.getBoolean("isVotingSessionActive", true);

        TextView resultText = findViewById(R.id.result_text);

        if (isVotingSessionActive) {
            resultText.setText("Results will be published after voting is finished.");
        } else {
            HashMap<String, String> winners = databaseHelper.getWinners();

            StringBuilder winnersText = new StringBuilder("Winners:\n");

            for (Map.Entry<String, String> entry : winners.entrySet()) {
                winnersText.append(getShortFacultyName(entry.getKey())).append(": ").append(entry.getValue()).append("\n");
            }

            resultText.setText(winnersText.toString());
        }
    }



}
