package com.example.workoutapp;

import java.util.ArrayList;
import java.util.HashMap;

public class Profile {
    String firstname;
    String lastname;
    String age;
    String number;
    String function;
    String username;
    String password;
    String level;
    HashMap<String,String > notifications;
    HashMap<String, String> times;
    String image;

    Profile(){

    }

    public Profile(String firstname,String lastname,String function,String number,String username,String password,String age,String level,HashMap<String,String>times,String image, HashMap<String,String> notifications){
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.number = number;
        this.function = function;
        this.username = username;
        this.password = password;
        this.level = level;
        this.notifications = notifications;
        this.times = times;
        this.image = image;
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

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
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
        String result = "User " +number+ " " + firstname + " " + lastname + "age " + age + " Function " + function + " Username "+ username +" Password " + password + " Level " + level + " Notifications " + notifications + " Times " +times + " Image " + image;
        return result;
    }


}
