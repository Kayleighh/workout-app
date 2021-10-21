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
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class WorkoutActivity extends AppCompatActivity {

    private View btnStartWorkout;
    private View finishWorkout;
    private View checkMark;
    private int currentExerciseIndex = -2;
    private ArrayList<Integer> arrayListVideos = new ArrayList<>();
    private boolean shouldExecuteOnResume;
    static WorkoutActivity activityA;
    private ArrayList<String> testTimes = new ArrayList<>();
    private ArrayList<String> testDays = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        addVideosToList();
        findViews();
        setCurrentMonth(findViewById(R.id.txtMaand));
        fillTimes(testTimes);
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

    public void setFirstRectengles(View view)
    {
        DateFormat dfgmt = new java.text.SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        dfgmt.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));
        String nowTime = dfgmt.format(new Date());
        String inputDate = nowTime.substring(0,9);
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        Date dt1 = null;
        try {
            dt1 = format1.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("EEEE");
        String finalDay = format2.format(dt1);
        //view.setText
    }

    public void findViews(){
        btnStartWorkout = findViewById(R.id.btnStartWorkout);
        finishWorkout = findViewById(R.id.btnFinishWorkout);
        checkMark = findViewById(R.id.check_mark_);

    }

    private void fillTimes (ArrayList<String> list)
    {
        list.add("09:00");
        list.add("11:00");
        list.add("15:00");
        list.add("09:00");
        list.add("10:00");


    }

    private void setCurrentMonth(TextView textView)
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH) + 1;

        String month = "";
        if(thisMonth ==1){month = "Januari";}
        if(thisMonth ==2){month = "Februari";}
        if(thisMonth ==3){month = "Maart";}
        if(thisMonth ==4){month = "April";}
        if(thisMonth ==5){month = "Mei";}
        if(thisMonth ==6){month = "Juni";}
        if(thisMonth ==7){month = "Juli";}
        if(thisMonth ==8){month = "Augustus";}
        if(thisMonth ==9){month = "September";}
        if(thisMonth ==10){month = "Oktober";}
        if(thisMonth ==11){month = "November";}
        if(thisMonth ==12){month = "December";}

        textView.setText(month);

    }



    public void addVideosToList(){
        arrayListVideos.add(R.raw.squat);
        arrayListVideos.add(R.raw.pushup);
    }
}