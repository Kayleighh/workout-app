package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class feature_Validatie_Scherm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_validatie_scherm);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Bedrijfs_Afdelingen, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    public void printData(View view) {
        System.out.println(findViewById(R.id.editTextTextPersonName));
        System.out.println(findViewById(R.id.editTextTextPersonName2));
        System.out.println(findViewById(R.id.editTextNumber));
}
class SpinnerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    }
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



}