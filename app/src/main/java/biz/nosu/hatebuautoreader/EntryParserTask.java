package biz.nosu.hatebuautoreader;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by inoue on 2015/04/01.
 */
public class EntryParserTask extends AsyncTask<String, Integer, ArrayList<BookmarkComment>> {
    private final String ENTRY_API = "http://b.hatena.ne.jp/entry/jsonlite/?url=";
    private Activity mActivity;
    private BookmarkCommentListAdapter mAdapter;
    private ListView mListView;
    private ArrayList<BookmarkComment> mComments;

    public EntryParserTask(Activity bookmarkCommentActivity) {
        mActivity = bookmarkCommentActivity;
        mComments = new ArrayList<BookmarkComment>();
    }

    protected ArrayList<BookmarkComment> doInBackground(String... urls) {
        ArrayList<BookmarkComment> bookmarkComments = new ArrayList<BookmarkComment>();
        Log.i("MainView", "EntryParser Start with URL: " + urls[0]);

        return mComments;
    }

    protected void onPostExecute(ArrayList<BookmarkComment> comments) {
        mAdapter = new BookmarkCommentListAdapter(mActivity.getApplicationContext(), 0, comments);
        mListView = (ListView)mActivity.findViewById(R.id.commentListView);
        mListView.setAdapter(mAdapter);
    }
}
