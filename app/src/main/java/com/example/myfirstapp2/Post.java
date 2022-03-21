package com.example.myfirstapp2;

import org.jsoup.nodes.Element;

public class Post {
    int position;
    String number;
    String name;
    String date;
    String uid;
    String message;
    Post(Element Data, int Position){
        this.position = Position;
        this.number = Data.select(".number").text();
        this.name = Data.select(".name").text();
        this.date = Data.select(".date").text();
        this.uid = Data.select(".uid").text();
        this.message = Data.select(".message").text();
    }
}