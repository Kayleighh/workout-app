package com.example.workoutapp;

import java.util.ArrayList;

public class ProfileList {
    public static ArrayList<Profile> profiles = new ArrayList<>();

    public void add(Profile profile){
        if (!profiles.contains(profile)){
            profiles.add(profile);
        }
    }

    public int size(){
        int size = profiles.size();
        return size;
    }
}