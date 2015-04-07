package biz.nosu.hatebuautoreader;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import java.util.ArrayList;


public class ArticleDetailActivity extends ActionBarActivity {
    private Article curArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        ArrayList<Article> articles = (ArrayList<Article>)extras.getSerializable("ARTICLES");

        curArticle = articles.get(0);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_article_detail);
        toolbarTop.setTitle(curArticle.getTitle());
        toolbarTop.setSubtitle(curArticle.getUrl());
        setSupportActionBar(toolbarTop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView articleWebView = (WebView)findViewById(R.id.articleWebView);
        articleWebView.setWebViewClient(new MyWebViewClient());

        articleWebView.loadUrl(curArticle.getUrl());
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
