package biz.nosu.hatebuautoreader;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class BookmarkCommentActivity extends ActionBarActivity {
    private final String ENTRY_API = "http://b.hatena.ne.jp/entry/jsonlite/?url=";
    private Activity mActivity;
    private Article curArticle;
    private ArrayList<BookmarkComment> mComments;
    private BookmarkCommentListAdapter mAdapter;
    private ListView mListView;

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

        mComments = new ArrayList<BookmarkComment>();
        mActivity = this;

        Ion.with(MainActivity.getAppContext())
                .load(ENTRY_API + curArticle.getUrl())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonArray bookmarks = result.get("bookmarks").getAsJsonArray();
                        for(int i = 0; i < bookmarks.size(); i++) {
                            JsonObject jsonBookmark = bookmarks.get(i).getAsJsonObject();

                            // Get Timestamp
                            String ts = jsonBookmark.get("timestamp").getAsString();
                            Log.i("MainView", "timestamp: " + ts);
                            Calendar timestamp = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            try {
                                timestamp.setTime(sdf.parse(ts));
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                            // Get Comment
                            String comment = jsonBookmark.get("comment").getAsString();
                            Log.i("MainView", "comment: " + comment);

                            // Get Username
                            String user = jsonBookmark.get("user").getAsString();

                            // Get Tags
                            JsonArray jsonTags = jsonBookmark.get("tags").getAsJsonArray();
                            ArrayList<String> tags = new ArrayList<String>();
                            for(int j = 0; j < jsonTags.size(); j++) {
                                tags.add(jsonTags.get(j).getAsString());
                            }

                            if(!comment.isEmpty()) {
                                BookmarkComment bookmarkComment = new BookmarkComment(timestamp, comment, user, tags);
                                mComments.add(bookmarkComment);
                            } else {
                                Log.i("MainView", "comment is empty");
                            }
                            mAdapter = new BookmarkCommentListAdapter(MainActivity.getAppContext(), 0, mComments);
                            mListView = (ListView)mActivity.findViewById(R.id.commentListView);
                            mListView.setAdapter(mAdapter);
                        }
                    }
                });

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
