package com.example.workoutapp;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;

public class TrainingJSON {
    private Training training = new Training();
    private ArrayList<Training> trainings = new ArrayList<>();
    public String loadJSONFromAssets(Context context,String fileName)throws IOException
    {
            String s;
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            s = new String(buffer,"UTF-8");

            return s;



    }

    private ArrayList<Training> getTraining()
    {
        return trainings;
    }

    public void getJSONObject(Context context) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(loadJSONFromAssets(context,"jsonfile.json"));
        JSONArray jsonArray = jsonObject.getJSONArray("workouts");
        for(int i = 0 ; i<jsonArray.length();i++)
        {
            JSONObject obj = jsonArray.getJSONObject(i);
            JSONArray jsonArray1 = obj.getJSONArray("exercises");
            for(int x = 0 ; x<jsonArray1.length();x++)
            {
                JSONObject object = jsonArray1.getJSONObject(x);
                training.setExerciseName((String) object.get("exercise"));
                training.setExerciseDescription((String) object.get("exerciseDescription"));
                training.setExerciseTip((String) object.get("exerciseTip"));
                training.setRepsDescription((String) object.get("repsDescription"));
                training.setAmountOfExercise((int) object.get("amountOfExercise"));
                training.setVideoId((String) object.get("videoId"));
                training.setSets((int) object.get("sets"));
                JSONArray jsonArray2 = ((JSONObject)object.get("amountOfReps")).getJSONArray("amountOfRepsPerSet");
                for(int y = 0 ;y<jsonArray2.length();y++)
                {
                    JSONObject jsonObject1 = (JSONObject) jsonArray2.getJSONObject(y);
                    training.addAmountsOfRepsPerSet((int)jsonObject1.get("set1"));
                    training.addAmountsOfRepsPerSet((int)jsonObject1.get("set2"));
                    training.addAmountsOfRepsPerSet((int)jsonObject1.get("set3"));

                }

                training.setAmountOfRest((Double) object.get("amountOfRest"));

                trainings.add(training);

            }
        }

    }}

//    public void parseExerciseObject(JSONArray exercises) throws IOException, ParseException,FileNotFoundException
//    {
//        System.out.println("Test1");
//        try {
//            for(int i = 0 ; i<exercises.length();i++)
//            {
//                System.out.println("Test2");
//                JSONObject object = exercises.getJSONObject(i);
//                String exerciseName =(String) object.get("exercise");
//                String exerciseDescription= (String) object.get("exerciseDescription");
//                String exerciseTip = (String) object.get("exerciseTip");
//                String repsDescription = (String) object.get("repsDescription");
//                String amountOfExercise = (String) object.get("amountOfExercise");
//                String videoId = (String) object.get("videoId");
//                String sets = (String) object.get("sets");
//                JSONObject amountOfReps = (JSONObject) object.get("amountOfReps");
//                JSONArray amountOfRepsPerSet = (JSONArray) amountOfReps.get("amountOfRepsPerSet");
//                System.out.println("Test3");
//                for(int x = 0 ; x<amountOfRepsPerSet.length();x++)
//                {
//                    System.out.println("Test4");
//                    JSONObject obj = (JSONObject) amountOfRepsPerSet.get(x);
//                    String set1 = (String) obj.get("set1");
//                    String set2 = (String) obj.get("set2");
//                    String set3 = (String) obj.get("set3");
//                }
//                String amountOfRest = (String) object.get("amountOfRest");
//                System.out.println("Test5");
//
//
//
//            }
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//
//}

//    public void readJSON() throws IOException, ParseException {
//        System.out.println("TEEEEEEEEEEEEEEEEEEEEEEEEEEESSSSSSSSSSSSSSTTTTTTTTTT");
//        JSONParser jsonParser = new JSONParser();
//        InputStream inputStream = getAssets()
//        try (FileReader reader = new FileReader(getAss))
//        {
//            JSONObject obj = (JSONObject) jsonParser.parse(reader);
//            JSONArray exercisesList = (JSONArray) obj.get("workouts") ;
//            parseExerciseObject(exercisesList);
//            //Iterator<String> iterator = exercisesList.iterator();
//            //while(iterator.hasNext()) {
//               // System.out.println(iterator.next());
//            //}
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }
