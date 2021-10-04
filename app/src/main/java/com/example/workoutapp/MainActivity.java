package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TrainingJSON trainingJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
                try {
                    trainingJSON.readJSON();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void next(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}