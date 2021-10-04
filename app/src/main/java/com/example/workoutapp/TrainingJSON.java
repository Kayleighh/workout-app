package com.example.workoutapp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class TrainingJSON {
    public void readJSON() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("jsonfile.json");)
        {
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray exercisesList = (JSONArray) obj.get("workouts") ;
            parseExerciseObject(exercisesList);
            //Iterator<String> iterator = exercisesList.iterator();
            //while(iterator.hasNext()) {
               // System.out.println(iterator.next());
            //}

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void parseExerciseObject(JSONArray exercises) throws IOException, ParseException,FileNotFoundException
    {
        System.out.println("Test1");
        try {
            for(int i = 0 ; i<exercises.length();i++)
            {
                System.out.println("Test2");
                JSONObject object = exercises.getJSONObject(i);
                String exerciseName =(String) object.get("exercise");
                String exerciseDescription= (String) object.get("exerciseDescription");
                String exerciseTip = (String) object.get("exerciseTip");
                String repsDescription = (String) object.get("repsDescription");
                String amountOfExercise = (String) object.get("amountOfExercise");
                String videoId = (String) object.get("videoId");
                String sets = (String) object.get("sets");
                JSONObject amountOfReps = (JSONObject) object.get("amountOfReps");
                JSONArray amountOfRepsPerSet = (JSONArray) amountOfReps.get("amountOfRepsPerSet");
                System.out.println("Test3");
                for(int x = 0 ; x<amountOfRepsPerSet.length();x++)
                {
                    System.out.println("Test4");
                    JSONObject obj = (JSONObject) amountOfRepsPerSet.get(x);
                    String set1 = (String) obj.get("set1");
                    String set2 = (String) obj.get("set2");
                    String set3 = (String) obj.get("set3");
                }
                String amountOfRest = (String) object.get("amountOfRest");
                System.out.println("Test5");



            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
