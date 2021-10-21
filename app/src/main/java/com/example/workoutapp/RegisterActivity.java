package com.example.workoutapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 123;
    Uri imageData;
    String image;
    Button button;
    TextView error;
ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        error = findViewById(R.id.errorText2);
         imageView = findViewById(R.id.imgProfielFoto);
        button = findViewById(R.id.btnVolgende);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        Button profilePic = findViewById(R.id.btnKiesFoto);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Pick an image"), GALLERY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            imageData = data.getData();
             image = imageData.toString();
                imageView.setImageURI(imageData);

        }
        else{
            image = "android:resource://com.example.workoutapp/drawable/placeholder";
            System.out.println("test");
        }
    }

    public void next() {
        EditText gebruikersnaam = findViewById(R.id.edtGebruikersnaam);
        EditText pass1 = findViewById(R.id.edtWachtwoord);
        EditText pass2 = findViewById(R.id.edtWachtwoordHerhaal);
        String gebruiknm = gebruikersnaam.getText().toString();
        String password1 = pass1.getText().toString();
        String password2 = pass2.getText().toString();
        if (password1.equals(password2)) {
            if(password1.isEmpty() || password2.isEmpty() || gebruiknm.isEmpty()){
                error.setText("Vul a.u.b alle velden in.");
            }

            else{
                System.out.println("test "+ gebruiknm);
                addProfile(gebruiknm, password1);
                Intent intent = new Intent(this, MyAccountActivity.class);
                startActivity(intent);
            }

        } else {

            error.setText("Wachtwoorden zijn niet gelijk");

        }


    }

    public JSONObject addProfile(String gebruikersnaam, String wachtwoord) {
        JSONObject jsonObject = new JSONObject();

        try {
            getProfilesFromJSON();
            if(image == null){
                image = "android:resource://com.example.workoutapp/drawable/placeholder";
            }
            String firstname = getProfilesFromJSON().get(0).toString();
            String lastname = getProfilesFromJSON().get(1).toString();
            String function = getProfilesFromJSON().get(2).toString();
            String number = getProfilesFromJSON().get(3).toString();
            jsonObject.put("Firstname", firstname);
            jsonObject.put("Lastname", lastname);
            jsonObject.put("Function", function);
            jsonObject.put("Number", number);
            jsonObject.put("Username", gebruikersnaam);
            jsonObject.put("Password", wachtwoord);
            jsonObject.put("Age", 0);
            jsonObject.put("Level", "Beginner");
            jsonObject.put("Time", "09:00");
            jsonObject.put("Image",image);
            jsonObject.put("Points", 0);           // toevoeging voor puntentelling

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
            //String image = jsonObject.getString("Image");
            String points = jsonObject.get("Points").toString();    // toevoeging voor puntentelling
            profiles.put(name);
            profiles.put(lastname);
            profiles.put(function);
            profiles.put(number);
            profiles.put(age);
            profiles.put(lvl);
            profiles.put(time);
            profiles.put(username);
            profiles.put(password);
            profiles.put(image);
            profiles.put(points);        // toevoeging voor puntentelling

            System.out.println(age + " " + lvl + " " + time);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return profiles;
    }
}