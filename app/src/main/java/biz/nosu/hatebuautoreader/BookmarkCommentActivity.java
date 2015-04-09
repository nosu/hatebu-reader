package biz.nosu.hatebuautoreader;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class BookmarkCommentActivity extends ActionBarActivity {
    private Article curArticle;
    private BookmarkComment comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_comment);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        curArticle = (Article)bundle.getSerializable("CUR_ARTICLE");

        if(curArticle == null) {
            finish();
        }

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_bookmark_comment);
        setSupportActionBar(toolbarTop);
        getSupportActionBar().setTitle(curArticle.getTitle());
        getSupportActionBar().setSubtitle(curArticle.getUrl());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new EntryParserTask(this).execute(curArticle.getUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bookmark_comment, menu);
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
