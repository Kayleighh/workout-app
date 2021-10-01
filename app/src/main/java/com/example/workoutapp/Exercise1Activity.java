package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class Exercise1Activity extends AppCompatActivity {

    private Button btnDone;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_1);

        Button whatsapp = findViewById(R.id.whatsapp);
        //Get orientation of screen
        int orientation = this.getResources().getConfiguration().orientation;
        //If orientation is landscape, hide whatsapp button and ratingbar
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
          whatsapp.setVisibility(View.INVISIBLE);
            RatingBar rating = findViewById(R.id.ratingBar);
            rating.setVisibility(View.INVISIBLE);
        }

        //OnClickListener voor whatsapp knop.
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Variabele met het whatsappnummer
                String number = "+31620033805";
                //URL van de whatsapp api. Deze handelt verder alles af.
                String url = "https://api.whatsapp.com/send?phone="+number;
                //Begin nieuw intent
                Intent i = new Intent(Intent.ACTION_VIEW);
                //Stuur de url mee met de intent
                i.setData(Uri.parse(url));
                //Voer het uit
                startActivity(i);
            }
        });


        videoView  = (VideoView) findViewById(R.id.videoView2);

        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toNext();
            }
        });

        String videopath = "android.resource://com.example.workoutapp/" + R.raw.pushup;
        Uri uri = Uri.parse(videopath);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
                new CountDownTimer(10000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        mediaPlayer.stop();
                    }
                }.start();
            }
        });
    }

    public void toNext(){
        Intent intent = new Intent(this, Exercise2Activity.class);
        startActivity(intent);
    }
}