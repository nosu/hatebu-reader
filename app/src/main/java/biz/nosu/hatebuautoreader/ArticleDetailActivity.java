package biz.nosu.hatebuautoreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import java.io.IOException;
import java.util.ArrayList;


public class ArticleDetailActivity extends ActionBarActivity {
    private ArrayList<Article> mArticles;
    private Article mCurArticle;
    private SharedPreferences mPrefs;
    protected WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        getArticlesToSharedPreference();
        setToolbar();
        setWebView();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        getArticlesToSharedPreference();
//        setToolbar();
//        setWebView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setArticlesToSharedPreference();
    }

    public void setToolbar() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_article_detail_top);
        setSupportActionBar(toolbarTop);
        getSupportActionBar().setTitle(mCurArticle.getTitle());
        getSupportActionBar().setSubtitle(mCurArticle.getUrl());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setWebView() {
        mWebView = (WebView)findViewById(R.id.articleWebView);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(mCurArticle.getUrl());
    }

    public void getArticlesToSharedPreference() {
        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if(extras == null) { // If don't come from MainActivity
            mPrefs = getSharedPreferences("TEMP", Context.MODE_PRIVATE);
            try {
                mArticles = (ArrayList<Article>) ObjectSerializer.deserialize(mPrefs.getString("ARTICLES", ObjectSerializer.serialize(new ArrayList<Article>())));
                mCurArticle = (Article) ObjectSerializer.deserialize(mPrefs.getString("CUR_ARTICLE", ObjectSerializer.serialize(new Article())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { // If come from MainActivity
            mArticles = (ArrayList<Article>)extras.getSerializable("ARTICLES");
            mCurArticle = mArticles.get(0);
        }
    }

    public void setArticlesToSharedPreference() {
        // Save the article list to preference
        SharedPreferences prefs = getSharedPreferences("TEMP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("ARTICLES", ObjectSerializer.serialize(mArticles));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

//    public void sequentialReader(ArrayList<Article> mArticles, MyWebView view) {
//        view.scrollLoadUrl(new onReachEndListener() {
//
//
//        });
//    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        if (mWebView != null) {
//            // Remove the WebView from the old placeholder
//            webViewPlaceholder.removeView(mWebView);
//        }
//        super.onConfigurationChanged(newConfig);
//
//        // Load the layout resource for the new configuration
//        setContentView(R.layout.state_preserving_impl);
//
//        // Reinitialize the UI
//        initUI();
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state of the WebView
        mWebView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore the state of the WebView
        mWebView.restoreState(savedInstanceState);
    }

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
            intent.putExtra("CUR_ARTICLE", mCurArticle);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
