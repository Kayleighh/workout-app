package com.example.workoutapp;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TrainingJSON {
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

    public ArrayList<Training> getTraining()
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
                Training training = new Training();
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
                    training.getAmountOfRepsPerSet().clear();
                    JSONObject jsonObject1 = jsonArray2.getJSONObject(y);
                    training.addAmountsOfRepsPerSet((int)jsonObject1.get("set1"));
                    training.addAmountsOfRepsPerSet((int)jsonObject1.get("set2"));
                    training.addAmountsOfRepsPerSet((int)jsonObject1.get("set3"));

                }

                training.setAmountOfRest((Double) object.get("amountOfRest"));

                trainings.add(training);
//
            }

        }

    }}
