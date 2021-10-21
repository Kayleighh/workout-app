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

public class MyAccountActivity extends AppCompatActivity {

    private EditText edtAge;
    Button save;
    ArrayList<String> times = new ArrayList<>();

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
                for(Spinner s : makeSpinnersList())
                {
                    times.add(String.valueOf(s.getSelectedItem()));
                }
                next();
            }
        });
    }


    // TODO: Werkt alleen op het moment dat de Button wordt ingedrukt (dus nog niet wanneer onClick uitgevoerd wordt);



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

        ArrayList<String> spinners = new ArrayList<>();
        spinners.add(monday);
        spinners.add(tuesday);
        spinners.add(wednesday);
        spinners.add(thursday);
        spinners.add(friday);

        addProfile(age, level, spinners);
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }


    public JSONObject addProfile(String age, String lvl, ArrayList time) {
        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArrayTime = new JSONArray();
        try {
//            for(String time1 : times)
//            {
//                time.add(time1);
//            }
//            for(Object time2 : time)
//            {
//                jsonArrayTime.put(time.indexOf(time2));
//            }
            getProfilesFromJSON();
            String firstname = getProfilesFromJSON().get(0).toString();
            String lastname = getProfilesFromJSON().get(1).toString();
            String function = getProfilesFromJSON().get(2).toString();
            String number = getProfilesFromJSON().get(3).toString();
            String username = getProfilesFromJSON().get(7).toString();
            String password = getProfilesFromJSON().get(8).toString();
            String image = getProfilesFromJSON().get(9).toString();
            jsonObject.put("Firstname", firstname);
            jsonObject.put("Lastname", lastname);
            jsonObject.put("Function", function);
            jsonObject.put("Number", number);
            jsonObject.put("Username", username);
            jsonObject.put("Password", password);
            jsonObject.put("Age", age);
            jsonObject.put("Level", lvl);
            jsonObject.put("Time", time);
            jsonObject.put("Image", image);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String input = jsonObject.toString();
        toJSON(input);

        return jsonObject;

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

            String age = jsonObject.get("Age").toString();
            String lvl = jsonObject.get("Level").toString();
            String time = jsonObject.get("Time").toString();
            String username = jsonObject.get("Username").toString();
            String password = jsonObject.get("Password").toString();
            String name = jsonObject.get("Firstname").toString();
            String lastname = jsonObject.getString("Lastname");
            String function = jsonObject.get("Function").toString();
            String number = jsonObject.getString("Number");
            String image = jsonObject.getString("Image");
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
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return profiles;
    }
}