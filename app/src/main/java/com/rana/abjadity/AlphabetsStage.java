package com.rana.abjadity;

import java.util.ArrayList;

public class AlphabetsStage {

    String id;
    public ArrayList<AlphabetsStep> steps;
    int TotalScore;
    Letter letter;

    AlphabetsStage(){
    }

    public AlphabetsStage(String id, ArrayList<AlphabetsStep> steps, int totalScore, Letter letter) {
        this.id = id;
        this.steps = steps;
        TotalScore = totalScore;
        this.letter = letter;
    }

    public AlphabetsStage(String id, int totalScore, Letter letter) {
        this.id = id;
        TotalScore = totalScore;
        this.letter = letter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<AlphabetsStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<AlphabetsStep> steps) {
        this.steps = steps;
    }

    public int getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(int totalScore) {
        TotalScore = totalScore;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }
}
