package biz.nosu.hatebuautoreader;


import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by inoue on 2015/04/04.
 */
public class Article implements Serializable {
    private String url;
    private String title;
    private String description;
    private String count;
    private String faviconUrl;
    private Bitmap faviconImg;
    private String thumbUrl;
    private Bitmap thumbImg;

    private final int TITLE_SIZE = 42;
    private final int DESC_SIZE = 100;
    private final String FAVICON_API = "http://favicon.hatena.ne.jp/?url=";

    public Article() {
        super();
        this.url = "";
        this.title = "";
        this.description = "";
        this.count = "0";
        this.faviconUrl = null;
        this.faviconImg = null;
        this.thumbUrl = null;
        this.thumbImg = null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
//        this.faviconUrl = FAVICON_API + url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = trimText(title, TITLE_SIZE);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = trimText(description, DESC_SIZE);
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getFaviconUrl() {
        return faviconUrl;
    }

    public void setFaviconUrl(String faviconUrl) {
        this.faviconUrl = faviconUrl;
    }

    public Bitmap getFaviconImg() {
        return this.faviconImg;
    }

    public void setFaviconImg(Bitmap img) {
        this.faviconImg = img;
    }

    public Bitmap getThumbImg() {
        return this.thumbImg;
    }

    public void setThumbImg(Bitmap img) {
        this.thumbImg = img;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    private String trimText(String text, int size) {
        if(text.length() > size) {
            return text.substring(0, size) + "…";
        } else {
            return text;
        }
    }
}
