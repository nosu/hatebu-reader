package biz.nosu.hatebuautoreader;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ArticleDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        ArrayList<Article> articles = (ArrayList<Article>)extras.getSerializable("ARTICLES");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_article_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(articles.get(0).getTitle());

        WebView articleWebView = (WebView)findViewById(R.id.articleWebView);
        articleWebView.setWebViewClient(new MyWebViewClient());

        articleWebView.loadUrl(articles.get(0).getUrl());
    }

//    public void sequentialReader(ArrayList<Article> articles, MyWebView view) {
//        view.scrollLoadUrl(new onReachEndListener() {
//
//
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_reader, menu);
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
}
