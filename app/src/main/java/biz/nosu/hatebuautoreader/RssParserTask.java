package biz.nosu.hatebuautoreader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toolbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by inoue on 2015/04/01.
 */
public class RssParserTask extends AsyncTask<URL, Integer, ArrayList<Article>> {
    private Activity activity;
    private ArticleListAdapter adapter;
    private ListView listView;
    private Toolbar toolbar;

    public RssParserTask(Activity mainActivity) {
        this.activity = mainActivity;
    }

    protected ArrayList<Article> doInBackground(URL... urls) {
        ArrayList<Article> itemList = new ArrayList<Article>();
        Log.i("MainView", "AsyncTask Start");
        try {
            InputStream is = urls[0].openConnection().getInputStream();
            itemList = parseXml(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }

    protected void onPostExecute(ArrayList<Article> articles) {
        adapter = new ArticleListAdapter(activity.getApplicationContext(), 0, articles);
        listView = (ListView)activity.findViewById(R.id.articleListView);
        listView.setAdapter(adapter);
    }

    public ArrayList<Article> parseXml(InputStream is) throws IOException, XmlPullParserException {
        ArrayList<Article> tempArticles = new ArrayList<Article>();
        Log.i("MainView", "ParseStart");

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            Article curArticle = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag = null;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")) {
                            curArticle = new Article();
                        } else if (curArticle != null) {
                            if (tag.equals("title")) {
                                curArticle.setTitle(parser.nextText());
                            } else if (tag.equals("link")) {
                                curArticle.setUrl(parser.nextText());
                            } else if (tag.equals("description")) {
                                curArticle.setDescription(parser.nextText());
                            } else if (tag.equals("encoded")) {
                                String cdata = parser.nextText();
//                                String trimmedCdata = cdata.substring(9, cdata.length() - 3);
                                String trimmedCdata = cdata;
                                Log.i("MainView", trimmedCdata);
                                ArrayList<String> urls = parseCdata(cdata);
                                switch (urls.size()) {
                                    case 0:
                                        Log.i("MainView", "RSS doesn't contain a url");
                                        curArticle.setFaviconUrl(null);
                                        curArticle.setThumbUrl(null);
                                        break;
                                    case 1:
                                        curArticle.setFaviconUrl(urls.get(0));
                                        curArticle.setThumbUrl(null);
                                        break;
                                    case 2:
                                        curArticle.setFaviconUrl(urls.get(0));
                                        curArticle.setThumbUrl(urls.get(1));
                                        break;
                                }
                            } else if (tag.equals("bookmarkcount")) {
                                curArticle.setCount(parser.nextText());
                            } else {
                                Log.i("MainView", "UnknownTag: " + tag);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")) {
                            tempArticles.add(curArticle);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("MainView", "Parse End");
        return tempArticles;
    }

    public ArrayList<String> parseCdata(String cdata) throws IOException, XmlPullParserException {
        ArrayList<String> result = new ArrayList<String>();
        XmlPullParser parser = Xml.newPullParser();
        int imgCount = 0;
        boolean endFlag = false;
        try {
            parser.setInput(new StringReader(cdata));
            int eventType = parser.getEventType();

            while (endFlag == false && imgCount != 2) {
                String tag = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tag.equals("img")) {
                            String imgUrl = parser.getAttributeValue(null, "src");
                            if(isValidPicture(imgUrl)) {
                                result.add(imgCount, imgUrl);
                            }
                            imgCount++;
                        } else {
                            Log.i("MainView", tag);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("blockquote")) {
                            endFlag = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("MainView", "ImgParseEnd");
        return result;
    }

    public boolean isValidPicture(String imageUrl) {
        String regex = "http://b.hatena.ne.jp/entry/image/";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(imageUrl);
        if(m.find()) {
            return false;
        } else {
            return true;
        }
    }
}
