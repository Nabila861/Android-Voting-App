package com.example.votingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String databaseName = "Signup.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Signup.db", null, 12); // Update the version number to 7
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table IF NOT EXISTS students(email TEXT primary key UNIQUE, password Text, course Text, name Text)");
        MyDatabase.execSQL("create Table IF NOT EXISTS candidates (email TEXT primary key, password Text,course Text, name Text,achievements Text, manifesto Text)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS admins(email TEXT PRIMARY KEY, password TEXT)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS approved_candidates (name Text PRIMARY KEY,course Text, achievements Text, manifesto Text)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS CandidateVote (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, course TEXT, studentEmail TEXT, voteCount INTEGER DEFAULT 0, UNIQUE(name, course, studentEmail))");

        insertAdminData(MyDatabase); // Insert admin data
        insertDummyCandidates(MyDatabase);
        insertDummyStudents(MyDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        MyDatabase.execSQL("drop Table if exists students");
        MyDatabase.execSQL("drop Table if exists candidates"); // Add this line to drop the 'candidates' table
        MyDatabase.execSQL("drop Table if exists approved_candidates");
        MyDatabase.execSQL("drop Table if exists CandidateVote");
        onCreate(MyDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void insertAdminData(SQLiteDatabase MyDatabase) {
        // Define the admin's email and password
        String adminEmail = "admin@gmail.com";
        String adminPassword = "123";

        // Insert the admin's data into the admins table
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", adminEmail);
        contentValues.put("password", adminPassword);

        long result = MyDatabase.insert("admins", null, contentValues);

        if (result == -1) {
            Log.d("DatabaseHelper", "Failed to insert admin data");
        } else {
            Log.d("DatabaseHelper", "Admin data inserted successfully");
        }
    }

    public boolean insertStudentData(String email, String password, String name, String course) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        contentValues.put("course", course);
        long result = MyDatabase.insert("students", null, contentValues);

        return result != -1;
    }

    public Boolean checkCandidateEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from candidates where email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }

    public boolean insertCandidateData(String email, String password, String name, String course, String achievements, String manifesto) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("course",course);
        contentValues.put("name", name);
        contentValues.put("achievements", achievements);
        contentValues.put("manifesto", manifesto);
        long result = MyDatabase.insert("candidates", null, contentValues);

        return result != -1;
    }


    private void insertDummyCandidates(SQLiteDatabase MyDatabase) {
        // Insert dummy candidates data
        insertCandidateData(MyDatabase, "john@example.com", "password123", "John Doe", "Faculty of Advanced Mathematics", "Awarded Best Programmer", "Passionate about software development");
        insertCandidateData(MyDatabase, "jane@example.com", "password456", "Jane Smith", "Faculty of Advanced Physics", "Published research papers on renewable energy", "Advocate for sustainable energy solutions");
        insertCandidateData(MyDatabase, "alex@example.com", "password789", "Alex Johnson", "Faculty of Advanced Physics", "Led a team to build a solar-powered car", "Dedicated to advancing clean transportation");
        insertCandidateData(MyDatabase, "emma@example.com", "password321", "Emma Wilson", "Faculty of Advanced Mathematics", "Launched a successful startup company", "Entrepreneurial mindset and strategic thinking");
        insertCandidateData(MyDatabase, "michael@example.com", "password654", "Michael Brown", "Faculty of Advanced Physics", "Published a book on cognitive behavioral therapy", "Passionate about mental health advocacy");
        insertCandidateData(MyDatabase, "sophia@example.com", "password987", "Sophia Davis", "Faculty of Advanced Mathematics", "Exhibited artwork in prestigious galleries", "Expressive and innovative artistic style");
        insertCandidateData(MyDatabase, "liam@example.com", "password012", "Liam Taylor", "Faculty of Artificial Intelligence", "Managed large-scale infrastructure projects", "Committed to sustainable and efficient construction");
        insertCandidateData(MyDatabase, "olivia@example.com", "password345", "Olivia Wilson", "Faculty of Artificial Intelligence", "Campaign manager for a successful mayoral candidate", "Advocate for social justice and equal representation");
        insertCandidateData(MyDatabase, "noah@example.com", "password678", "Noah Martinez", "Faculty of Artificial Intelligence", "Published research on genetic engineering", "Passionate about advancing medical science");
        insertCandidateData(MyDatabase, "isabella@example.com", "password901", "Isabella Thompson", "Faculty of Traditional Chinese Medicine", "Led community initiatives for environmental conservation", "Driven to protect the planet and promote sustainability");
    }


    private void insertCandidateData(SQLiteDatabase MyDatabase, String email, String password, String name, String course, String achievements, String manifesto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("course", course);
        contentValues.put("name", name);
        contentValues.put("achievements", achievements);
        contentValues.put("manifesto", manifesto);
        long result = MyDatabase.insert("candidates", null, contentValues);

        if (result == -1) {
            Log.d("DatabaseHelper", "Failed to insert candidate data");
        } else {
            Log.d("DatabaseHelper", "Candidate data inserted successfully");
        }
    }

    private void insertDummyStudents(SQLiteDatabase MyDatabase) {
        // Insert dummy students data
        insertStudentData(MyDatabase, "johnstudent@example.com", "123", "John Student", "Faculty of Advanced Mathematics");
        insertStudentData(MyDatabase, "janestudent@example.com", "456", "Jane Student", "Faculty of Advanced Physics");
        insertStudentData(MyDatabase, "alexstudent@example.com", "789", "Alex Student", "Faculty of Advanced Physics");
        insertStudentData(MyDatabase, "emmastudent@example.com", "321", "Emma Student", "Faculty of Advanced Mathematics");
        insertStudentData(MyDatabase, "michaelstudent@example.com", "654", "Michael Student", "Faculty of Advanced Physics");
        insertStudentData(MyDatabase, "sophiastudent@example.com", "987", "Sophia Student", "Faculty of Advanced Mathematics");
        insertStudentData(MyDatabase, "liamstudent@example.com", "012", "Liam Student", "Faculty of Artificial Intelligence");
        insertStudentData(MyDatabase, "oliviastudent@example.com", "345", "Olivia Student", "Faculty of Artificial Intelligence");
        insertStudentData(MyDatabase, "noahstudent@example.com", "678", "Noah Student", "Faculty of Artificial Intelligence");
        insertStudentData(MyDatabase, "isabellastudent@example.com", "901", "Isabella Student", "Faculty of Traditional Chinese Medicine");
    }

    private void insertStudentData(SQLiteDatabase MyDatabase, String email, String password, String name, String course) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        contentValues.put("course", course);
        long result = MyDatabase.insert("students", null, contentValues);

        if (result == -1) {
            Log.d("DatabaseHelper", "Failed to insert student data");
        } else {
            Log.d("DatabaseHelper", "Student data inserted successfully");
        }
    }

    public boolean insertApprovedCandidateData(Candidate candidate) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course", candidate.getCourse());
        contentValues.put("name", candidate.getName());
        contentValues.put("achievements", candidate.getAchievements());
        contentValues.put("manifesto", candidate.getManifesto());
        long result = MyDatabase.insert("approved_candidates", null, contentValues);

        return result != -1;
    }


    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from students where email = ?", new String[]{email});

        return cursor.getCount() > 0;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from students where email = ? and password =?", new String[]{email, password});

        return cursor.getCount() > 0;
    }

    public Boolean checkAdminEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM admins WHERE email = ? AND password = ?", new String[]{email, password});
        boolean isAdmin = cursor.getCount() > 0;
        cursor.close();
        return isAdmin;
    }

    public String getAdminEmail() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT email FROM admins LIMIT 1";  // Assuming there's only one admin
        Cursor cursor = db.rawQuery(query, null);

        String email = "";
        if(cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndex("email"));

        }
        cursor.close();

        return email;
    }

    public List<Candidate> getAllCandidates() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM candidates";
        Cursor cursor = db.rawQuery(query, null);

        List<Candidate> candidates = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String course = cursor.getString(cursor.getColumnIndex("course"));
                String achievements = cursor.getString(cursor.getColumnIndex("achievements"));
                String manifesto = cursor.getString(cursor.getColumnIndex("manifesto"));

                Candidate candidate = new Candidate(name, course, achievements, manifesto);
                candidates.add(candidate);

            } while(cursor.moveToNext());
        }
        cursor.close();

        return candidates;
    }



    public List<ApprovedCandidate> getAllApprovedCandidates() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM approved_candidates";
        Cursor cursor = db.rawQuery(query, null);

        List<ApprovedCandidate> approvedCandidates = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String course = cursor.getString(cursor.getColumnIndex("course"));
                String achievements = cursor.getString(cursor.getColumnIndex("achievements"));
                String manifesto = cursor.getString(cursor.getColumnIndex("manifesto"));

                ApprovedCandidate candidate = new ApprovedCandidate(name, course, achievements, manifesto);
                approvedCandidates.add(candidate);

            } while (cursor.moveToNext());
        }
        cursor.close();

        System.out.println("Fetched " + approvedCandidates.size() + " approved candidates from the database"); // Console log for data fetch

        return approvedCandidates;
    }

    public List<StudentCandidateList> getApprovedCandidatesByCourse(String studentCourse) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM approved_candidates WHERE course = ?";
        Cursor cursor = db.rawQuery(query, new String[]{studentCourse});

        List<StudentCandidateList> studentCandidateList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String course = cursor.getString(cursor.getColumnIndex("course"));
                String achievements = cursor.getString(cursor.getColumnIndex("achievements"));
                String manifesto = cursor.getString(cursor.getColumnIndex("manifesto"));

                StudentCandidateList studentcandidate = new StudentCandidateList(name, course, achievements, manifesto);
                studentCandidateList.add(studentcandidate);

            } while (cursor.moveToNext());
        }
        cursor.close();

        System.out.println("Fetched " + studentCandidateList.size() + " approved candidates from the database"); // Console log for data fetch

        return studentCandidateList;
    }


    public String getStudentCourse(String studentEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT course FROM students WHERE email = ?", new String[]{studentEmail});
        if (cursor.moveToFirst()) {
            String course = cursor.getString(cursor.getColumnIndex("course"));
            cursor.close();
            return course;
        } else {
            cursor.close();
            return null; // or throw an exception
        }
    }

    public boolean insertVoteRecord(String studentEmail, String candidateName, String course) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", candidateName);
        contentValues.put("course", course);
        contentValues.put("studentEmail", studentEmail);
        long result = MyDatabase.insert("CandidateVote", null, contentValues);

        if (result == -1) {
            Log.d("DatabaseHelper", "Failed to insert vote record");
            return false;
        } else {
            Log.d("DatabaseHelper", "Vote record inserted successfully");
            return true;
        }
    }



    public boolean checkCandidateVote(String candidateName, String studentEmail) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM CandidateVote WHERE name = ? AND EXISTS (SELECT 1 FROM students WHERE email = ?)", new String[]{candidateName, studentEmail});

        boolean alreadyVoted = cursor.getCount() > 0;
        cursor.close();
        return alreadyVoted;
    }


    public void incrementVoteCount(String candidateName) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        MyDatabase.execSQL("UPDATE CandidateVote SET voteCount = voteCount + 1 WHERE name = ?", new String[]{candidateName});
    }

    public boolean hasVoted(String studentEmail) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM CandidateVote WHERE studentEmail = ?", new String[]{studentEmail});

        boolean hasVoted = cursor.getCount() > 0;
        cursor.close();
        return hasVoted;
    }


//Chart logic
public HashMap<String, HashMap<String, Integer>> getCandidateVotes() {
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT name, course, COUNT(*) as voteCount FROM CandidateVote GROUP BY name, course";
    Cursor cursor = db.rawQuery(query, null);

    HashMap<String, HashMap<String, Integer>> candidateVotes = new HashMap<>();
    if (cursor.moveToFirst()) {
        do {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String course = cursor.getString(cursor.getColumnIndex("course"));
            int voteCount = cursor.getInt(cursor.getColumnIndex("voteCount"));

            HashMap<String, Integer> courseAndVoteCount = new HashMap<>();
            courseAndVoteCount.put(course, voteCount);

            candidateVotes.put(name, courseAndVoteCount);

        } while (cursor.moveToNext());
    }
    cursor.close();

    return candidateVotes;
}


    public int getApprovedCandidatesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM approved_candidates", null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } else {
            cursor.close();
            return 0;
        }
    }

    public HashMap<String, String> getWinners() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT name, course, COUNT(*) as voteCount FROM CandidateVote GROUP BY name, course ORDER BY course, voteCount DESC";
        Cursor cursor = db.rawQuery(query, null);

        HashMap<String, String> winners = new HashMap<>();
        if (cursor.moveToFirst()) {
            String currentFaculty = "";
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String course = cursor.getString(cursor.getColumnIndex("course"));

                // If this is a new faculty, then this candidate is the winner for this faculty
                if (!course.equals(currentFaculty)) {
                    winners.put(course, name);
                    currentFaculty = course;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();

        return winners;
    }









}
