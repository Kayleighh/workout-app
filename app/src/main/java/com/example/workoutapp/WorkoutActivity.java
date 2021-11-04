package com.example.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class WorkoutActivity extends AppCompatActivity {

    private View btnStartWorkout;
    private View finishWorkout;
    private View checkMark;
    private int currentExerciseIndex = -2;
    private ArrayList<Integer> arrayListVideos = new ArrayList<>();
    private ArrayList<Integer> arrayListThumbnails = new ArrayList<>();
    private boolean shouldExecuteOnResume;
    static WorkoutActivity activityA;
    private ArrayList<String> testTimes = new ArrayList<>();
    private ArrayList<String> testDays = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        addVideosToList();
        addThumbnailsToList();
        findViews();
        setCurrentMonth(findViewById(R.id.txtMaand));
        GetChosenTimes(testTimes);
        rectanglesAction(makeButtonsList());
        setRectangles();
        shouldExecuteOnResume = false;
        activityA = this;
       startButtonActionListener();
        finishWorkout.setEnabled(false);
        finishWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishWorkout();
            }
        });
    }

    public static WorkoutActivity getInstance(){
        return activityA;
    }

    @Override
    protected void onResume() {
        currentExerciseIndex++;

        if (shouldExecuteOnResume){
            try {
                if (currentExerciseIndex == arrayListVideos.size()){
                    finishWorkout.setEnabled(true);
                    finishWorkout.setBackground(ContextCompat.getDrawable(this, R.drawable.finish));
                    checkMark.setVisibility(View.VISIBLE);
                }
                else {
                    String videopath = "android.resource://com.example.workoutapp/" + arrayListVideos.get(currentExerciseIndex);
                    int thumbnail = arrayListThumbnails.get(currentExerciseIndex);

                    Intent ExerciseIntent = new Intent(this, ExerciseActivity.class);
                    ExerciseIntent.putExtra("key1", videopath);
                    ExerciseIntent.putExtra("key2", currentExerciseIndex);
                    ExerciseIntent.putExtra("key3", arrayListVideos);
                    ExerciseIntent.putExtra("key4", shouldExecuteOnResume);
                    ExerciseIntent.putExtra("key5", thumbnail);

                    startActivity(ExerciseIntent);

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            shouldExecuteOnResume = true;
        }
        super.onResume();
    }


    public void finishWorkout(){
        finish();
        finishWorkout.setEnabled(false);
    }

    //A method that add a onCLickListener to the button "StartWorkout"
    //The method checks if the date and the time match with the choosen dates and times by the user and returns a warning if it doesnt match.
    private void startButtonActionListener()
    {
        btnStartWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView warning = findViewById(R.id.warningView1);
                if(!CheckIfSelected(makeButtonsList()))
                {
                    warning.setText("Er is geen datum gekozen");
                    warning.setTextSize(18);
                    warning.setVisibility(View.VISIBLE);
                }
                else if(compareTime(Integer.parseInt(getChosenTime(getTime(makeButtonsList())).substring(0,2)),getCurrentHour()) && compareDay(translateDaysToDutch(getCurrentDay()).substring(0,4),getTime(makeButtonsList()).substring(0,4)))
                {
                    onResume();
                }
                else if(compareTime(Integer.parseInt(getChosenTime(getTime(makeButtonsList())).substring(0,2)),getCurrentHour()) && !compareDay(translateDaysToDutch(getCurrentDay()).substring(0,4),getTime(makeButtonsList()).substring(0,4)))
                {
                    warning.clearComposingText();
                    warning.setText("De gekozen dag klopt niet");
                    warning.setVisibility(View.VISIBLE);
                }

                else if(!compareTime(Integer.parseInt(getChosenTime(getTime(makeButtonsList())).substring(0,2)),getCurrentHour()) && compareDay(translateDaysToDutch(getCurrentDay()).substring(0,4),getTime(makeButtonsList()).substring(0,4)))
                {
                    warning.clearComposingText();
                    warning.setText("Het bepaalde tijd is niet correct voor die gekozen dag");
                    warning.setVisibility(View.VISIBLE);//1
                }
                //onResume();
                else if (!compareTime(Integer.parseInt(getChosenTime(getTime(makeButtonsList())).substring(0,2)),getCurrentHour()) && !compareDay(translateDaysToDutch(getCurrentDay()).substring(0,4),getTime(makeButtonsList()).substring(0,4)))
                {
                    warning.clearComposingText();
                    warning.setText("De gekozen dag en tijd kloppen niet");
                    warning.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    //A method that is used to put all the 5 buttons from the horizontal scroll agenda(for days) in 1 arraylist.
    //Returns an arraylist with buttons.
    private ArrayList<Button> makeButtonsList()
    {
        Button btn0 = findViewById(R.id.rectangle0);
        Button btn1 = findViewById(R.id.rectangle1);
        Button btn2 = findViewById(R.id.rectangle2);
        Button btn3 = findViewById(R.id.rectangle3);
        Button btn4 = findViewById(R.id.rectangle4);

        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(btn0);
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        return buttons;
    }

    //A method that set the 5 buttons in the scroll agenda to be the next 5 days of the week starting from today
    //it makes sure that weekend days dont be showed on the screen.
    private void setRectangles()
    {
        for(int i = 0;i<7;i++)
        {

            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE,i);
            dt = c.getTime();
            String date = String.valueOf(dt);
            String day = date.substring(0,3);
            String datum = date.substring(8,10);
            if(day.equals("Sat")){}
            else if(day.equals("Sun")){}
            else{String fullDate =translateDaysToDutch(day) +"\n"+ datum;
                chooseRectangle(fullDate);}
        }
    }
    //A method that is used to set text of the 5 buttons in the scroll agenda.
    // It checks if the buttons is emptey so it fills it with the first given text
    //It make sure they get in the right order.
    private void chooseRectangle(String text)
    {
        Button btn0 = findViewById(R.id.rectangle0);
        Button btn1 = findViewById(R.id.rectangle1);
        Button btn2 = findViewById(R.id.rectangle2);
        Button btn3 = findViewById(R.id.rectangle3);
        Button btn4 = findViewById(R.id.rectangle4);
        if(btn0.getText().equals(""))
        {
            btn0.setText(text);
        }
        else if(btn1.getText().equals("")){btn1.setText(text);}
        else if(btn2.getText().equals("")){btn2.setText(text);}
        else if(btn3.getText().equals("")){btn3.setText(text);}
        else if(btn4.getText().equals("")){btn4.setText(text);}

    }


    //A method used to find views of the buttons or textviews.
    private void findViews(){
        btnStartWorkout = findViewById(R.id.btnStartWorkout);
        finishWorkout = findViewById(R.id.btnFinishWorkout);
        checkMark = findViewById(R.id.check_mark_);
        TextView warning = findViewById(R.id.warningView1);
        warning.setVisibility(View.INVISIBLE);
    }

    //Adding onClickListener to all the buttons from the scroll agenda.
    private void rectanglesAction(ArrayList<Button> buttons) {


        for (Button b : buttons) {
            b.setBackgroundColor(Color.DKGRAY);
        }
        for (Button b : buttons) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b.setBackgroundColor(Color.BLACK);
                    b.setSelected(true);
                    findViewById(R.id.warningView1).setVisibility(View.INVISIBLE);
                    for (Button b1 : buttons) {
                        if (b1 != b) {
                            b1.setBackgroundColor(Color.DKGRAY);
                            b1.setSelected(false);
                        }

                    }
                    System.out.println(b.isSelected());

                }
            });
        }
    }
    //A method that is used to get the chosen times of the user so we can check if the user can get into the workout page.
    //It put the times in one arraylist on a right order (starting from monday finishing with friday)
    //The right order is important in this array cause the index is used later to decide if which day it is.
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GetChosenTimes(ArrayList<String> list)
    {
//        Settings settings = new Settings();
//        settings.getTimes();
        HashMap times = new HashMap();

        String filename = "test.json";
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(this.getFilesDir() + filename));
            Profile profile = gson.fromJson(reader, Profile.class);
            profile.setTimes(profile.getTimes());
            times = profile.getTimes();
            list.add(String.valueOf(times.get("Monday")));
            list.add(String.valueOf(times.get("Tuesday")));
            list.add(String.valueOf(times.get("Wednesday")));
            list.add(String.valueOf(times.get("Thursday")));
            list.add(String.valueOf(times.get("Friday")));
            // close reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //A method to decide which month it is.
    //used to add this month to the layout of the app.
    private void setCurrentMonth(TextView textView)
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH) + 1;

        String month = "";
        if(thisMonth ==1){month = "Januari";}
        if(thisMonth ==2){month = "Februari";}
        if(thisMonth ==3){month = "Maart";}
        if(thisMonth ==4){month = "April";}
        if(thisMonth ==5){month = "Mei";}
        if(thisMonth ==6){month = "Juni";}
        if(thisMonth ==7){month = "Juli";}
        if(thisMonth ==8){month = "Augustus";}
        if(thisMonth ==9){month = "September";}
        if(thisMonth ==10){month = "Oktober";}
        if(thisMonth ==11){month = "November";}
        if(thisMonth ==12){month = "December";}

        textView.setText(month);

    }



    public void addVideosToList(){
        arrayListVideos.add(R.raw.squat);
        arrayListVideos.add(R.raw.pushup);
    }

    public void addThumbnailsToList(){
        arrayListThumbnails.add(R.drawable.squat);
        arrayListThumbnails.add(R.drawable.pushup);
    }

    //a method to get all the info from the choosen button from the scroll agenda.
    //such like (date , day and time) and its used later with a subString method to seperate all of these info.
    private String getTime(ArrayList<Button> buttons)
    {
        String s = "";
        for(Button b : buttons)
        {
            if (b.isSelected()==true)
            {
                s = String.valueOf(b.getText());
            }
        }
        return s;
    }
    //A method that is used to translate days from english to dutch.
    //its used for comparing.
    private String translateDaysToDutch(String englishDay)
    {
        String dutchDay = "";
        if(englishDay.equals("Mon")){dutchDay = "Maandag";}
        if(englishDay.equals("Tue")){dutchDay = "Dinsdag";}
        if(englishDay.equals("Wed")){dutchDay = "Woensdag";}
        if(englishDay.equals("Thu")){dutchDay = "Donderdag";}
        if(englishDay.equals("Fri")){dutchDay = "Vrijdag";}
        if(englishDay.equals("Sat")){dutchDay = "Zaterdag";}
        if(englishDay.equals("Sun")){dutchDay = "Zondag";}
        return dutchDay;
    }

    //A method that checks which day it is (from the gettime method) and then gets the matching day from the arraylist-
    // -of the chosen times of the user(JSON file)(GetChosenTimes).
    private String getChosenTime(String text)
    {
        String s ="";
        if(text.startsWith("Maan")){s = s +testTimes.get(0);}
        if(text.startsWith("Dins")){s = s +testTimes.get(1);}
        if(text.startsWith("Woen")){s = s +testTimes.get(2);}
        if(text.startsWith("Dond")){s = s +testTimes.get(3);}
        if(text.startsWith("Vrij")){s = s +testTimes.get(4);}
        System.out.println(s);

        return s;
    }
    //A method to get the current hour.
    //Returns an int of the value of the current hour
    //Example : if its 15:24 -> it returns 15.
    private int getCurrentHour()
    {
        Date currentTime = Calendar.getInstance().getTime();
        int x = Integer.parseInt(String.valueOf(currentTime).substring(11,13));
        System.out.println(x);
        return  x;
    }
    //A method that returns the current day a string.
    //Returns String(Day).
    private String getCurrentDay()
    {
        Date currentTime = Calendar.getInstance().getTime();
        String day = String.valueOf(currentTime).substring(0,3);
        return day;
    }
    //A method that compare the current time with the chosen time.
    //Return boolean
    private boolean compareTime(int x , int y)
    {
        boolean time = false;
        if(x==y)
        {
            time =true;
        }
        return time;
    }

    //A method to compare the current Day with the chosen day.
    //Return boolean.
    private boolean compareDay(String x , String y)
    {
        boolean today = false;

        if(x.equals(y))
        {
            today = true;
        }
        return today;
    }

    //A method that checks which button out of the buttons arraylist (horizontally scroll bar) is selected.
    //Returns boolean.
    private boolean CheckIfSelected(ArrayList<Button> buttons)
    {
        boolean select = false;
        for(Button b : buttons)
        {
            if(b.isSelected())
            {
                select = true;
            }


        }
        return select;
    }

}