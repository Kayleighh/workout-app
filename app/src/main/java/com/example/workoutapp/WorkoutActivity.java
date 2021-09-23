package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorkoutActivity extends AppCompatActivity {

    private Button btnStartWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        btnStartWorkout = findViewById(R.id.btnStartWorkout);
        btnStartWorkout.setBackgroundColor(Color.argb(223, 96, 55, 1));
        btnStartWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWorkout();
            }
        });
    }

    public void startWorkout(){
        Intent switchActivityIntent = new Intent(this, WorkoutStartActivity.class);
        startActivity(switchActivityIntent);
    }
}