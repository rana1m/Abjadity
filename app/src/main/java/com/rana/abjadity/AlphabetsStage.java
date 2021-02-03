package com.rana.abjadity;

public class AlphabetsStage {

    String id;
    AlphabetsStep step;
    int TotalScore;
    Letter letter;

    AlphabetsStage(){

    }

    public AlphabetsStage(String id, AlphabetsStep step, int totalScore, Letter letter) {
        this.id = id;
        this.step = step;
        TotalScore = totalScore;
        this.letter = letter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AlphabetsStep getStep() {
        return step;
    }

    public void setStep(AlphabetsStep step) {
        this.step = step;
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
