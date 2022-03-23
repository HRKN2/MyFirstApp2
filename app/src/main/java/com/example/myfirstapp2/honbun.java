package com.example.myfirstapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class honbun extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honbun);

        Intent intent = getIntent();
        String threadURL = intent.getStringExtra("threadURL");
        TextView textView = findViewById(R.id.testMessage);
        threadURL = threadURL.replace("/l50","");
        textView.setText(threadURL);
        ListView listView = findViewById(R.id.Posts);
        listView.setOnItemClickListener(this);
        //以下でそのスレッドの内容を取得し、画面に反映する
        try {
            new GetHonbun(this).execute(new URL(threadURL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //ここにリストビューをクリックしたときの動作を記述する
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //アンカーが含まれていたらToastを表示する
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }
}