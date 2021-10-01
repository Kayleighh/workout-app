package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class Exercise1Activity extends AppCompatActivity {

    private Button btnDone;
    private VideoView videoView;
    private TextView circle1;
    private GradientDrawable sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        System.out.println("je bent nu in exercise 1");

        videoView  = (VideoView) findViewById(R.id.videoView2);
        btnDone = findViewById(R.id.btnDone);
        circle1 = findViewById(R.id.circle1);

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
//                new CountDownTimer(10000, 1000) {
//                    public void onTick(long millisUntilFinished) {
//                    }
//
//                    public void onFinish() {
//                        mediaPlayer.setLooping(false);
//                        mediaPlayer.stop();
//                    }
//                };
            }
        });

        sd = (GradientDrawable) circle1.getBackground().mutate();
        sd.setColor(0xff999999);
        sd.invalidateSelf();
    }

    public void toNext(){
        System.out.println("je gaat nu naar exercise 2");
        Intent intent = new Intent(this, Exercise2Activity.class);
        startActivity(intent);
        sd.setColor(666666);
        sd.invalidateSelf();
    }
}