package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NumbersStep extends AppCompatActivity {

    String id,number,Score;

    public NumbersStep() {
    }

    public NumbersStep(String id, String level, String score) {
        this.id = id;
        Score = score;
    }

    public NumbersStep(String id, String level) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}