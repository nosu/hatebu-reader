package biz.nosu.hatebuautoreader;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by inoue on 2015/03/31.
 */
public class MyWebViewClient extends WebViewClient {
    public Timer timer;

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        timer = new Timer();
        TimerTask timerTask = new TimerScrollTask(view, 30);
        timer.scheduleAtFixedRate(timerTask, 10, 200);
    }

    public void timer

}
