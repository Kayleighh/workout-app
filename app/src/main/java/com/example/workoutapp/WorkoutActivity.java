package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity {

    private View btnStartWorkout;
    private View finishWorkout;
    private View checkMark;
    private int currentExerciseIndex = -2;
    private ArrayList<Integer> arrayListVideos = new ArrayList<>();
    private boolean shouldExecuteOnResume;
    static WorkoutActivity activityA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        addVideosToList();
        findViews();
        shouldExecuteOnResume = false;
        activityA = this;

//        btnStartWorkout.setBackgroundColor(Color.argb(223, 96, 55, 1));
        btnStartWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResume();
            }
        });

        finishWorkout.setEnabled(false);
        finishWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishWorkout();
            }
        });
    }

    public static WorkoutActivity getInstance(){
        return activityA;
    }

    @Override
    protected void onResume() {
        currentExerciseIndex++;

        if (shouldExecuteOnResume){
            try {
                if (currentExerciseIndex == arrayListVideos.size()){
                    finishWorkout.setEnabled(true);
                    finishWorkout.setBackground(ContextCompat.getDrawable(this, R.drawable.finish));
                    checkMark.setVisibility(View.VISIBLE);
                }
                else {
                    String videopath = "android.resource://com.example.workoutapp/" + arrayListVideos.get(currentExerciseIndex);
                    Intent ExerciseIntent = new Intent(this, ExerciseActivity.class);
                    ExerciseIntent.putExtra("key1", videopath);
                    ExerciseIntent.putExtra("key2", currentExerciseIndex);
                    ExerciseIntent.putExtra("key3", arrayListVideos);
                    ExerciseIntent.putExtra("key4", shouldExecuteOnResume);
                    startActivity(ExerciseIntent);

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            shouldExecuteOnResume = true;
        }
        super.onResume();
    }

    public void finishWorkout(){
        finish();
        finishWorkout.setEnabled(false);
    }

    public void findViews(){
        btnStartWorkout = findViewById(R.id.btnStartWorkout);
        finishWorkout = findViewById(R.id.btnFinishWorkout);
        checkMark = findViewById(R.id.check_mark_);

    }



    public void addVideosToList(){
        arrayListVideos.add(R.raw.squat);
        arrayListVideos.add(R.raw.pushup);
    }
}