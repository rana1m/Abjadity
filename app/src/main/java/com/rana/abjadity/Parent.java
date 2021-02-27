package com.rana.abjadity;

import java.util.ArrayList;

public class Parent {

    public String id;
    public String type;
    public ArrayList<Child> children;
    public String character;
    public String name;


    public Parent() {
    }


    public Parent(String id, String type, ArrayList<Child> children, String character, String name) {
        this.id = id;
        this.type = type;
        this.children = children;
        this.character = character;
        this.name = name;

    }

    public Parent(String type, ArrayList<Child> children, String character, String name) {
        this.type = type;
        this.children = children;
        this.character = character;
        this.name = name;

    }
    public Parent(String id,String type,String character, String name) {
        this.id = id;
        this.type = type;
        this.character = character;
        this.name = name;

    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setName(String name) {
        this.name = name;
    }



}
