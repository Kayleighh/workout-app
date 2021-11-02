package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
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
    private Bundle extras;
    private int currentProgress = 0;
    private ProgressBar pb;
    private View rectangleCloseRest;
    private TextView textRest;
    private long timeLeftInMillis = 5000;
    private TextView repCounter;
    private ProgressBar progressTest;
    private Integer rep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        findViews();
        addCirclesToList();
        setContent();

        btnDone.setOnClickListener(this::onClick);

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
            //Get the reps this exercise has.
            getReps();
            //Make the countdown.
            countdownReps();



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

    private void onClick(View view) {
        extras = getIntent().getExtras();
        if (extras != null) {

            int currentExerciseIndex = extras.getInt("key2");
            ArrayList<Integer> arrayListVideos = extras.getIntegerArrayList("key3");

            if (currentExerciseIndex != arrayListVideos.size() - 1) {
                findViewById(R.id.ConstraintLayout2).setVisibility(View.INVISIBLE);
                findViewById(R.id.ConstraintLayout3).setVisibility(View.INVISIBLE);

                findViewById(R.id.ConstraintLayout4).setVisibility(View.VISIBLE);

                CountDownTimer countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long l) {

                        currentProgress = currentProgress + 10;
                        pb.setProgress(currentProgress);
                        pb.setMax(100);
                        rectangleCloseRest.setOnClickListener(view1 -> finish());
                        updateTextRest((int) l / 1000);
                    }

                    @Override
                    public void onFinish() {
                        finish();
                    }
                }.start();
            } else {
                finish();
            }
        }
    }

    private void setContent(){
        extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key1");

            Uri uri = Uri.parse(value);
            videoView.setVideoURI(uri);
            videoView.setOnPreparedListener(mediaPlayer -> {
                mediaPlayer.start();
               // countdownReps();
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

                for (Training training :trainingJSON.getTraining()) {

                    if (training.getExerciseName().equals("Push up")) {
                        String tip = training.getExerciseTip();
                        exerciseTipTextView.clearComposingText();
                        exerciseTipTextView.setText(tip);
                        exerciseDescriptionTextView.clearComposingText();
                        exerciseDescriptionTextView.setText(training.getExerciseDescription());
                    }
                }
            }
        }
    }

    private void addCirclesToList(){
        arrayListCircles.add(R.id.ellipse_25);
        arrayListCircles.add(R.id.ellipse_26);
        arrayListCircles.add(R.id.ellipse_27);
        arrayListCircles.add(R.id.ellipse_28);
        arrayListCircles.add(R.id.ellipse_29);
    }

    private void findViews(){
        videoView  = findViewById(R.id.videoView2);
        btnDone = findViewById(R.id.btnDone);
        pb = findViewById(R.id.repProgress);
        rectangleCloseRest = findViewById(R.id.closeRest);
        textRest = findViewById(R.id.textRest);
        repCounter = findViewById(R.id.repCounter);
        progressTest = findViewById(R.id.repProgress);
    }

    public void updateTextRest(int progress){
        int seconds = progress % 60;
        String timeLeftText;
        if (seconds < 10){
            timeLeftText = "0" + seconds;
        }
        else {
            timeLeftText = "" + seconds;
        }

        textRest.setText(timeLeftText);
    }

    //Update the text with the remaining reps.
    public void updateRepCounter(int progress){
        int reps = progress;
        String repsLeft;
        repsLeft = ""+reps;
        repCounter.setText(repsLeft);


    }

    //Turns the progressbar into a countdown bar. For every 5 seconds make the progressbar move 5%.
    private void countdownReps()
    {
        CountDownTimer test = new CountDownTimer(timeLeftInMillis,5000) {
            @Override
            public void onTick(long l) {
                currentProgress = currentProgress + 5;

                progressTest.setProgress(currentProgress);
                progressTest.setMax(100);
                updateRepCounter(rep);

            }

            @Override
            //If the countdowntimer is finished and the reps are all done set the textview to workout klaar
            public void onFinish() {
                if(rep == 0){
                    repCounter.setText("Workout klaar");
                }else{
                    //If the countdowntimer is finished but the reps arent yet. Start again.
                    rep = rep -1;
                    start();
                }
            }
        }.start();
    }

    private int getReps()
    {
        for (Training training :trainingJSON.getTraining())
        {
            if (training.getExerciseName().equals("Squat"))
            {
                rep = training.getAmountOfRepsPerSet().get(0);

            }
            if(training.getExerciseName().equals("Push up")){
                rep = training.getAmountOfRepsPerSet().get(0);
            }

        }
        return rep;
    }
}






















