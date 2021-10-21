package com.example.workoutapp;

public class Profile {

    private String name;
    private String surname;
    private String department;
    private int points;

    public Profile(String name, String surname, String department) {
        this.name = name;
        this.surname = surname;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
