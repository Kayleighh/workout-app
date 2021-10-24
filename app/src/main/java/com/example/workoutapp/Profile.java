package com.example.workoutapp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;

public class Profile {
    String firstname;
    String lastname;
    String age;
    String number;
    String department;
    String username;
    String password;
    String level;
    HashMap<String,String > notifications;
    HashMap<String, String> times;
    String image;
    int points;

    Profile(){

    }
    public Profile(String name, String surname, String department, int points) {
        this.firstname = name;
        this.lastname = surname;

    }

    public Profile(String name, String surname, int points){
        this.firstname = name;
        this.lastname = surname;
        this.points = points;
    }
    public Profile(String firstname,String lastname,String department,String number,String username,String password,String age,String level,HashMap<String,String>times,String image, HashMap<String,String> notifications){
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.number = number;
        this.department = department;
        this.username = username;
        this.password = password;
        this.level = level;
        this.notifications = notifications;
        this.times = times;
        this.image = image;
        this.department = department;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String function) {
        this.department = function;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String,String> getNotifications() {
        return notifications;
    }

    public void setNotifications(HashMap<String,String> notifications) {
        this.notifications = notifications;
    }

    public HashMap<String, String> getTimes() {
        return times;
    }

    public void setTimes(HashMap<String, String> times) {
        this.times = times;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public String toString()
    {
        String result = "User " +number+ " " + firstname + " " + lastname + "age " + age + " Department " + function + " Username "+ username +" Password " + password + " Level " + level + " Notifications " + notifications + " Times " +times + " Image " + image + " Points "+ points;
        return result;
    }

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
}


