package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity {

    private View btnStartWorkout;
    private View finishWorkout;
    private View checkMark;
    private int currentExerciseIndex = -2;
    private ArrayList<Integer> arrayListVideos = new ArrayList<>();
    private ArrayList<Integer> arrayListThumbnails = new ArrayList<>();
    private boolean shouldExecuteOnResume;
    static WorkoutActivity activityA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        addVideosToList();
        addThumbnailsToList();
        findViews();
        shouldExecuteOnResume = false;
        activityA = this;

        btnStartWorkout.setOnClickListener(view -> onResume());

        finishWorkout.setEnabled(false);
        finishWorkout.setOnClickListener(view -> finishWorkout());
    }

    public static WorkoutActivity getInstance(){
        return activityA;
    }

    @Override
    protected void onResume() {
        currentExerciseIndex++;
        System.out.println("exercise: " + currentExerciseIndex);

        if (shouldExecuteOnResume){
            try {
                if (currentExerciseIndex == arrayListVideos.size()){
                    finishWorkout.setEnabled(true);
                    finishWorkout.setBackground(ContextCompat.getDrawable(this, R.drawable.finish));
                    checkMark.setVisibility(View.VISIBLE);
                }
                else {
                    String videopath = "android.resource://com.example.workoutapp/" + arrayListVideos.get(currentExerciseIndex);
                    int thumbnail = arrayListThumbnails.get(currentExerciseIndex);

                    Intent ExerciseIntent = new Intent(this, ExerciseActivity.class);
                    ExerciseIntent.putExtra("key1", videopath);
                    ExerciseIntent.putExtra("key2", currentExerciseIndex);
                    ExerciseIntent.putExtra("key3", arrayListVideos);
                    ExerciseIntent.putExtra("key4", shouldExecuteOnResume);
                    ExerciseIntent.putExtra("key5", thumbnail);
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

    public void addThumbnailsToList(){
        arrayListThumbnails.add(R.drawable.squat);
        arrayListThumbnails.add(R.drawable.pushup);
    }
}