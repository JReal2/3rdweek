package com.example.project8_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button prev, next;
    MyPictureView view;
    int curNum = 0;
    File[] imageFiles;
    String imageFname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 이미지 뷰어");
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);
        prev = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        view = (MyPictureView) findViewById(R.id.image);

        imageFiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures").listFiles();
        imageFname = imageFiles[curNum].toString();
        view.imagePath = imageFname;

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curNum <= 0) {
                    Toast.makeText(getApplicationContext(), "첫번째 그림입니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    curNum--;
                    imageFname = imageFiles[curNum].toString();
                    view.imagePath=imageFname;
                    view.invalidate();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curNum >= imageFiles.length-2) {
                    Toast.makeText(getApplicationContext(), "마지막 그림입니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    curNum++;
                    imageFname=imageFiles[curNum].toString();
                    view.imagePath=imageFname;
                    view.invalidate();
                }
            }
        });
    }
}