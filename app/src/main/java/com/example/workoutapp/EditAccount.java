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

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

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
    Profile profile;
    HashMap<String,String>notifications;
    HashMap<String,String>times;
    String newUsername;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    /*On create show the profile picture: Only works with placeholder because of an issue with permissions.
    The screen consists of multiple layouts. The parent layout constains the basic elements that need to be displayed at all times.
    Mainscreen is the layout that holds all the elements needed to create the screen as seen in Figma.
    The other layouts are shown when clicked on the respective buttons on the mainscreen.
    */
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
        Button btnDelete = findViewById(R.id.button8);

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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();
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
           System.out.println("Needs to be fixed: Permission issues.");

        }
    }


    //Method that gets all the info from the json file using Gson. It then makes a new instance of the Profile class and sets all the methods using the data from the json.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> getProfilesFromJSON() {
        ArrayList<String> profiles = new ArrayList<>();
        //HashMap<String,String> notifications = new HashMap<>();
        //HashMap<String,String> times = new HashMap<>();
        String filename = "test.json";
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(this.getFilesDir() + filename));
            profile = gson.fromJson(reader, Profile.class);
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
            profile.setNotifications(profile.getNotifications());

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

    //Method to open the gallery on the phone.
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
    //Method for when a picture has been chosen.
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            imageData = data.getData();
            image = imageData.toString();
            newProfilePic = findViewById(R.id.imageView);
            newProfilePic.setImageURI(imageData);

            //Set all the methods properly with the data from the json.
            getProfilesFromJSON();
            notifications = profile.getNotifications();
            times = profile.getTimes();
            profile.setNumber(getProfilesFromJSON().get(0));
            profile.setFirstname(getProfilesFromJSON().get(1));
            profile.setLastname(getProfilesFromJSON().get(2));
            profile.setDepartment(getProfilesFromJSON().get(3));
            profile.setAge(getProfilesFromJSON().get(4));
            profile.setLevel(getProfilesFromJSON().get(5));
            profile.setUsername(getProfilesFromJSON().get(6));
            profile.setPassword(getProfilesFromJSON().get(7));

            profile.setNotifications(notifications);
            profile.setTimes(times);
            //Set this method with the new Image uri.
            profile.setImage(image);
            System.out.println(image);
            toJSON(profile);
            mainScreen.setVisibility(View.VISIBLE);
            changePhoto.setVisibility(View.INVISIBLE);


        }
    }

    /*
        All these methods work the same way: Set all the methods of the profile class with the data from the json, except for the field you want to change.
        Set that one with the data the user fills in. Then send it to the toJSON file, that will write the new data to the json file.
     */
    private void changeUsername()
    {

        changeUsername.setVisibility(View.VISIBLE);
        changePassword.setVisibility(View.INVISIBLE);
        changePhoto.setVisibility(View.INVISIBLE);
        mainScreen.setVisibility(View.INVISIBLE);


        Button saveUsername = findViewById(R.id.saveUsername);
        saveUsername.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                EditText editUsername = findViewById(R.id.editUsername);
                newUsername = editUsername.getText().toString();

                getProfilesFromJSON();

                notifications = profile.getNotifications();
                times = profile.getTimes();
                profile.setNumber(getProfilesFromJSON().get(0));
                profile.setFirstname(getProfilesFromJSON().get(1));
                profile.setLastname(getProfilesFromJSON().get(2));
                profile.setDepartment(getProfilesFromJSON().get(3));
                profile.setAge(getProfilesFromJSON().get(4));
                profile.setLevel(getProfilesFromJSON().get(5));
                profile.setPassword(getProfilesFromJSON().get(7));
                profile.setImage(getProfilesFromJSON().get(8));
                profile.setNotifications(notifications);
                profile.setTimes(times);
                profile.setUsername(newUsername);
                toJSON(profile);
                mainScreen.setVisibility(View.VISIBLE);
                changeUsername.setVisibility(View.INVISIBLE);

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



        Button savePassword = findViewById(R.id.savePassword);
        savePassword.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                EditText editPassword = findViewById(R.id.enterPass2);
                EditText editPassword2 = findViewById(R.id.enterPass1);
                String pass1 = editPassword.getText().toString();
                String pass2 = editPassword2.getText().toString();
                if(pass1.isEmpty() || pass2.isEmpty()){
                    System.out.println("Vul beide velden in of klik op annuleer.");
                }else{
                    if(pass1.equals(pass2)){
                        getProfilesFromJSON();
                        notifications = profile.getNotifications();
                        times = profile.getTimes();
                        profile.setNumber(getProfilesFromJSON().get(0));
                        profile.setFirstname(getProfilesFromJSON().get(1));
                        profile.setLastname(getProfilesFromJSON().get(2));
                        profile.setDepartment(getProfilesFromJSON().get(3));
                        profile.setAge(getProfilesFromJSON().get(4));
                        profile.setLevel(getProfilesFromJSON().get(5));
                        profile.setUsername(getProfilesFromJSON().get(6));
                        profile.setImage(getProfilesFromJSON().get(7));
                        System.out.println(getProfilesFromJSON().get(7));
                        profile.setNotifications(notifications);
                        profile.setTimes(times);
                        profile.setPassword(pass1);
                        toJSON(profile);
                        mainScreen.setVisibility(View.VISIBLE);
                        changePassword.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    /*
        Basically writes nothing to the json so the account doesnt excists anymore. Then sends you back to the first screen of the program.
     */
    private void deleteAccount()
    {
        System.out.println("Your account has been deleted");
        Intent intent = new Intent(this,MainActivity.class);
        Profile profile = new Profile();
        toJSON(profile);
        startActivity(intent);
    }

    //Writes a Profile instance to the json file using Gson. The file can be found in device file explores -> data -> data -> com.example.workoutapp -> filetest.json
    private void toJSON(Profile profile)
    {
        Gson gson = new Gson();
        String filename = "test.json";
        try {
            //getFilesDir finds the directory on the phone it needs to create the file in.
            FileWriter writer = new FileWriter(this.getFilesDir() + filename);
            gson.toJson(profile, writer);
            writer.flush(); //flush data to file   <---
            writer.close(); //close write
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}