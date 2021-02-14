package com.rana.abjadity;

public class Letter {

    String id,name,chant,word1,word2,word3,word4,video_URL,voice_URL;

    public Letter() {

    }
    public Letter(Letter l) {
        this.id = l.id;
        this.name = l.name;
        this.chant = l.chant;
        this.word1 = l.word1;
        this.word2 = l.word2;
        this.word3 = l.word3;
        this.word4 = l.word4;
        this.video_URL = l.video_URL;
        this.voice_URL = l.voice_URL;
    }

    public Letter(String id, String name, String chant, String word1, String word2, String word3, String word4, String video_URL, String voice_URL) {
        this.id = id;
        this.name = name;
        this.chant = chant;
        this.word1 = word1;
        this.word2 = word2;
        this.word3 = word3;
        this.word4 = word4;
        this.video_URL = video_URL;
        this.voice_URL = voice_URL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChant() {
        return chant;
    }

    public void setChant(String chant) {
        this.chant = chant;
    }

    public String getWord1() {
        return word1;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    public String getWord3() {
        return word3;
    }

    public void setWord3(String word3) {
        this.word3 = word3;
    }

    public String getWord4() {
        return word4;
    }

    public void setWord4(String word4) {
        this.word4 = word4;
    }

    public String getVideo_URL() {
        return video_URL;
    }

    public void setVideo_URL(String video_URL) {
        this.video_URL = video_URL;
    }

    public String getVoice_URL() {
        return voice_URL;
    }

    public void setVoice_URL(String voice_URL) {
        this.voice_URL = voice_URL;
    }
}
