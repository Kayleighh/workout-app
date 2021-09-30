package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WorkoutActivity extends AppCompatActivity {

    private Button btnStartWorkout;
    public static Button btnFinishWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        readJSON();

        btnStartWorkout = findViewById(R.id.btnStartWorkout);
        btnStartWorkout.setBackgroundColor(Color.argb(223, 96, 55, 1));
        btnStartWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWorkout();
            }
        });

        btnFinishWorkout = findViewById(R.id.btnFinishWorkout);

        btnFinishWorkout.setVisibility(View.GONE);
        btnFinishWorkout.setBackgroundColor(Color.argb(223, 96, 55, 1));
        btnFinishWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishWorkout();
            }
        });
    }

    public void startWorkout(){
        Intent switchActivityIntent = new Intent(this, Exercise1Activity.class);
        startActivity(switchActivityIntent);
    }

    public void finishWorkout(){
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
        btnFinishWorkout.setVisibility(View.INVISIBLE);
    }

// *** dit werkt maar na terug keren naar deze activity vanuit mainActivity wordt de button ook geactiveerd waardoor die visible blijft ***
    @Override
    protected void onResume(){
        if (Exercise2Activity.showBtn){
            btnFinishWorkout.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    private void readJSON(){
        try {
            Resources res = getResources();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(res.openRawResource(R.raw.jsonfile)));
            StringBuilder responseBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = bufferedReader.readLine()) != null){
                responseBuilder.append(inputStr);
            }
            getProfilesFromJSON(new JSONObject(responseBuilder.toString()));

        }catch (Exception e){

        }
    }

    public void getProfilesFromJSON(JSONObject response){
        try{
            String exercise = response.get("exercise").toString();
            System.out.println(exercise);
        } catch(JSONException e){
            e.printStackTrace();
        }
    }
}