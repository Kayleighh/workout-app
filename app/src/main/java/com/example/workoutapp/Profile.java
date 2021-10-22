package com.example.workoutapp;

import java.util.Comparator;

public class Profile {

    private String name;
    private String surname;
    private String department;
    private int points;

    public Profile(String name, String surname, String department, int points) {
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

    public String getDepartment() { return department; }

    public void setDepartment(String department) { this.department = department; }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public static Comparator<Profile> PointComparator = new Comparator<Profile>() {
        public int compare(Profile profile1, Profile profile2) {
            int points1 = profile1.getPoints();
            int points2 = profile2.getPoints();

            return points2-points1;
        }
    };

    @Override
    public String toString() {
        return "[ name=" + name + ", surname=" + surname + ", department=" + department + ", points=" + points + "]";
    }
}