package com.example.workoutapp;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

public class MyAccountActivity extends AppCompatActivity {

    private EditText edtAge;
    Button save;

    // een methode die voegt alle spinners (van mijn account scherm) aan een lijst toe
    // wordt gebruikt voor het fixen van de bug dat alleen de eerste spinner werkt.
    //return een lijst met 5 spinners
    private ArrayList<Spinner> makeSpinnersList() {
        //spnTime = findViewById(R.id.spnMonday);
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
    private ArrayList<TextView> makeTextViewsList() {
        TextView selectedMonday = findViewById(R.id.selectedMonday);
        TextView selectedTuesday = findViewById(R.id.selectedTuesday);
        TextView selectedWednesday = findViewById(R.id.selectedWednsday);
        TextView selectedThursday = findViewById(R.id.selectedThrusday);
        TextView selectedFriday = findViewById(R.id.selectedFriday);

        ArrayList<TextView> textViews = new ArrayList<>();
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

    private void makeSpinnerDropdownItem(Spinner spinner) {
        ArrayAdapter<String> timeListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, makeTimeList());

        spinner.setAdapter(timeListAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(makeSpinnersList().indexOf(spinner));
                makeTextViewsList().get(makeSpinnersList().indexOf(spinner)).setText(String.valueOf(spinner.getItemAtPosition(i)));


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account2);
        makeTextViewsList();
        for (Spinner spinner : makeSpinnersList()) {
            makeSpinnerDropdownItem(spinner);
        }
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
    }


//Method to get all the new input the user has chosen. Add the profile to the json file and then continue to the next screen.
    public void next() {
        String level;
        EditText editAge = findViewById(R.id.edtAge);
        String age = editAge.getText().toString();
        RadioGroup group = findViewById(R.id.radioGroup);
        int selectedId = group.getCheckedRadioButtonId();
        if(selectedId == -1){
            level = "beginner";
        }else{
            RadioButton radioButton = findViewById(selectedId);
            level = radioButton.getText().toString();
        }

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
        addProfile(age, level, time);
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }


    public void addProfile(String age, String lvl, HashMap time) {
        HashMap<String, String> notifications = new HashMap<String, String>();
        //Set all notifications to true. user can change these in the settings screen.
        notifications.put("HR","true");
        notifications.put("Nieuwe oefening","true");
        notifications.put("Behaalde stappen","true");
        notifications.put("Reminder","true");

        //Get all extras from the previous screens.
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String firstname = extras.getString("Firstname");
            String lastname = extras.getString("Lastname");
            String function = extras.getString("Function");
            String number = extras.getString("Number");
            String username = extras.getString("Username");
            String password = extras.getString("Password");
            String image = extras.getString("Image");

    //Create a new instance of the Profile class and set all the methods with the data from the user.
            Profile profile = new Profile();
            profile.setFirstname(firstname);
            profile.setLastname(lastname);
            profile.setDepartment(function);
            profile.setNumber(number);
            profile.setUsername(username);
            profile.setPassword(password);
            profile.setAge(age);
            profile.setLevel(lvl);
            profile.setTimes(time);
            profile.setNotifications(notifications);
            profile.setImage(image);
            //Write to the json file. The file can be found in device file explores -> data -> data -> com.example.workoutapp -> filetest.json
            Gson gson = new Gson();
            String filename = "test.json";
            try {
                FileWriter writer = new FileWriter(this.getFilesDir() + filename);
                gson.toJson(profile,writer);
                writer.flush(); //flush data to file   <---
                writer.close(); //close write
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

}