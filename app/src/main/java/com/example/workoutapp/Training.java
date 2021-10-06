package com.example.workoutapp;

import java.util.ArrayList;

public class Training {
    private String exerciseName;
    private String exerciseDescription;
    private String exerciseTip;
    private String repsDescription;
    private int amountOfExercise;
    private String videoId;
    private int sets;
    private ArrayList<Integer> amountOfRepsPerSet = new ArrayList<>();
    private double amountOfRest;

    public Training(){}
    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public String getExerciseTip() {
        return exerciseTip;
    }

    public void setExerciseTip(String exerciseTip)
    {
        this.exerciseTip=exerciseTip;
    }

    public String getRepsDescription(){
        return repsDescription;
    }

    public void setRepsDescription(String repsDescription)
    {
        this.repsDescription=repsDescription;
    }

    public int getAmountOfExercise()
    {
        return amountOfExercise;
    }

    public void setAmountOfExercise(int amountOfExercise)
    {
        this.amountOfExercise=amountOfExercise;

    }
    public String getVideoId()
    {
        return videoId;
    }

    public void setVideoId(String videoId)
    {
        this.videoId=videoId;
    }

    public int getSets()
    {
        return sets;
    }
    public void setSets(int sets)
    {
        this.sets=sets;
    }
    public ArrayList<Integer> getAmountOfRepsPerSet()
    {
        return amountOfRepsPerSet;
    }

    public void addAmountsOfRepsPerSet(int x )
    {
        getAmountOfRepsPerSet().add(x);
    }

    public void setAmountOfRepsPerSet(ArrayList<Integer> amountOfRepsPerSet){
        this.amountOfRepsPerSet=amountOfRepsPerSet;
    }

    public double getAmountOfRest()
    {
        return amountOfRest;
    }

    public void setAmountOfRest(double amountOfRest)
    {
        this.amountOfRest=amountOfRest;
    }
}

