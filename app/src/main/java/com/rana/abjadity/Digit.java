package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Digit{
    String id, name, chant;


    public Digit() {
    }
    public Digit(String id, String name, String chant) {
        this.id = id;
        this.name = name;
        this.chant = chant;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  String getName() {
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






}