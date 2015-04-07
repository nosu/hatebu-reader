package biz.nosu.hatebuautoreader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static Context appContext;
    private static final String RSS_URL = "http://b.hatena.ne.jp/hotentry.rss";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = getApplicationContext();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(R.string.app_name);

        listView = (ListView)findViewById(R.id.articleListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ArticleDetailActivity.class);
                int end_position = listView.getAdapter().getCount();
                ArrayList<Article> articlesToRead = new ArrayList<Article>();
                for(int cur_position = position; cur_position < end_position; cur_position++) {
                    articlesToRead.add((Article) listView.getItemAtPosition(position));
                }
                intent.putExtra("ARTICLES", articlesToRead);
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

        switch (id) {
        case R.id.action_settings:
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Context getAppContext() {
        return appContext;
    }

}
