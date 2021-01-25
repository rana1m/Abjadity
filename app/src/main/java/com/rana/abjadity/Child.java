package com.rana.abjadity;

import android.widget.EditText;

public class Child {

    private String id;
    private String type;
    private String character;
    private String name;
    private String age;
    private String email;
    private String password;
    private String level;
    private String score;
    private String alarm;
    private String parentId;



    public Child(String parentId,String id,String age, String character, String name, String level, String score, String alarm) {
        this.parentId = parentId;
        this.id = id;
        this.age = age;
        this.character = character;
        this.name = name;
        this.level = level;
        this.score = score;
        this.alarm = alarm;
    }

    public Child(String name,String age,String level) {
        this.name = name;
        this.level = level;
        this.age = age;
    }


    public Child() {

    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
