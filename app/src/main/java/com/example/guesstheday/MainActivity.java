package com.example.guesstheday;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity  {
    public  static RadioButton radioButton,radioButton2,radioButton3,radioButton4,radioButton5;
    public static RadioGroup radioGroup;
    public static TextView textView,textView5;
    public static String day;
    public static Button button;
    public  static int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        radioGroup = findViewById(R.id.radioGroup);
        textView = findViewById(R.id.textView);
        textView5 = findViewById(R.id.textView5);
        button = findViewById(R.id.button);
        code();
    }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public  void code()
        {
        String [] options={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        try
        {
            date();
        }
        catch(Exception e)
        {
            Toast.makeText(this, "parsing failed", Toast.LENGTH_SHORT).show();
        }
        shuffleArray(options);
        String [] option= {options[0],options[1],options[2],options[3]};
        if (!((day==option[0])||(day==option[1])||(day==option[2])||(day==option[3])))
        {
            option[0]=day;
            shuffleArray(option);
        }
        radioButton.setText(option[0]);
        radioButton2.setText(option[1]);
        radioButton3.setText(option[2]);
        radioButton4.setText(option[3]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int radioId=radioGroup.getCheckedRadioButtonId();
                    radioButton5 = findViewById(radioId);
                    if (radioGroup.getCheckedRadioButtonId() == -1)
                        Toast.makeText(MainActivity.this, "Please choose an option", Toast.LENGTH_SHORT).show();
                    else
                        checkbutton(radioButton5);
                }
            });
        }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void shuffleArray(String[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    public static void date() throws ParseException
    {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1900,2100);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        textView.setText(gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = dateFormat.parse(gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH));
        calendar.setTime(d);
        String[] days = new String[] {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        day = days[calendar.get(Calendar.DAY_OF_WEEK)-1];
    }

    public static int randBetween(int start, int end)
    {
        return start + (int)Math.round(Math.random() * (end - start));
    }
    public void checkbutton(View v)
    {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton5 = findViewById(radioId);
        if (radioButton5.getText().toString() == day)
        {
            score++;
            code();
            textView5.setText("Current Score is "+score);
            radioGroup.clearCheck();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Chosen option is wrong", Toast.LENGTH_SHORT).show();
            radioGroup.clearCheck();
            finishAffinity();
            Intent i=new Intent (MainActivity.this,MainActivity2.class);
            startActivity(i);
        }
    }
}