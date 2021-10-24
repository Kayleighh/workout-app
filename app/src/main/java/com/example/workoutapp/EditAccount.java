package com.example.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class EditAccount extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        getProfilesFromJSON();
        ImageView image = findViewById(R.id.imageView);
        image.setImageDrawable(getResources().getDrawable(R.drawable.placeholder));
        String imagefromJSON = getProfilesFromJSON().get(7);
        if(imagefromJSON != null){
            /*Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);*/
            image.setImageURI(Uri.parse(imagefromJSON));
        }
        System.out.println(getProfilesFromJSON().get(7));
        //image.setImageURI(Uri.parse(getProfilesFromJSON().get(7)));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> getProfilesFromJSON() {
        ArrayList<String> profiles = new ArrayList<>();

        String filename = "test.json";
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(this.getFilesDir() + filename));
            Profile profile = gson.fromJson(reader, Profile.class);
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
            profile.setImage(profile.getImage());


            profiles.add(profile.getNumber());
            profiles.add(profile.getFirstname());
            profiles.add(profile.getLastname());
            profiles.add(profile.getDepartment());
            profiles.add(profile.getAge());
            profiles.add(profile.getUsername());
            profiles.add(profile.getPassword());
            profiles.add(profile.getImage());
            profiles.add(profile.getLevel());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return profiles;
    }
}