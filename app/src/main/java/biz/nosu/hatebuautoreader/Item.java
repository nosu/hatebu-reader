package biz.nosu.hatebuautoreader;

/**
 * Created by inoue on 2015/04/02.
 */
public class Item {
    private String mUrl;
    private String mTitle;

    public Item() {
        mUrl = "";
        mTitle = "";
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }
}
