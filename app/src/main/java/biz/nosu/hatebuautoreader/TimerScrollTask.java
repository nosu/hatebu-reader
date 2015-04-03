package biz.nosu.hatebuautoreader;

import android.webkit.WebView;

import java.util.TimerTask;

/**
 * Created by inoue on 2015/04/03.
 */
public class TimerScrollTask extends TimerTask {
    private WebView mWebView;
    private int mSize;

    public TimerScrollTask(WebView view, int size) {
        this.mWebView = view;
        this.mSize = size;
    }

    @Override
    public void run() {
        mWebView.scrollBy(0, mSize);
    }

}
