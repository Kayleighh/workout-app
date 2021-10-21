package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button register;
    private Button login;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        error = findViewById(R.id.errorText);
//        Intent intent = new Intent(this, myAccount.class);
        register = findViewById(R.id.btnRegister);
        register.setOnClickListener(view -> next());
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }
//test
    public void next() {
        Intent intent = new Intent(this, feature_Validatie_Scherm.class);
        startActivity(intent);
    }

    public void login() {
        EditText username = findViewById(R.id.username);
        String uname = username.getText().toString();

        EditText pass = findViewById(R.id.password);
        String password = pass.getText().toString();

        getProfilesFromJSON();
        try {
            String valPass = getProfilesFromJSON().get(8).toString();
            String valUser = getProfilesFromJSON().get(7).toString();
            System.out.println(getProfilesFromJSON());
            if (uname.isEmpty() || password.isEmpty()) {
                error.setText("Gebruikersnaam of wachtwoord is niet ingevuld.");
            } else {
                if (uname.equals(valUser) && password.equals(valPass)) {
                    Intent intent = new Intent(this, WorkoutActivity.class);
                    startActivity(intent);
                } else {

                    error.setText("Gegevens kloppen niet. Probeer opnieuw.");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
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
            profiles.put(name);
            profiles.put(lastname);
            profiles.put(function);
            profiles.put(number);
            profiles.put(age);
            profiles.put(lvl);
            profiles.put(time);
            profiles.put(username);
            profiles.put(password);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return profiles;
    }
}