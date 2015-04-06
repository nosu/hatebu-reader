package biz.nosu.hatebuautoreader;

import android.content.Context;
import android.webkit.WebView;

import java.util.TimerTask;

/**
 * Created by inoue on 2015/04/03.
 */
public class TimerScrollTask extends TimerTask {
    private WebView webView;
    private int size;
    private int yBeforeScroll;
    private int yAfterScroll;

    public TimerScrollTask(WebView view, int size) {
        this.webView = view;
        this.size = size;
    }

    @Override
    public void run() {
//        yBeforeScroll = webView.getScrollY();
        webView.scrollBy(0, size);
//        yAfterScroll = webView.getScrollY();
//        if(yBeforeScroll == yAfterScroll) {
//            webView.onReachEnd();
//        }
    }

}
