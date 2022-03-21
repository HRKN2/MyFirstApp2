package com.example.myfirstapp2;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public final class GetHonbun extends AsyncTask<URL, Void, Document> {
    //フィールドと変数の設定
    private Activity mainActivity;
    String url1;

    String tmp = "";
    //コンストラクタの設定
    public GetHonbun(Activity activity) {
        this.mainActivity = activity;
    }

    //まずここが実行される
    @Override
    protected Document doInBackground(URL... urls) {

        final URL url = urls[0];
        HttpURLConnection con = null;
        url1 = url.toString();
        try {
            //httpコネクトの設定
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setInstanceFollowRedirects(false);
            con.connect();
            final int statusCode = con.getResponseCode();
            /*
            if (statusCode != HttpURLConnection.HTTP_OK) {
                tmp = "正常に接続できていません。statusCode:" + statusCode;
                System.err.println(tmp);
                return null;
            }
             */

            // Jsoupで対象URLの情報を取得する。
            Document doc = Jsoup.connect(url1).get();
            //Elements elm = doc.select(".html");
            //Element elm2 = elm.get(4);
            //String title = elm2.text();
            return  doc;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }


    //doInBackgroundが終わったあとにこれが呼ばれる
    @Override
    protected void onPostExecute(Document result) {
        TextView tv = mainActivity.findViewById(R.id.testMessage);
        //tv.setMovementMethod(new ScrollingMovementMethod());
        if(result ==null){
            tv.setText(tmp);
            return;
        }
        //tv.setText(result.select(".post").text());

        //書き込み一覧を取得しPostsという配列に入れる
        Elements Posts = result.select(".post");
        int PostsSize = Posts.size();
        ArrayList PostsArray = new ArrayList();
        for(int i=0;i<PostsSize;i++){
            Post tmp = new Post(Posts.get(i),i);
            PostsArray.add(tmp);
        }
        //tv.setText(Posts.get(5).text()); //テスト用

        // MyAdapterを作成し、データを設定
        MyAdapter adapter = new MyAdapter(mainActivity);
        adapter.setList(PostsArray);

        // ListViewにadapterを設定する
        ListView listView = (ListView) mainActivity.findViewById(R.id.Posts);
        listView.setAdapter(adapter);
    }
}