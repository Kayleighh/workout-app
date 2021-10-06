package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    private Button btnDone;
    private VideoView videoView;
    private ArrayList<Integer> arrayListCircles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        findViews();
        addCirclesToList();
        setContent();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setContent(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key1");

            Uri uri = Uri.parse(value);
            videoView.setVideoURI(uri);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();

                    new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long l) {
                            mediaPlayer.setLooping(true);
                        }

                        public void onFinish() {
                            mediaPlayer.stop();
                        }
                    };
                }
            });
            int currentExerciseIndex = extras.getInt("key2");
            int circle = arrayListCircles.get(currentExerciseIndex);
            View ellipse = findViewById(circle);
            ellipse.setBackground(ContextCompat.getDrawable(this, R.drawable.ellipse));
            System.out.println("je bent nu in exercise " + (currentExerciseIndex + 1));
        }
    }

    public void addCirclesToList(){
        arrayListCircles.add(R.id.ellipse_25);
        arrayListCircles.add(R.id.ellipse_26);
        arrayListCircles.add(R.id.ellipse_27);
        arrayListCircles.add(R.id.ellipse_28);
        arrayListCircles.add(R.id.ellipse_29);
    }

    public void findViews(){
        videoView  = findViewById(R.id.videoView2);
        btnDone = findViewById(R.id.btnDone);
    }
}






















