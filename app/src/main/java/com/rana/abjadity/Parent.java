package com.rana.abjadity;

import java.util.ArrayList;

public class Parent {

    public String id;
    public String type;
    public ArrayList<Child> childs;
    public String character;
    public String name;
    public String email;
    public String password;

    public Parent() {
    }


    public Parent(String id, String type, ArrayList<Child> childs, String character, String name, String email, String password) {
        this.id = id;
        this.type = type;
        this.childs = childs;
        this.character = character;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Parent(String type, ArrayList<Child> childs, String character, String name, String email, String password) {
        this.type = type;
        this.childs = childs;
        this.character = character;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Child> getChilds() {
        return childs;
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setChilds(ArrayList<Child> childs) {
        this.childs = childs;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
