package biz.nosu.hatebuautoreader;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by inoue on 2015/03/31.
 */
public class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        try {
            autoScroll(view, 1000, 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void autoScroll(WebView view, long interval, int size) throws InterruptedException {
        int contentHeight = view.getContentHeight();
        int curY = view.getScrollY();
//        while(size < contentHeight - curY) {
            view.scrollBy(0, size);
//        }
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
