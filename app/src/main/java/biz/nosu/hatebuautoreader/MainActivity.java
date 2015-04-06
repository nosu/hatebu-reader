package biz.nosu.hatebuautoreader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static Context appContext;
    private static final String RSS_URL = "http://b.hatena.ne.jp/hotentry.rss";
    private ArrayList<Article> articles;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = getApplicationContext();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        listView = (ListView)findViewById(R.id.articleListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ArticleDetailActivity.class);
                int end_position = listView.getAdapter().getCount();
                ArrayList<Article> articles_to_pass = new ArrayList<Article>();
                for(int cur_position = position; cur_position < end_position; cur_position++) {
                    articles_to_pass.add((Article) listView.getItemAtPosition(position));
                }
                intent.putExtra("ARTICLES", articles_to_pass);
                startActivity(intent);
            }
        });

        URL url = null;
        try {
            url = new URL(RSS_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new RssParserTask(this).execute(url);

        Log.i("MainView", "getArticles() End");

//        this.articles = new ArrayList<Article>();
//        for(int i = 0; i < 20; i++) {
//            articles.add(new Article("https://google.com/search?q=" + String.valueOf(i), String.valueOf(i) + "つ目のタイトルだよ！", "本文ですよーー！本文ですよーー！本文ですよーー！", "10"));
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Context getAppContext() {
        return appContext;
    }

}
