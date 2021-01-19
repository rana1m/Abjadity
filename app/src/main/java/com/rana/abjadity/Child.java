package com.rana.abjadity;

public class Child {

    private String id;
    private String type;
    private String character;
    private String name;
    private String email;
    private String password;
    private String level;
    private String score;
    private String alarm;

    public Child(String id, String type, String character, String name, String email, String password, String level, String score, String alarm) {
        this.id = id;
        this.type = type;
        this.character = character;
        this.name = name;
        this.email = email;
        this.password = password;
        this.level = level;
        this.score = score;
        this.alarm = alarm;
    }

    public Child(String type, String character, String name, String email, String password, String level, String score, String alarm) {
        this.type = type;
        this.character = character;
        this.name = name;
        this.email = email;
        this.password = password;
        this.level = level;
        this.score = score;
        this.alarm = alarm;
    }

    public Child() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
}
