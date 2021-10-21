package com.example.workoutapp;

public class Profile {

    private String name;
    private String surname;
    private int points;

    public Profile(String name, String surname, int points) {
        this.name = name;
        this.surname = surname;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}