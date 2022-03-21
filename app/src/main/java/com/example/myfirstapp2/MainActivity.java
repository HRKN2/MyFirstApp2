package com.example.myfirstapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public Map<String,String> map = new HashMap<>();

    @SuppressLint("WrongThread")
    private static final String[] texts = {
            "Steam",
            "2",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMap();
        ListView listView = new ListView(this);
        listView.setOnItemClickListener(this);
        setContentView(listView);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, R.layout.list, texts);
        listView.setAdapter(arrayAdapter);
    }
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Intent intent = new Intent(this, titles.class);
        String selectedText = texts[position];
        String url = map.get(selectedText);

        //ここで次のアクティビティに名前とURLを渡す URLだけ渡しても良いんじゃね実は
        intent.putExtra("BoardNAME", selectedText);
        intent.putExtra("BoardUrl",url);
        //次のアクティビティに飛ぶ
        startActivity(intent);
    }
    public void initMap(){
        map.put("Steam","https://krsw.5ch.net/steam/subback.html");
    }
}