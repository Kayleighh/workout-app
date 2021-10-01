package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button toRankings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        toRankings = findViewById(R.id.toRankings);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        toRankings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRankings();
            }
        });
    }

    public void next(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void toRankings(){
        Intent intent = new Intent(this, RankingsActivity.class);
        startActivity(intent);
    }
}