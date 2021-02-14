package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class NumbersStage extends AppCompatActivity {

    String id;
    public ArrayList<NumbersStep> steps;
    int TotalScore;
    Number number;

    NumbersStage(){
    }

    public NumbersStage(String id, ArrayList<NumbersStep> steps, int totalScore, Number number) {
        this.id = id;
        this.steps = steps;
        TotalScore = totalScore;
        this.number = number;
    }

    public NumbersStage(String id, int totalScore, Number number) {
        this.id = id;
        TotalScore = totalScore;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<NumbersStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<NumbersStep> steps) {
        this.steps = steps;
    }

    public int getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(int totalScore) {
        TotalScore = totalScore;
    }

    public Number getNumber() {
        return number;
    }

    public void setLetter(Number number) {
        this.number = number;
    }
}
