package com.example.myfirstapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class titles extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titles);

        Intent intent = getIntent();
        String boardNAME = intent.getStringExtra("BoardNAME");
        String url1 = intent.getStringExtra("BoardUrl");;
        TextView textView = (TextView) findViewById(R.id.textView);

        //以下でリストにスレッドタイトル一覧を設定する
        try {
            new GetThreadTitles(this).execute(new URL(url1));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}