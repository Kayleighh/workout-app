package com.example.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Settings extends AppCompatActivity {
    Switch hr;
    Switch nieuweOefening;
    Switch stappen;
    Switch reminder;
    ArrayList getProfile;
    String valueLevel;
    Profile profile;
    HashMap<String,String> test;
    HashMap<String, String> times;

    private ArrayList<Spinner> makeSpinnersList() {
        Spinner spnMonday = findViewById(R.id.spnMonday);
        Spinner spnTuesday = findViewById(R.id.spnTuesday);
        Spinner spnWednesday = findViewById(R.id.spnWednesday);
        Spinner spnThursday = findViewById(R.id.spnThursday);
        Spinner spnFriday = findViewById(R.id.spnFriday);


        ArrayList<Spinner> spinners = new ArrayList<>();
        spinners.add(spnMonday);
        spinners.add(spnTuesday);
        spinners.add(spnWednesday);
        spinners.add(spnThursday);
        spinners.add(spnFriday);

        return spinners;
    }

    //Een methode die voegt alle textViews (van mijn account scherm ) aan een lijst toe
    //returnt lijst met textviews.
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<TextView> makeTextViewsList() {
        TextView selectedMonday = findViewById(R.id.selectedMonday);
        TextView selectedTuesday = findViewById(R.id.selectedTuesday);
        TextView selectedWednesday = findViewById(R.id.selectedWednsday);
        TextView selectedThursday = findViewById(R.id.selectedThrusday);
        TextView selectedFriday = findViewById(R.id.selectedFriday);

        ArrayList<TextView> textViews = new ArrayList<>();

        getTimes();
        selectedMonday.setText(getTimes().get("Monday"));
        selectedTuesday.setText(getTimes().get("Tuesday"));
        selectedWednesday.setText(getTimes().get("Wednesday"));
        selectedThursday.setText(getTimes().get("Thursday"));
        selectedFriday.setText(getTimes().get("Friday"));
        textViews.add(selectedMonday);
        textViews.add(selectedTuesday);
        textViews.add(selectedWednesday);
        textViews.add(selectedThursday);
        textViews.add(selectedFriday);
        return textViews;
    }

    // een methode dat voegt tijden aan timelist
    //wordt later gebruikt voor het maken van een spinner droptown item.
    //return timelist
    private ArrayList<String> makeTimeList() {
        ArrayList<String> timeList = new ArrayList<>();
        timeList.add("09:00");
        timeList.add("10:00");
        timeList.add("11:00");
        timeList.add("12:00");
        timeList.add("13:00");
        timeList.add("14:00");
        timeList.add("15:00");
        timeList.add("16:00");
        timeList.add("17:00");

        return timeList;
    }

    //een methode die wordt gebruikt voor het maken van een dropdown voor de spinners en dan kan de eindgebruiker een tijd kunnen selecteren

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void makeSpinnerDropdownItem(Spinner spinner) {
        ArrayAdapter<String> timeListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, makeTimeList());
        spinner.setAdapter(timeListAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                makeTextViewsList().get(makeSpinnersList().indexOf(spinner)).setText(String.valueOf(spinner.getItemAtPosition(i)));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getProfile = getProfilesFromJSON();
        profile = new Profile();
        getNotifications();

        getTimes();
        System.out.println(getTimes().get("Monday"));
        makeTextViewsList();

        for (Spinner spinner : makeSpinnersList()) {
            makeSpinnerDropdownItem(spinner);
        }

        hr = findViewById(R.id.switch1);
        nieuweOefening = findViewById(R.id.switch2);
        stappen = findViewById(R.id.switch3);
        reminder = findViewById(R.id.switch4);







      try {
            //Get the values for the switches from the json
            String valueHr = test.get("HR");
            String valueOefening = test.get("Nieuwe oefening");
            String valueStappen = test.get("Behaalde stappen");
            String valueReminder = test.get("Reminder");

            //Set the switches according to the values. String needs to be parsed to boolean.
            hr.setChecked(Boolean.parseBoolean(valueHr));
            nieuweOefening.setChecked(Boolean.parseBoolean(valueOefening));
            stappen.setChecked(Boolean.parseBoolean(valueStappen));
            reminder.setChecked(Boolean.parseBoolean(valueReminder));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Switch case for level, based on the value in the json file. Button with said value will turn blue.

        try {
            valueLevel = getProfile.get(8).toString();
            System.out.println("test " + valueLevel);
            switch (valueLevel) {
                case "Beginner":
                    Button beginnerBtn = findViewById(R.id.radioButton);
                    beginnerBtn.setBackgroundColor(Color.parseColor("#2D78AA"));
                    break;
                case "Gevorderd":
                    Button gevorderdBtn = findViewById(R.id.radioButton2);
                    gevorderdBtn.setBackgroundColor(Color.parseColor("#2D78AA"));
                    break;
                case "Master":
                    Button masterBtn = findViewById(R.id.radioButton3);
                    masterBtn.setBackgroundColor(Color.parseColor("#2D78AA"));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {
        //When the back button is pressed, add the profile (save the changes) and start the right intent.
        super.onBackPressed();
        addProfile();
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addProfile() {
        String level;

        RadioGroup group = findViewById(R.id.radioGroup);
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId == -1) {
            level = valueLevel;
        } else {
            RadioButton radioButton = findViewById(selectedId);
            level = radioButton.getText().toString();
        }
        //Get all values from spinners
        Spinner spinMonday = findViewById(R.id.spnMonday);
        Spinner spinTuesday = findViewById(R.id.spnTuesday);
        Spinner spinWednesday = findViewById(R.id.spnWednesday);
        Spinner spinThursday = findViewById(R.id.spnThursday);
        Spinner spinFriday = findViewById(R.id.spnFriday);
        String monday = spinMonday.getSelectedItem().toString();
        String tuesday = spinTuesday.getSelectedItem().toString();
        String wednesday = spinWednesday.getSelectedItem().toString();
        String thursday = spinThursday.getSelectedItem().toString();
        String friday = spinFriday.getSelectedItem().toString();


        HashMap<String,String> time = new HashMap<>();

        time.put("Monday",monday);
        time.put("Tuesday",tuesday);
        time.put("Wednesday",wednesday);
        time.put("Thursday",thursday);
        time.put("Friday",friday);

        //Get value from switches

        Boolean checkHr = hr.isChecked();
        Boolean checkOefening = nieuweOefening.isChecked();
        Boolean checkStappen = stappen.isChecked();
        Boolean checkReminder = reminder.isChecked();

        String valueHr = checkHr.toString();
        String valueOefening = checkOefening.toString();
        String valueStappen = checkStappen.toString();
        String valueReminder = checkReminder.toString();

        HashMap<String, String> notifications = new HashMap<String, String>();
        notifications.put("HR", valueHr);
        notifications.put("Nieuwe oefening", valueOefening);
        notifications.put("Behaalde stappen", valueStappen);
        notifications.put("Reminder", valueReminder);

        //Get the values of the things that arent being edited in this screen
        getProfilesFromJSON();
        String number = getProfilesFromJSON().get(0);
        String firstname = getProfilesFromJSON().get(1);
        String lastname = getProfilesFromJSON().get(2);
        String function = getProfilesFromJSON().get(3);
        String age = getProfilesFromJSON().get(4);
        String username = getProfilesFromJSON().get(5);
        String password = getProfilesFromJSON().get(6);
        String image = getProfilesFromJSON().get(7);


        Profile profile = new Profile();
        profile.setFirstname(firstname);
        profile.setLastname(lastname);
        profile.setDepartment(function);
        profile.setNumber(number);
        profile.setUsername(username);
        profile.setPassword(password);
        profile.setAge(age);
        profile.setLevel(level);
        profile.setTimes(time);
        profile.setNotifications(notifications);
        profile.setImage(image);
        Gson gson = new Gson();
        String filename = "test.json";
        try {
            FileWriter writer = new FileWriter(this.getFilesDir() + filename);
            gson.toJson(profile, writer);
            writer.flush(); //flush data to file   <---
            writer.close(); //close write
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> getProfilesFromJSON() {
        ArrayList<String> profiles = new ArrayList<>();

        String filename = "test.json";
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(this.getFilesDir() + filename));
            Profile profile = gson.fromJson(reader, Profile.class);
            // close reader
            reader.close();

            profile.setNumber(profile.getNumber());
            profile.setFirstname(profile.getFirstname());
            profile.setLastname(profile.getLastname());
            profile.setDepartment(profile.getDepartment());
            profile.setAge(profile.getAge());
            profile.setLevel(profile.getLevel());
            profile.setTimes(profile.getTimes());
            profile.setUsername(profile.getUsername());
            profile.setPassword(profile.getPassword());
            profile.setImage(profile.getImage());


            profiles.add(profile.getNumber());
            profiles.add(profile.getFirstname());
            profiles.add(profile.getLastname());
            profiles.add(profile.getDepartment());
            profiles.add(profile.getAge());
            profiles.add(profile.getUsername());
            profiles.add(profile.getPassword());
            profiles.add(profile.getImage());
            profiles.add(profile.getLevel());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return profiles;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public HashMap<String, String> getNotifications() {
        //ArrayList<HashMap> notifications = new ArrayList<>();
        //HashMap<String,String> test = new HashMap<>();
        String filename = "test.json";
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(this.getFilesDir() + filename));
            Profile profile = gson.fromJson(reader, Profile.class);
            profile.setNotifications(profile.getNotifications());
            test = profile.getNotifications();
            System.out.println(test);
            // close reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

            //notifications.add(profile.getNotifications());
        return test;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public HashMap<String, String> getTimes()
    {
        String filename = "test.json";
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(this.getFilesDir() + filename));
            Profile profile = gson.fromJson(reader, Profile.class);
            profile.setTimes(profile.getTimes());
            times = profile.getTimes();
            // close reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return times;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> setSpinnerTimes(){
        getTimes();
        TextView selectedMonday = findViewById(R.id.selectedMonday);
        TextView selectedTuesday = findViewById(R.id.selectedTuesday);
        TextView selectedWednesday = findViewById(R.id.selectedWednsday);
        TextView selectedThursday = findViewById(R.id.selectedThrusday);
        TextView selectedFriday = findViewById(R.id.selectedFriday);

        selectedMonday.setText(getTimes().get("Monday"));
        selectedTuesday.setText(getTimes().get("Tuesday"));
        selectedWednesday.setText(getTimes().get("Wednesday"));
        selectedThursday.setText(getTimes().get("Thursday"));
        selectedFriday.setText(getTimes().get("Friday"));

        ArrayList<String> timeList = new ArrayList<>();
        timeList.add(getTimes().get("Monday"));
        timeList.add(getTimes().get("Tuesday"));
        timeList.add(getTimes().get("Wednesday"));
        timeList.add(getTimes().get("Thursday"));
        timeList.add(getTimes().get("Friday"));

        return timeList;
    }
}