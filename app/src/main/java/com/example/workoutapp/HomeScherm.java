package com.example.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScherm extends AppCompatActivity {

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_scherm);
        setListenerBtnWorkout(findViewById(R.id.btnWorkOut));
        setListenerBtnWorkout2(findViewById(R.id.btnWorkOut2));
        setListenerBtnMijnAccount(findViewById(R.id.btnMijnAccount));
        setListenerBtnMijnAccount2(findViewById(R.id.btnMijnAccount2));
        setListenerbtnRangeLijst(findViewById(R.id.btnRangLijsten));
        setListenerbtnRangeLijst2(findViewById(R.id.btnRangLijsten2));


    }
    //A method to set a click listener to button workout
    private void setListenerBtnWorkout(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScherm.this,WorkoutActivity.class));
            }
        });
    }
    //A method to set a click listener to button workout2
    private void setListenerBtnWorkout2(Button btn)
    {
        btn.setText("WORK-OUTS");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScherm.this,WorkoutActivity.class));
            }
        });

    }
    //A method to set a click listener to button mijn account
    private void setListenerBtnMijnAccount(Button btn)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScherm.this, Settings.class));
            }
        });
    }
    //A method to set a click listener to button mijn account2
    private void setListenerBtnMijnAccount2(Button btn)
    {
        btn.setText("MIJN ACCOUNT");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScherm.this, Settings.class));
            }
        });
    }
    //A method to set a click listener to button ranglijsten
    private void setListenerbtnRangeLijst(Button btn)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScherm.this,RankingsActivity.class));
            }
        });
    }
    //A method to set a click listener to button ranglijsten2
    private void setListenerbtnRangeLijst2(Button btn)
    {
        btn.setText("RANGLIJSTEN");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScherm.this,RankingsActivity.class));
            }
        });}

}