package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        addVideosToList();
        addThumbnailsToList();
        findViews();
        setCurrentMonth(findViewById(R.id.txtMaand));
        fillTimes(testTimes);
        rectanglesAction(makeButtonsList());
//        setFirstRectengles(findViewById(R.id.rectangle0));
//        setSecondRectangles(findViewById(R.id.rectangle1));
        setRectangles();
        shouldExecuteOnResume = false;
        activityA = this;



//        btnStartWorkout.setBackgroundColor(Color.argb(223, 96, 55, 1));

        btnStartWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView warning = findViewById(R.id.warningView1);
                if(!isSelected(makeButtonsList()))
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

        // finish workout button is set disabled but when enabled and clicked finish workout
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

    // gets called whenever this activity is being resumed (in our case when a exercise is finished)
    @Override
    protected void onResume() {
        // exercise index increases by one because we want to go to the next exercise
        currentExerciseIndex++;

        if (shouldExecuteOnResume){
            try {
                // when index is equal to the amount of videos in the video list enable the finish workout button
                // so that user can finish workout
                if (currentExerciseIndex == arrayListVideos.size()){
                    finishWorkout.setEnabled(true);
                    finishWorkout.setBackground(ContextCompat.getDrawable(this, R.drawable.finish));
                    checkMark.setVisibility(View.VISIBLE);
                }
                // when index is not equal to the size of the list with videos, get the right video and thumbnail
                // start new activity and put different values through to the next activity
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

    // finish workout activity
    public void finishWorkout(){
        finish();
        finishWorkout.setEnabled(false);
    }


    public void setRectangles()
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
                chooseRectangle(i,fullDate);}
        }
    }

    public void chooseRectangle(int x , String text)
    {
        Button btn0 = findViewById(R.id.rectangle0);
        Button btn1 = findViewById(R.id.rectangle1);
        Button btn2 = findViewById(R.id.rectangle2);
        Button btn3 = findViewById(R.id.rectangle3);
        Button btn4 = findViewById(R.id.rectangle4);
        if(btn0.getText().equals("")){btn0.setText(text);}
        else if(btn1.getText().equals("")){btn1.setText(text);}
        else if(btn2.getText().equals("")){btn2.setText(text);}
        else if(btn3.getText().equals("")){btn3.setText(text);}
        else if(btn4.getText().equals("")){btn4.setText(text);}

    }


    public String translateDaysToDutch(String englishDay)
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

//    public int setMonth(String month)
//    {
//        int number = 0;
//        if(month.equals("Jan")){number = 1;}
//        if(month.equals("Feb")){number = 2;}
//        if(month.equals("Mar")){number = 3;}
//        if(month.equals("Apr")){number = 4;}
//        if(month.equals("May")){number = 5;}
//        if(month.equals("June")){number = 6;}
//        if(month.equals("July")){number = 7;}
//        if(month.equals("Aug")){number = 8;}
//        if(month.equals("Sept")){number = 9;}
//        if(month.equals("Oct")){number =10 ;}
//        if(month.equals("Nov")){number = 11;}
//        if(month.equals("Dec")){number = 12;}
//        return number;
//
//    }

    public void findViews(){
        btnStartWorkout = findViewById(R.id.btnStartWorkout);
        finishWorkout = findViewById(R.id.btnFinishWorkout);
        checkMark = findViewById(R.id.check_mark_);
        TextView warning = findViewById(R.id.warningView1);
        warning.setVisibility(View.INVISIBLE);


    }

    public ArrayList<Button> makeButtonsList()
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


    public void rectanglesAction(ArrayList<Button> buttons) {


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

    private void fillTimes (ArrayList<String> list)
    {
        list.add("09:00");
        list.add("20:00");
        list.add("15:00");
        list.add("11:00");
        list.add("10:00");

    }

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

    private int getCurrentHour()
    {
        Date currentTime = Calendar.getInstance().getTime();
        int x = Integer.parseInt(String.valueOf(currentTime).substring(11,13));
        System.out.println(x);
        return  x;
    }

    private String getCurrentDay()
    {
        Date currentTime = Calendar.getInstance().getTime();
        String day = String.valueOf(currentTime).substring(0,3);
        return day;
    }

    private boolean compareTime(int x , int y)
    {
        boolean time = false;
        if(x==y)
        {
            time =true;
        }
        return time;
    }

    private boolean compareDay(String x , String y)
    {
        boolean today = false;

        if(x.equals(y))
        {
            today = true;
        }
        return today;
    }

    private boolean isSelected(ArrayList<Button> buttons)
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