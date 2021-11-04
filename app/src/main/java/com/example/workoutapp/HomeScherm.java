package com.example.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HomeScherm extends AppCompatActivity {

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_scherm);
        Button btnMain = findViewById(R.id.btnMain);
        Button btnMain2 = findViewById(R.id.btnMain2);
        ScrollView scrollView = findViewById(R.id.scrollView);
        Button btnMyAccount = findViewById(R.id.btnMyAccount);
        Button btnLogOut = findViewById(R.id.btnLogOut);
        scrollView.setVisibility(View.INVISIBLE);
        try {
            setListenerBtnMain(btnMain,scrollView,btnMain2);
            setListenerBtnMain2(btnMain2,scrollView,btnMain);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setListenerBtnMyAccount(btnMyAccount);
        setListenerBtnLogOut(btnLogOut);

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getFirstName() throws  IOException
    {
        String filename = "test.json";
        Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(this.getFilesDir() + filename));
            Profile profile = gson.fromJson(reader, Profile.class);
            // close reader
            reader.close();
            String s = profile.getFirstname();

            return s;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setListenerBtnMain(Button btn , ScrollView scrollView,Button btn2) throws IOException {
        btn.setText(getFirstName());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.VISIBLE);

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setListenerBtnMain2(Button btn , ScrollView scrollView,Button btn2) throws IOException {
        btn.setText(getFirstName());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.setVisibility(View.INVISIBLE);
                btn.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setListenerBtnMyAccount(Button btn)
    {
        btn.setText("Mijn Account");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScherm.this,Settings.class));
            }
        });
    }

    private void setListenerBtnLogOut(Button btn)
    {
        btn.setText("Uitloggen");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScherm.this,MainActivity.class));
            }
        });
    }

}