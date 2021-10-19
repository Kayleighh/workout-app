package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Settings extends AppCompatActivity {
    Switch hr;
    Switch nieuweOefening;
    Switch stappen;
    Switch reminder;
    JSONArray profile;
    String valueLevel;
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
        setContentView(R.layout.activity_settings);
        profile = getProfilesFromJSON();
        makeTextViewsList();
        for (Spinner spinner : makeSpinnersList()) {
            makeSpinnerDropdownItem(spinner);
        }

        hr = findViewById(R.id.switch1);
        nieuweOefening =findViewById(R.id.switch2);
        stappen = findViewById(R.id.switch3);
        reminder = findViewById(R.id.switch4);
        try {
            //Get the values for the switches from the json
            String valueHr = profile.get(10).toString();
            String valueOefening = profile.get(11).toString();
            String valueStappen = profile.get(12).toString();
            String valueReminder = profile.get(13).toString();

            //Set the switches according to the values. String needs to be parsed to boolean.
            hr.setChecked(Boolean.parseBoolean(valueHr));
            nieuweOefening.setChecked(Boolean.parseBoolean(valueOefening));
            stappen.setChecked(Boolean.parseBoolean(valueStappen));
            reminder.setChecked(Boolean.parseBoolean(valueReminder));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Switch case for level, based on the value in the json file. Button with said value will turn blue.

        try {
           valueLevel = profile.get(5).toString();
            switch (valueLevel){
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
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onBackPressed() {
        //When the back button is pressed, add the profile (save the changes) and start the right intent.
        super.onBackPressed();
        addProfile();
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }

    public void addProfile(){
        JSONObject jsonObject = new JSONObject();

        String level;

        RadioGroup group = findViewById(R.id.radioGroup);
        int selectedId = group.getCheckedRadioButtonId();
        if(selectedId == -1){
            level = valueLevel;
        }else{
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

        //Add values to arraylist
        ArrayList<String> spinners = new ArrayList<>();
        spinners.add(monday);
        spinners.add(tuesday);
        spinners.add(wednesday);
        spinners.add(thursday);
        spinners.add(friday);


        //Get value from switches

        Boolean checkHr = hr.isChecked();
        Boolean checkOefening = nieuweOefening.isChecked();
        Boolean checkStappen = stappen.isChecked();
        Boolean checkReminder = reminder.isChecked();

        String valueHr = checkHr.toString();
        String valueOefening = checkOefening.toString();
        String valueStappen = checkStappen.toString();
        String valueReminder = checkReminder.toString();


        try {
            //Get the values of the things that arent being edited in this screen
            String age = profile.get(4).toString();
            String username = profile.get(7).toString();
            String password = profile.get(8).toString();
            String firstname = profile.get(0).toString();
            String lastname = profile.get(1).toString();
            String function = profile.get(2).toString();
            String number = profile.get(3).toString();
            String image = profile.get(9).toString();

            //Add everything to the jsonObject
            jsonObject.put("Firstname", firstname);
            jsonObject.put("Lastname", lastname);
            jsonObject.put("Function", function);
            jsonObject.put("Number", number);
            jsonObject.put("Username", username);
            jsonObject.put("Password", password);
            jsonObject.put("Age", age);
            jsonObject.put("Level", level);
            jsonObject.put("Time", spinners);
            jsonObject.put("Image", image);
            jsonObject.put("HR",valueHr);
            jsonObject.put("Nieuwe oefening",valueOefening);
            jsonObject.put("Behaalde stappen",valueStappen);
            jsonObject.put("Reminder",valueReminder);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String input = jsonObject.toString();
        //Write the jsonObject to the json file
        toJSON(input);

    }


    public boolean toJSON(String input) {
        String filename = "test.json";
        Boolean check;
        try {
            File file = new File(this.getFilesDir() + filename);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(input);
            bufferedWriter.close();
            check = true;
            return check;
        } catch (IOException e) {
            e.printStackTrace();
            check = false;
            return check;
        }
    }

    public JSONArray getProfilesFromJSON() {
        JSONArray profiles = new JSONArray();
        String filename = "test.json";
        File file = new File(this.getFilesDir() + filename);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String response = stringBuilder.toString();
            JSONObject jsonObject = null;


            jsonObject = new JSONObject(response);

            String age = jsonObject.getString("Age");
            String lvl = jsonObject.getString("Level");
            String time = jsonObject.getString("Time");
            String username = jsonObject.getString("Username");
            String password = jsonObject.getString("Password");
            String name = jsonObject.getString("Firstname");
            String lastname = jsonObject.getString("Lastname");
            String function = jsonObject.getString("Function");
            String number = jsonObject.getString("Number");
            String image = jsonObject.getString("Image");
            String hr = jsonObject.getString("HR");
            String oefening = jsonObject.getString("Nieuwe oefening");
            String stappen = jsonObject.getString("Behaalde stappen");
            String reminder = jsonObject.getString("Reminder");

            profiles.put(name);
            profiles.put(lastname);
            profiles.put(function);
            profiles.put(number);
            profiles.put(age);
            profiles.put(lvl);
            profiles.put(time);
            profiles.put(username);
            profiles.put(password);
            profiles.put(image);
            profiles.put(hr);
            profiles.put(oefening);
            profiles.put(stappen);
            profiles.put(reminder);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return profiles;
    }
}