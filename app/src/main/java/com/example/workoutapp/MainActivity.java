package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TrainingJSON trainingJSON = new TrainingJSON();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("HERE WE START");
        try {
            System.out.println("HERE WE END");
           trainingJSON.getJSONObject(getApplicationContext());
        }catch(IOException | JSONException e) {
            System.out.println("NIZAAAAAAAAAAAAAAAR" );
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
    }

    public void next(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}