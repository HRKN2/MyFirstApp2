package com.example.myfirstapp2;
import android.app.Activity;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public final class AsyncHttpRequest extends AsyncTask<URL, Void, Elements> {
    //フィールドと変数の設定
    private Activity mainActivity;
    String url1;


    //コンストラクタの設定
    public AsyncHttpRequest(Activity activity) {
        this.mainActivity = activity;
    }


    //まずここが実行される
    @Override
    protected Elements doInBackground(URL... urls) {
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
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.err.println("正常に接続できていません。statusCode:" + statusCode);
                return null;
            }

            // Jsoupで対象URLの情報を取得する。
            Document doc = Jsoup.connect(url1).get();
            Elements elm = doc.select("[id=trad] a");
            Element elm2 = elm.get(4);
            String title = elm2.text();
            return  elm;

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
    protected void onPostExecute(Elements result) {
        //TextView tv = mainActivity.findViewById(R.id.messageTextView);
        //tv.setText(result);
    }
}