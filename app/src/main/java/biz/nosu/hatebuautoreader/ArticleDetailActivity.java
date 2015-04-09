package biz.nosu.hatebuautoreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import java.io.IOException;
import java.util.ArrayList;


public class ArticleDetailActivity extends ActionBarActivity {
    private ArrayList<Article> articles;
    private Article curArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if(extras == null) { // If don't come from MainActivity
            SharedPreferences mPrefs = getSharedPreferences("TEMP", Context.MODE_PRIVATE);
            try {
                articles = (ArrayList<Article>) ObjectSerializer.deserialize(mPrefs.getString("ARTICLES", ObjectSerializer.serialize(new ArrayList<Article>())));
                curArticle = (Article) ObjectSerializer.deserialize(mPrefs.getString("CUR_ARTICLE", ObjectSerializer.serialize(new Article())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { // If come from MainActivity
            articles = (ArrayList<Article>)extras.getSerializable("ARTICLES");
            curArticle = articles.get(0);
        }

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_article_detail_top);
        setSupportActionBar(toolbarTop);
        getSupportActionBar().setTitle(curArticle.getTitle());
        getSupportActionBar().setSubtitle(curArticle.getUrl());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView articleWebView = (WebView)findViewById(R.id.articleWebView);
        articleWebView.setWebViewClient(new MyWebViewClient());

        articleWebView.loadUrl(curArticle.getUrl());
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the article list to preference
        SharedPreferences prefs = getSharedPreferences("TEMP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("ARTICLES", ObjectSerializer.serialize(articles));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();

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
        getMenuInflater().inflate(R.menu.menu_article_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
        case R.id.action_bookmark_comment:
            Intent intent = new Intent(getApplicationContext(), BookmarkCommentActivity.class);
            intent.putExtra("CUR_ARTICLE", curArticle);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
