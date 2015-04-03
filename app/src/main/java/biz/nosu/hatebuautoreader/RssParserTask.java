package biz.nosu.hatebuautoreader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.webkit.WebView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by inoue on 2015/04/01.
 */
public class RssParserTask extends AsyncTask<URL, Integer, ArrayList<Item>> {
    private Activity mActivity;

    public RssParserTask(Activity mainActivity) {
        this.mActivity = mainActivity;
    }

    protected ArrayList<Item> doInBackground(URL... urls) {
        ArrayList<Item> itemList = new ArrayList<Item>();
        Log.i("MainView", "AsyncTask Start");
        try {
            // HTTP経由でアクセスし、InputStreamを取得する
            InputStream is = urls[0].openConnection().getInputStream();
            itemList = parseXml(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ここで返した値は、onPostExecuteメソッドの引数として渡される
        return itemList;
    }

    protected void onPostExecute(ArrayList<Item> itemList) {
        mActivity.setItemList(itemList);
        Log.i("MainView", "onPostExecute start");
        Item item = itemList.get(0);
        Log.i("MainView", "Open: " + item.getUrl());
        WebView view = (WebView)mActivity.findViewById(R.id.articleWebView);
        view.loadUrl(item.getUrl());
    }

    public ArrayList<Item> parseXml(InputStream is) throws IOException, XmlPullParserException {
        ArrayList<Item> itemList = new ArrayList<Item>();
        Log.i("MainView", "ParseStart");

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            Item currentItem = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag = null;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")) {
                            currentItem = new Item();
                        } else if (currentItem != null) {
                            if (tag.equals("title")) {
                                currentItem.setTitle(parser.nextText());
                            } else if (tag.equals("link")) {
                                currentItem.setUrl(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")) {
                            itemList.add(currentItem);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("MainView", "Parse End");
        return itemList;
    }
}
