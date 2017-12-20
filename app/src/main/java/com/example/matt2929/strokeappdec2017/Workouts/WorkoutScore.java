package com.example.matt2929.strokeappdec2017.Workouts;

/**
 * Created by matt2929 on 12/19/17.
 */

public class WorkoutScore {
    String Name;
    float Score;

    public WorkoutScore(String Name, float Score) {
        this.Name = Name;
        this.Score = Score;
    }

    public String getName() {
        return Name;
    }

    public float getScore() {
        return Score;
    }
}
