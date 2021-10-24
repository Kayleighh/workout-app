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

        String firstname = edtName.getText().toString();
        String lastname = edtName2.getText().toString();
        String number = edtNumber.getText().toString();
        Spinner mySpinner = findViewById(R.id.spinner);
        String function = mySpinner.getSelectedItem().toString();

        //addProfile(Name1, Name2, Number, function);
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("Firstname", firstname);
        intent.putExtra("Lastname", lastname);
        intent.putExtra("Number", number);
        intent.putExtra("Function",function);
        startActivity(intent);

    }

    class SpinnerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}