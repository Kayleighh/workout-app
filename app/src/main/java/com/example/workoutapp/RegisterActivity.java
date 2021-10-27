package com.example.workoutapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

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
                imageView.setImageURI(imageData);


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
                Bundle extras = getIntent().getExtras();
                if(extras != null){
                    String firstname = extras.getString("Firstname");
                    String lastname = extras.getString("Lastname");
                    String number = extras.getString("Number");
                    String function = extras.getString("Function");
/*
                    if(image == null){
                        Uri uri = Uri.parse("android:resource://com.example.workoutapp/drawable/placeholder");
                        try {
                            InputStream stream = getContentResolver().openInputStream(uri);
                            image = stream.toString();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }*/

                    Intent intent = new Intent(this, MyAccountActivity.class);
                    intent.putExtra("Firstname",firstname);
                    intent.putExtra("Lastname",lastname);
                    intent.putExtra("Number",number);
                    intent.putExtra("Function",function);
                    intent.putExtra("Username",gebruiknm);
                    intent.putExtra("Password",password1);
                    intent.putExtra("Image",image);


                    startActivity(intent);
                }
                System.out.println("test "+ gebruiknm);
                //addProfile(gebruiknm, password1);

            }

        } else {

            error.setText("Wachtwoorden zijn niet gelijk");

        }


    }
}