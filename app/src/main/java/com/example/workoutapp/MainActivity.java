package com.example.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private Button register;
    private Button login;
    TextView error;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) { login();
            }
        });
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 4);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);


        startAlarm(c);

    }
//test
    public void next() {
        Intent intent = new Intent(this, feature_Validatie_Scherm.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void login() {
        EditText username = findViewById(R.id.username);
        String uname = username.getText().toString();

        EditText pass = findViewById(R.id.password);
        String password = pass.getText().toString();
           String valPass =getProfilesFromJSON().get(1).toString();
            String valUser = getProfilesFromJSON().get(0).toString();
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

        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList getProfilesFromJSON() {
        ArrayList<String> profiles = new ArrayList<>();
        String filename = "test.json";
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(this.getFilesDir() + filename));
            Profile profile = gson.fromJson(reader,Profile.class);
            // close reader
            reader.close();

            profile.setNumber(profile.getNumber());
            profile.setFirstname(profile.getFirstname());
            profile.setLastname(profile.getLastname());
            profile.setDepartment(profile.getDepartment());
            profile.setAge(profile.getAge());
            profile.setLevel(profile.getLevel());
            profile.setTimes(profile.getTimes());
            profile.setUsername(profile.getUsername());
            profile.setPassword(profile.getPassword());
            System.out.println(profile.getPassword());
            profile.setImage(profile.getImage());
            profile.setNotifications(profile.getNotifications());

            profiles.add(profile.getUsername());
            profiles.add(profile.getPassword());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return profiles;
    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
}