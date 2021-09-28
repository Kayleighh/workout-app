package com.example.workoutapp;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;

public class MyAccountActivity extends AppCompatActivity {

    private EditText edtAge;

    // een methode die voegt alle spinners (van mijn account scherm) aan een lijst toe
    // wordt gebruikt voor het fixen van de bug dat alleen de eerste spinner werkt.
    //return een lijst met 5 spinners
    private ArrayList<Spinner> makeSpinnersList()
    {
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
    private ArrayList<TextView> makeTextViewsList()
    {
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
    private ArrayList<String> makeTimeList()
    {
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

    private void makeSpinnerDropdownItem(Spinner spinner)
    {
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
        readFromJSON();
        for(Spinner spinner : makeSpinnersList())
        {
            makeSpinnerDropdownItem(spinner);
        }

    }


    // TODO: Werkt alleen op het moment dat de Button wordt ingedrukt (dus nog niet wanneer onClick uitgevoerd wordt);

    public void onClick(View view) {
//        String age = edtAge.getText().toString();
//        System.out.println(age);
        next();
    }
    public void next(){
        EditText editAge = findViewById(R.id.edtAge);
        String age = editAge.getText().toString();
        RadioGroup group = findViewById(R.id.radioGroup);
        int selectedId = group.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        String level = radioButton.getText().toString();
        Spinner mySpinner = (Spinner) findViewById(R.id.spnMonday);
        String text = mySpinner.getSelectedItem().toString();

        addProfile(age,level,text);
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }


    public JSONObject addProfile(String age, String lvl,String time )
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Age", age);
            jsonObject.put("Level", lvl);
            jsonObject.put("Time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject input = jsonObject;
        toJSON(input);

        return jsonObject;

    }
    public boolean toJSON(JSONObject input)
    {
        String filename = "test.json";
        Boolean check;
        try {
            File file = new File(this.getFilesDir()+filename);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            JSONArray profileArray = new JSONArray();
            profileArray.put(input);
            for(int i = 0; i <= profileArray.length(); i++){
                try {
                    String test = profileArray.get(i).toString();
                    bufferedWriter.append(test).append("\n");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

           // bufferedWriter.write(input);
            bufferedWriter.close();
            check = true;
            return check;
        } catch (IOException e) {
            e.printStackTrace();
            check = false;
            return check;
        }
    }
    public void readFromJSON()
    {
        String filename = "test.json";
        Boolean check;
        try {
            File file = new File(this.getFilesDir()+filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null)
            {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String responce = stringBuilder.toString();
            getProfilesFromJSON(responce);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getProfilesFromJSON(String responce)
    {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(responce);

            String age = jsonObject.get("Age").toString();
            String lvl = jsonObject.get("Level").toString();
            String time = jsonObject.get("Time").toString();
            System.out.println(age + " " + lvl + " " + time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}