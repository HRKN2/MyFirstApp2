package com.example.myfirstapp2;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public final class GetThreadTitles extends AsyncTask<URL, Void, Elements> {
    //フィールドと変数の設定
    private Activity mainActivity;
    String url1;
    String boardURL;
    String threadURL;
    //コンストラクタの設定
    public GetThreadTitles(Activity activity) {
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
        if(result == null)return;
        //ここにResultを使ってtitlesビューのスレタイ一覧を更新する処理を書く
        TextView tv = mainActivity.findViewById(R.id.textView);
        tv.setText(result.get(1).toString());
        ArrayList<String> titlenames = new ArrayList();

        int resultSize = result.size();
        for(int i=0;i<resultSize;i++){
            titlenames.add(result.get(i).text());
        }
        // ここで描画に追加してるはず
        ArrayAdapter adapter =
                new ArrayAdapter<>(mainActivity, android.R.layout.simple_list_item_1, titlenames);
        // ListViewにArrayAdapterを設定する
        ListView listView = mainActivity.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //ここでListViewに機能をもたせたい
        //~~ここにListViewを押したら次のアクティビティに飛ぶ処理を書く

        //1回だけ押されたときの処理を以下に書く
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ここに処理を書く
                boardURL = "https://krsw.5ch.net/test/read.cgi/steam/";
                threadURL = boardURL + result.get(position).attr("href").toString();
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(threadURL));//応急処置でブラウザで開く
                Intent intent = new Intent(mainActivity, honbun.class); // こっちが本来の用途で別のアクで開く
                intent.putExtra("threadURL", threadURL);
                mainActivity.startActivity(intent);
            }
        });

    }
}