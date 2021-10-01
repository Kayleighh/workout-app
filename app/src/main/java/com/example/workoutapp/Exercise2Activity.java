package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class Exercise2Activity extends AppCompatActivity {

    private VideoView videoView2;
    private Button btnDone;
    public static boolean showBtn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBtn = true;
        setContentView(R.layout.activity_exercise_2);
        videoView2 = findViewById(R.id.videoView2);

        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishWorkout();
            }
        });

//        String videopath = "android.resource://com.example.workoutapp/" + R.raw.squat;
//        Uri uri = Uri.parse(videopath);
//        videoView2.setVideoURI(uri);
//        videoView2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.start();
//                mediaPlayer.setLooping(true);
//                new CountDownTimer(10000, 1000) {
//                    public void onTick(long millisUntilFinished) {
//                    }
//
//                    public void onFinish() {
//                        mediaPlayer.stop();
//                    }
//                }.start();
//            }
//        });
    }

    public void finishWorkout(){
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
        WorkoutActivity.btnFinishWorkout.setVisibility(View.VISIBLE);
    }
}