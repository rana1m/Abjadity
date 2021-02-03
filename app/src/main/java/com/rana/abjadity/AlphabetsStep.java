package com.rana.abjadity;

public class AlphabetsStep {

    String id,level,Score;

    public AlphabetsStep() {
    }

    public AlphabetsStep(String id, String level, String score) {
        this.id = id;
        this.level = level;
        Score = score;
    }

    public AlphabetsStep(String id, String level) {
        this.id = id;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}
