package com.example.myfirstapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.text.HtmlCompat;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater = null;
    private ArrayList<Post> Posts;


    public MyAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }
    public void setList(ArrayList<Post> Posts) {
        this.Posts = Posts;
    }
    @Override
    public boolean isEnabled(int position) {

        return true;
    }
    @Override
    public int getCount() {
        return Posts.size();
    }
    @Override
    public Object getItem(int i) {
        return Posts.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @SuppressLint({"ClickableViewAccessibility", "ViewHolder"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.post, viewGroup, false);
        Post post = Posts.get(i);
        ((TextView)view.findViewById(R.id.post_number)).setText(post.number);
        ((TextView)view.findViewById(R.id.post_name)).setText(post.name);
        ((TextView)view.findViewById(R.id.post_date)).setText(post.date);
        ((TextView)view.findViewById(R.id.post_id)).setText(post.uid);

        TextView textView = ((TextView)view.findViewById(R.id.post_message));
        //textView.setText(Html.fromHtml(post.message));
        //textView.setText(Html.fromHtml(post.message));
        textView.setText(HtmlCompat.fromHtml(post.message, HtmlCompat.FROM_HTML_MODE_COMPACT));
        //textView.setMovementMethod(MutableLinkMovementMethod.getInstance());

        textView.setOnTouchListener(new ViewGroup.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                TextView textView = (TextView) view;
                //LinkMovementMethodを継承したもの 下記参照
                MutableLinkMovementMethod m = new MutableLinkMovementMethod();
                //MovementMethod m=LinkMovementMethod.getInstance();
                //リンクのチェックを行うため一時的にsetする
                textView.setMovementMethod(m);
                boolean mt = m.onTouchEvent(textView, (Spannable) textView.getText(), event);
                //チェックが終わったので解除する しないと親view(listview)に行けない
                textView.setMovementMethod(null);
                //setMovementMethodを呼ぶとフォーカスがtrueになるのでfalseにする
                textView.setFocusable(false);
                //戻り値がtrueの場合は今のviewで処理、falseの場合は親viewで処理
                return mt;
            }
        });

        return view;
    }
    //テキストービューの中のリンクに対してタップ時の動作を追加する
}