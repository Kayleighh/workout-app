package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.Comparator;

public class RankingsActivity extends AppCompatActivity {

    private RadioButton button1;
    private RadioButton button2;
    ProfileList profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);

        button1 = findViewById(R.id.radioButton1);
        button2 = findViewById(R.id.radioButton2);

        Profile profile1 = new Profile("Kees", "Bergen", 0);
        Profile profile2 = new Profile("Sarah", "Viersten", 2);
        Profile profile3 = new Profile("Manuello", "Castro", 4);
        profiles = new ProfileList();
        profiles.add(profile1);
        profiles.add(profile2);
        profiles.add(profile3);

        ProfileList.profiles.sort(Comparator.comparing(Profile::getPoints));

        ListView listView = findViewById(R.id.listView);
        PersonListAdapter listAdapter = new PersonListAdapter(this, R.layout.adapter_view_layout, ProfileList.profiles);
        listView.setAdapter(listAdapter);
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

//    public void onWorkoutFinishedButtonClicked(View view) {
//        if (finishWorkout.setEnabled(true)) {
//
//        }
//    }
}