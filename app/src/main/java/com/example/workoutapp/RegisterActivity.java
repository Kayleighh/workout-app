package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = findViewById(R.id.btnVolgende);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
    }

    public void next(){
        EditText gebruikersnaam = findViewById(R.id.edtGebruikersnaam);
        EditText pass1 = findViewById(R.id.edtWachtwoord);
        EditText pass2 = findViewById(R.id.edtWachtwoordHerhaal);
        String gebruiknm = gebruikersnaam.getText().toString();
        String password1 = pass1.getText().toString();
        String password2 = pass2.getText().toString();
        if(password2.equals(password1)){
            addProfile(gebruiknm,password1);
            Intent intent = new Intent(this, MyAccountActivity.class);
            startActivity(intent);
        }else{
            System.out.println("passwords dont match");
            System.out.println(password1 +" " + password2);

        }

    }
    public JSONObject addProfile(String gebruikersnaam, String wachtwoord)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Age",0);
            jsonObject.put("Level","Beginner");
            jsonObject.put("Time","09:00");
            jsonObject.put("Username", gebruikersnaam);
            jsonObject.put("Password", wachtwoord);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String input = jsonObject.toString();
        toJSON(input);

        return jsonObject;

    }
    public boolean toJSON(String input)
    {
        String filename = "test.json";
        Boolean check;
        try {
            File file = new File(this.getFilesDir()+filename);
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