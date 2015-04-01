package biz.nosu.hatebuautoreader;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by inoue on 2015/04/01.
 */
public class RssParserTask extends AsyncTask<URL, Integer, Long> {
    protected Long doInBackground(URL... urls) {
        String result = new String[];
        try {
            // HTTP経由でアクセスし、InputStreamを取得する
            URL url = new URL(urls[0]);
            InputStream is = url.openConnection().getInputStream();
            result = parseXml(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ここで返した値は、onPostExecuteメソッドの引数として渡される
        return result;
    }

    public String[] parseXml(InputStream is) {

    }
}
