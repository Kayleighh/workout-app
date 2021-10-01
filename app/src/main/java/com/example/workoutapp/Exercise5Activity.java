package com.example.workoutapp;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Exercise5Activity extends AppCompatActivity {

    private Button btnDone;
    private VideoView videoView;
    public static boolean showBtn = false;
    private TextView circle5;
    private GradientDrawable sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        System.out.println("je bent nu in exercise 5");

        showBtn = true;

        videoView  = (VideoView) findViewById(R.id.videoView2);
        btnDone = findViewById(R.id.btnDone);
        circle5 = findViewById(R.id.circle5);

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
//                        mediaPlayer.stop();
//                    }
//                }.start();
            }
        });

        sd = (GradientDrawable) circle5.getBackground().mutate();
        sd.setColor(0xff999999);
        sd.invalidateSelf();
    }

    public void toNext(){
        System.out.println("je gaat nu terug naar home");
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
        WorkoutActivity.btnFinishWorkout.setVisibility(View.VISIBLE);
        sd.setColor(666666);
        sd.invalidateSelf();
    }
}
