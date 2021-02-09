package com.rana.abjadity;

public class AlphabetsStep {

    String id,letter,Score;

    public AlphabetsStep() {
    }

    public AlphabetsStep(String id, String level, String score) {
        this.id = id;
        Score = score;
    }

    public AlphabetsStep(String id, String level) {
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
