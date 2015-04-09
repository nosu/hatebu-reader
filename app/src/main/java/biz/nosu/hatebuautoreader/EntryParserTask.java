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

        Ion.with(MainActivity.getAppContext())
                .load(ENTRY_API + urls[0])
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
                        }
                    }
                });
        Log.i("MainView", "mComments: " + mComments.toString());
        return mComments;
    }

    protected void onPostExecute(ArrayList<BookmarkComment> comments) {
        mAdapter = new BookmarkCommentListAdapter(mActivity.getApplicationContext(), 0, comments);
        mListView = (ListView)mActivity.findViewById(R.id.commentListView);
        mListView.setAdapter(mAdapter);
    }
}
