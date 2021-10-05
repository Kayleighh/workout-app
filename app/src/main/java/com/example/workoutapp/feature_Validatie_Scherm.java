package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;

public class feature_Validatie_Scherm extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_validatie_scherm);


        Spinner spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Bedrijfs_Afdelingen, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void printData(View view) {
        EditText edtName = findViewById(R.id.editTextTextPersonName);
        EditText edtName2 = findViewById(R.id.editTextTextPersonName2);
        EditText edtNumber = findViewById(R.id.editTextNumber);

        String Name1 = edtName.getText().toString();
        String Name2 = edtName2.getText().toString();
        String Number = edtNumber.getText().toString();
        Spinner mySpinner = findViewById(R.id.spinner);
        String text = mySpinner.getSelectedItem().toString();
        System.out.println(Name1 + " " + Name2 + " " + Number);
        addProfile(Name1, Name2, Number, text);
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    class SpinnerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public JSONObject addProfile(String voornaam, String achternaam, String number, String function) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Firstname", voornaam);
            jsonObject.put("Lastname", achternaam);
            jsonObject.put("Number", number);
            jsonObject.put("Function", function);
            jsonObject.put("Username", "");
            jsonObject.put("Password", "");
            jsonObject.put("Age", 0);
            jsonObject.put("Level", "Beginner");
            jsonObject.put("Time", "09:00");

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

}