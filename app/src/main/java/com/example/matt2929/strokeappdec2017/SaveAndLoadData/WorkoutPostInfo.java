package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import java.util.Calendar;

/**
 * Created by matt2929 on 12/20/17.
 */

public class WorkoutPostInfo {
    Calendar CurrentDate;
    String Comments;
    String WorkoutName;
    Integer Reps;
    Integer Score;
    Long Duration;
    int hand;//0-left 1-right

    private WorkoutPostInfo(Calendar date, String workoutName, int hand, Integer reps, Integer score, Long duration, String comments) {
    }

    public Calendar getCurrentDate() {
        return CurrentDate;
    }

    public int getHand() {
        return hand;
    }

    public Integer getReps() {
        return Reps;
    }

    public Integer getScore() {
        return Score;
    }

    public Long getDuration() {
        return Duration;
    }

    public String getComments() {
        return Comments;
    }

    public String getWorkoutName() {
        return WorkoutName;
    }

    public String WorkoutToString() {
        return null;
        //TODO:w->s
    }

    public WorkoutPostInfo stringToWorkout() {
        return null;
        //TODO:s->W
    }
}

