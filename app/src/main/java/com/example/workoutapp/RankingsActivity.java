package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;

public class RankingsActivity extends AppCompatActivity {

    private RadioButton button1;
    private RadioButton button2;
    private ListView listView;

    Profile p1 = new Profile("John", "Smith", "Marketing");
    Profile p2 = new Profile("Steve", "Johnson", "Management");
    Profile p3 = new Profile("Stacy", "Ashleys", "Klantenservice");
    Profile p4 = new Profile("Matt", "Lanning", "Verkoop");

    ProfileList profiles = new ProfileList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);

        button1 = findViewById(R.id.radioButton1);
        button2 = findViewById(R.id.radioButton2);
        listView = findViewById(R.id.listView);
    }

    public void onRadioButtonClicked(View view){
        switch (view.getId()){
            case R.id.radioButton1:
                // voeg alle profiles in profileList toe aan de listView
                profiles.add(p1);
                profiles.add(p2);
                profiles.add(p3);
                profiles.add(p4);

                button1.setTextColor(Color.WHITE);
                button2.setTextColor(Color.BLACK);
                break;
            case R.id.radioButton2:
                // voeg alle profiles in profileList die van dezelfde afdeling zijn als jij toe aan de listView
                for (int i = 0; i < profiles.size(); i++){
                    ProfileList.profiles.remove(i);
                }

                button1.setTextColor(Color.BLACK);
                button2.setTextColor(Color.WHITE);
                break;
        }
        PersonListAdapter adapter = new PersonListAdapter(this, R.layout.adapter_view_layout, ProfileList.profiles);
        listView.setAdapter(adapter);
    }
}