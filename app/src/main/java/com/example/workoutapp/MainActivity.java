package com.example.workoutapp;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.security.PrivateKey;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private EditText edtAge;
    private Button btnBeginner;
    private Button btnGevorderd;
    private Button btnMaster;
    private Spinner spnTime;
    private Spinner spnMonday;
    private Spinner spnTuesday;
    private Spinner spnWednesday;
    private Spinner spnThursday;
    private Spinner spnFriday;

    private TextView selectedMonday;
    private TextView selectedTuesday;
    private TextView selectedWednesday;
    private TextView selectedFriday;
    private TextView selectedThursday;



    // een methode die voegt alle spinners (van mijn account scherm) aan de lijst toe
    // wordt gebruikt voor het fixen van de bug dat alleen de eerste spinner werkt.
    //return een lijst met 5 spinners
    private ArrayList<Spinner> makeSpinnersList()
    {
        //spnTime = findViewById(R.id.spnMonday);
        spnMonday = findViewById(R.id.spnMonday);
        spnTuesday = findViewById(R.id.spnTuesday);
        spnWednesday = findViewById(R.id.spnWednesday);
        spnThursday = findViewById(R.id.spnThursday);
        spnFriday = findViewById(R.id.spnFriday);


        ArrayList<Spinner> spinners = new ArrayList<>();
        spinners.add(spnMonday);
        spinners.add(spnTuesday);
        spinners.add(spnWednesday);
        spinners.add(spnThursday);
        spinners.add(spnFriday);

        return spinners;
    }

    private ArrayList<TextView> makeTextViewsList()
    {
        selectedMonday = findViewById(R.id.selectedMonday);
        selectedTuesday = findViewById(R.id.selectedTuesday);
        selectedWednesday = findViewById(R.id.selectedWednsday);
        selectedThursday = findViewById(R.id.selectedThrusday);
        selectedFriday = findViewById(R.id.selectedFriday);

        ArrayList<TextView> textViews = new ArrayList<>();
        textViews.add(selectedMonday);
        textViews.add(selectedTuesday);
        textViews.add(selectedWednesday);
        textViews.add(selectedThursday);
        textViews.add(selectedFriday);

        return textViews;
    }

    // een methode dat voegt tijden aan timelist
    //wordt later gebruikt voor het maken van een spinner droptown item.
    //return timelist
    private ArrayList<String> makeTimeList()
    {
        ArrayList<String> timeList = new ArrayList<>();
        timeList.add("09:00");
        timeList.add("10:00");
        timeList.add("11:00");
        timeList.add("12:00");
        timeList.add("13:00");
        timeList.add("14:00");
        timeList.add("15:00");
        timeList.add("16:00");
        timeList.add("17:00");

        return timeList;
    }

    private void makeSpinnerDropdownItem(Spinner spinner)
    {
        ArrayAdapter<String> timeListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, makeTimeList());

            spnTime = spinner;
            spnTime.setAdapter(timeListAdapter);

            spnTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //System.out.println(spnTime.getSelectedItem().toString());
                    System.out.println(makeSpinnersList().indexOf(spnTime));
                    selectedMonday.setText(String.valueOf( spnMonday.getItemAtPosition(i)));


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(Spinner spinner : makeSpinnersList())
        {
            makeSpinnerDropdownItem(spinner);
        }


//        btnBeginner = findViewById(R.id.btnBeginner);
//        btnGevorderd = findViewById(R.id.btnGevorderd);
//        btnMaster = findViewById(R.id.btnMaster);










        // TODO: Nog alleen de eerste Spinner werkt; uitzoeken hoe we het voor allemaal kunnen laten werken
        // TODO: Bij het opstarten van de applicatie wordt de eerste waarde uitgeprint van de Spinner
        // TODO: Spinners hebben nog een soort van "hint" nodig om de eerste waarde (09:00) weer te geven
    }

    //Misschien ListView gebruiken ipv buttons

//    public void isClicked(View view){
//        switch (view.getId()){
//            case R.id.btnBeginner:
//                String niv1 = btnBeginner.getText().toString();
//                System.out.println(niv1);
//                break;
//            case R.id.btnGevorderd:
//                String niv2 = btnGevorderd.getText().toString();
//                System.out.println(niv2);
//                break;
//            case R.id.btnMaster:
//                String niv3 = btnMaster.getText().toString();
//                System.out.println(niv3);
//                break;
//            default:
//                break;
//        }
//    }

    // TODO: Werkt alleen op het moment dat de Button wordt ingedrukt (dus nog niet wanneer onClick uitgevoerd wordt);

    public void onClick(View view) {
        String age = edtAge.getText().toString();
        System.out.println(age);
    }
}


