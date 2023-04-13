package com.example.project8_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker date;
    EditText diary;
    Button button;
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 일기장");

        date=(DatePicker) findViewById(R.id.date);
        diary=(EditText) findViewById(R.id.diary);
        button=(Button) findViewById(R.id.button);

        Calendar cal = Calendar.getInstance();
        int cyear = cal.get(Calendar.YEAR);
        int cmonth = cal.get(Calendar.MONTH);
        int cday = cal.get(Calendar.DAY_OF_MONTH);

        filename = Integer.toString(cyear)+"_"+Integer.toString(cmonth+1)+"_"+Integer.toString(cday)+".txt";
        start(filename);


        date.init(cyear, cmonth, cday, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                filename = Integer.toString(year)+"_"+Integer.toString(monthOfYear+1)+"_"+Integer.toString(dayOfMonth)+".txt";
                String str = readDiary(filename);
                diary.setText(str);
                button.setEnabled(true);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream outFs = openFileOutput(filename, Context.MODE_PRIVATE);
                    String str = diary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(), filename+" 이 저장됨", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {

                }
            }
        });
    }

    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            button.setText("수정하기");
        } catch(IOException e) {
            diary.setHint("일기 없음");
            button.setText("새로 저장");
        }
        return diaryStr;
    }

    void start(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            diary.setText(diaryStr);
            button.setText("수정하기");
            button.setEnabled(true);
        } catch(IOException e) {
            diary.setHint("일기 없음");
            button.setText("새로 저장");
        }
    }
}