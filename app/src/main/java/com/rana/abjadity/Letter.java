package com.rana.abjadity;

public class Letter {

    String id,name,chant,word1,word2,word3,word4,image1,image2,image3,image4,video_URL,voice_URL1,voice_URL2,voice_URL3,voice_URL4;

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
        this.image1 = l.image1;
        this.image2 = l.image2;
        this.image3 = l.image3;
        this.image4 = l.image4;
        this.video_URL = l.video_URL;
        this.voice_URL1 = l.voice_URL1;
        this.voice_URL2 = l.voice_URL2;
        this.voice_URL3 = l.voice_URL3;
        this.voice_URL4 = l.voice_URL4;
    }

    public Letter(String id, String name, String chant, String word1, String word2, String word3, String word4, String image1, String image2, String image3, String image4, String video_URL, String voice_URL1, String voice_URL2, String voice_URL3, String voice_URL4) {
        this.id = id;
        this.name = name;
        this.chant = chant;
        this.word1 = word1;
        this.word2 = word2;
        this.word3 = word3;
        this.word4 = word4;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.video_URL = video_URL;
        this.voice_URL1 = voice_URL1;
        this.voice_URL2 = voice_URL2;
        this.voice_URL3 = voice_URL3;
        this.voice_URL4 = voice_URL4;
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

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public void setVideo_URL(String video_URL) {
        this.video_URL = video_URL;
    }

    public String getVoice_URL1() {
        return voice_URL1;
    }

    public void setVoice_URL1(String voice_URL1) {
        this.voice_URL1 = voice_URL1;
    }

    public String getVoice_URL2() {
        return voice_URL2;
    }

    public void setVoice_URL2(String voice_URL2) {
        this.voice_URL2 = voice_URL2;
    }

    public String getVoice_URL3() {
        return voice_URL3;
    }

    public void setVoice_URL3(String voice_URL3) {
        this.voice_URL3 = voice_URL3;
    }

    public String getVoice_URL4() {
        return voice_URL4;
    }

    public void setVoice_URL4(String voice_URL4) {
        this.voice_URL4 = voice_URL4;
    }

}
