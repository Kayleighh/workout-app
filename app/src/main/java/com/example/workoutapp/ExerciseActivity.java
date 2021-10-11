package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    private Button btnDone;
    private VideoView videoView;
    private ArrayList<Integer> arrayListCircles = new ArrayList<>();
    private TextView exerciseNameTextView;
    private TrainingJSON trainingJSON = new TrainingJSON();
    private TextView exerciseTipTextView;
    private TextView exerciseDescriptionTextView;
    private Button whatsapp;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        findViews();
        addCirclesToList();
        setContent();

        btnDone.setOnClickListener(view -> {
            extras = getIntent().getExtras();
            if (extras != null) {

                int currentExerciseIndex = extras.getInt("key2");
                ArrayList<Integer> arrayListVideos = extras.getIntegerArrayList("key3");

                if (currentExerciseIndex != arrayListVideos.size() - 1) {
                    findViewById(R.id.ConstraintLayout2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.ConstraintLayout3).setVisibility(View.INVISIBLE);

                    findViewById(R.id.ConstraintLayout4).setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        finish();
                    }, 3000);
                }
                else {
                    finish();
                }
            }
        });

        whatsapp = findViewById(R.id.button2);
        int orientation = this.getResources().getConfiguration().orientation;
        //If orientation is landscape, hide whatsapp button and ratingbar
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            whatsapp.setVisibility(View.INVISIBLE);
            RatingBar rating = findViewById(R.id.ratingBar);
            rating.setVisibility(View.INVISIBLE);

            View circle1 = findViewById(R.id.ellipse_25);
            circle1.setVisibility(View.INVISIBLE);
            View circle2 = findViewById(R.id.ellipse_26);
            circle2.setVisibility(View.INVISIBLE);
            View circle3 = findViewById(R.id.ellipse_27);
            circle3.setVisibility(View.INVISIBLE);
            View circle4 = findViewById(R.id.ellipse_28);
            circle4.setVisibility(View.INVISIBLE);
            View circle5 = findViewById(R.id.ellipse_29);
            circle5.setVisibility(View.INVISIBLE);

        }

        //OnClickListener voor whatsapp knop.
        whatsapp.setOnClickListener(view -> {
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
        });
    }

    public void setContent(){
        extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key1");

            Uri uri = Uri.parse(value);
            videoView.setVideoURI(uri);
            videoView.setOnPreparedListener(mediaPlayer -> {
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
            });
            int currentExerciseIndex = extras.getInt("key2");
            int circle = arrayListCircles.get(currentExerciseIndex);
            View ellipse = findViewById(circle);
            ellipse.setBackground(ContextCompat.getDrawable(this, R.drawable.ellipse));
            System.out.println("je bent nu in exercise " + (currentExerciseIndex + 1));

            try {
                trainingJSON.getJSONObject(getApplicationContext());
            }catch(IOException | JSONException e) {
                e.printStackTrace();
            }
            for(Training training : trainingJSON.getTraining())
            {
                System.out.println(trainingJSON.getTraining().size());
                System.out.println(training.getExerciseName());
            }

            exerciseNameTextView= findViewById(R.id.textView3);
            exerciseTipTextView=findViewById(R.id.textView2);
            exerciseDescriptionTextView = findViewById(R.id.textView6);

            if(currentExerciseIndex == 0 )
            {
                exerciseNameTextView.clearComposingText();
                exerciseNameTextView.setText("Squat");

                for (Training training :trainingJSON.getTraining())
                {
                    System.out.println(training.getExerciseName());
                    if (training.getExerciseName().equals("Squat"))
                    {
                        String tip = training.getExerciseTip();
                        exerciseTipTextView.clearComposingText();
                        exerciseTipTextView.setText(tip);
                        exerciseDescriptionTextView.clearComposingText();
                        exerciseDescriptionTextView.setText(training.getExerciseDescription());
                    }

                }
            }
            if(currentExerciseIndex==1)
            {
                exerciseNameTextView.clearComposingText();
                exerciseNameTextView.setText("Push up");

                for (Training training :trainingJSON.getTraining())
                {

                    if (training.getExerciseName().equals("Push up"))
                    {
                        String tip = training.getExerciseTip();
                        exerciseTipTextView.clearComposingText();
                        exerciseTipTextView.setText(tip);
                        exerciseDescriptionTextView.clearComposingText();
                        exerciseDescriptionTextView.setText(training.getExerciseDescription());
                    }
            }
        }
    }}

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






















