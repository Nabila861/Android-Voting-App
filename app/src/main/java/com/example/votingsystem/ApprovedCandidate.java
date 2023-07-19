package com.example.votingsystem;

public class ApprovedCandidate {
    private final String name;
    private final String course;
    private final String achievements;
    private final String manifesto;

    public ApprovedCandidate(String name, String course, String achievements, String manifesto) {
        this.name = name;
        this.course = course;
        this.achievements = achievements;
        this.manifesto = manifesto;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getAchievements() {
        return achievements;
    }

    public String getManifesto() {
        return manifesto;
    }
}
