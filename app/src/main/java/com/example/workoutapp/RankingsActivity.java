package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Collections;

public class RankingsActivity extends AppCompatActivity {

    private RadioButton button1;
    private RadioButton button2;
    ProfileList profiles;
    ArrayList<Profile> profileArrayList = new ArrayList<>();
    ArrayList<Profile> profileArrayListTwo = new ArrayList<>();
    ArrayList<Profile> departmentArrayList = new ArrayList<>();
    Profile profile1 = new Profile("Kees", "Bergen", "verkoop", 13);
    Profile profile2 = new Profile("Sarah", "Viersten", "verkoop", 2);
    Profile profile3 = new Profile("Manuello", "Castro", "management",6);
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);

        sortByColleagues();

        listView = findViewById(R.id.listView);

        button1 = findViewById(R.id.radioButton1);
        button2 = findViewById(R.id.radioButton2);

    }

    public void onRadioButtonClicked(View view){
        switch (view.getId()){
            case R.id.radioButton1:
                button1.setTextColor(Color.WHITE);
                button2.setTextColor(Color.BLACK);
                sortByColleagues();
                break;
            case R.id.radioButton2:
                button1.setTextColor(Color.BLACK);
                button2.setTextColor(Color.WHITE);
                sortByDepartments();
                break;
        }
    }

    public void sortByColleagues() {
        emptyArrayList();
        fillArrayList();
        Collections.sort(profileArrayList, Profile.PointComparator);

        PersonListAdapter listAdapter = new PersonListAdapter(this, R.layout.adapter_view_layout, profileArrayList);
        ((ListView) (findViewById(R.id.listView))).setAdapter(listAdapter);
    }

    public void sortByDepartments() {
        emptyArrayList();
        fillArrayList();
        for (Profile p : profileArrayList) {
            if (p.getDepartment().equals("verkoop")) {
                departmentArrayList.add(p);
            }
        }

        Collections.sort(departmentArrayList, Profile.PointComparator);

//        Collections.sort(profileArrayList, Profile.DepartmentComparator);
//        System.out.println(Profile.DepartmentComparator);

        PersonListAdapter listAdapterTwo = new PersonListAdapter(this, R.layout.adapter_view_layout, departmentArrayList);
        ((ListView) (findViewById(R.id.listView))).setAdapter(listAdapterTwo);
    }

    public void fillArrayList() {
        profileArrayList.add(profile1);
        profileArrayList.add(profile2);
        profileArrayList.add(profile3);
    }

    public void emptyArrayList() {
        profileArrayList.clear();
        departmentArrayList.clear();
    }

//    public void onWorkoutFinishedButtonClicked(View view) {
//        if (finishWorkout.setEnabled(true)) {
//
//        }
//    }
}