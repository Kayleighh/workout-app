package com.example.workoutapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class EditAccount extends AppCompatActivity {
    Uri imageData;
    String image;
    ImageView newProfilePic;
    private static final int GALLERY_REQUEST_CODE = 123;
    ConstraintLayout parent;
    ConstraintLayout mainScreen;
    ConstraintLayout changeUsername;
    ConstraintLayout changePassword;
    ConstraintLayout changePhoto;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        showImage();
        parent = findViewById(R.id.parent);
        mainScreen = findViewById(R.id.startPage);
        changeUsername = findViewById(R.id.changeUsername);
        changePassword = findViewById(R.id.changePassword);
        changePhoto = findViewById(R.id.changePhoto);

        parent.setVisibility(View.VISIBLE);
        mainScreen.setVisibility(View.VISIBLE);
        changeUsername.setVisibility(View.INVISIBLE);
        changePassword.setVisibility(View.INVISIBLE);
        changePhoto.setVisibility(View.INVISIBLE);

        Button btnUsername = findViewById(R.id.button6);
        Button btnPassword = findViewById(R.id.button7);
        Button btnPhoto = findViewById(R.id.button5);

        btnUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUsername();
            }
        });

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePhoto();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showImage()
    {
        getProfilesFromJSON();
        ImageView image = findViewById(R.id.imageView);
        image.setImageDrawable(getResources().getDrawable(R.drawable.placeholder));
        String imagefromJSON = getProfilesFromJSON().get(7);
        if(imagefromJSON != null){
                System.out.println("image test");

            }
        }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> getProfilesFromJSON() {
        ArrayList<String> profiles = new ArrayList<>();

        String filename = "test.json";
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(this.getFilesDir() + filename));
            Profile profile = gson.fromJson(reader, Profile.class);
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

    private void back(){
        Intent intent = new Intent(this,WorkoutActivity.class);
        startActivity(intent);
    }

    private void changePhoto()
    {
        //changePhoto = findViewById(R.id.changePhoto);
        changePhoto.setVisibility(View.VISIBLE);
        changePassword.setVisibility(View.INVISIBLE);
        changeUsername.setVisibility(View.INVISIBLE);
        mainScreen.setVisibility(View.INVISIBLE);

        Button profilePic = findViewById(R.id.savePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
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
            newProfilePic = findViewById(R.id.imageView);
            newProfilePic.setImageURI(imageData);
            mainScreen.setVisibility(View.VISIBLE);
            changePhoto.setVisibility(View.INVISIBLE);


        }
    }

    private void changeUsername()
    {
        //ConstraintLayout changeUsername = findViewById(R.id.changeUsername);
        changeUsername.setVisibility(View.VISIBLE);
        changePassword.setVisibility(View.INVISIBLE);
        changePhoto.setVisibility(View.INVISIBLE);
        mainScreen.setVisibility(View.INVISIBLE);

        EditText editUsername = findViewById(R.id.editUsername);
        String newUsername = editUsername.getText().toString();
        Button saveUsername = findViewById(R.id.saveUsername);
        saveUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(newUsername);
            }
        });
    }

    private void changePassword()
    {
        //ConstraintLayout changePassword = findViewById(R.id.changePassword);
        changePassword.setVisibility(View.VISIBLE);
        changeUsername.setVisibility(View.INVISIBLE);
        changePhoto.setVisibility(View.INVISIBLE);
        mainScreen.setVisibility(View.INVISIBLE);

        EditText editPassword = findViewById(R.id.editTextTextPassword);
        EditText editPassword2 = findViewById(R.id.editTextTextPassword2);
        String pass1 = editPassword.getText().toString();
        String pass2 = editPassword2.getText().toString();

        Button savePassword = findViewById(R.id.savePassword);
        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass1.isEmpty() || pass2.isEmpty()){
                    System.out.println("Vul beide velden in of klik op annuleer.");
                }else{
                    if(pass1.equals(pass2)){
                        System.out.println(pass1 + " " + pass2);
                    }
                }
            }
        });
    }
}