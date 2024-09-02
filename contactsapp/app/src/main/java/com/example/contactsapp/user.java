package com.example.contactsapp;

public class user {
    private  int id;
    private  String name;
    private  String Number;

    private String photoPath;


    public int getId() {
        return id;
    }

    public user(String name, String number , String photoPath) {
        this.name = name;
        Number = number;
        this.photoPath = photoPath;
    }

    public user(int id, String name, String number, String photoPath) {
        this.id = id;
        this.name = name;
        Number = number;
        this.photoPath = photoPath;
    }

    // Getters and setters for photoPath
    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setName(String name) {
        this.name = name;
    }



    public void setNumber(String number) {
        Number = number;
    }
    public String getNumber() {
        return Number;
    }
    public String getName() {
        return name;
    }

}
