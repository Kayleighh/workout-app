package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class RankingsActivity extends AppCompatActivity {

    private RadioButton button1;
    private RadioButton button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);

        button1 = findViewById(R.id.radioButton1);
        button2 = findViewById(R.id.radioButton2);
    }

    public void onRadioButtonClicked(View view){
        switch (view.getId()){
            case R.id.radioButton1:
                button1.setTextColor(Color.WHITE);
                button2.setTextColor(Color.BLACK);
                break;
            case R.id.radioButton2:
                button1.setTextColor(Color.BLACK);
                button2.setTextColor(Color.WHITE);
                break;
        }
    }

    public void onWorkoutFinishedButtonClicked(View view) {
        if (finishWorkout.setEnabled(true)) {

        }
    }
}