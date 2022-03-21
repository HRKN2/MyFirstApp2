package com.example.myfirstapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

public class honbun extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honbun);

        Intent intent = getIntent();
        String threadURL = intent.getStringExtra("threadURL");
        TextView textView = findViewById(R.id.testMessage);
        textView.setText(threadURL);
        //以下でそのスレッドの内容を取得し、画面に反映する
        try {
            new GetHonbun(this).execute(new URL(threadURL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}