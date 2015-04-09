package biz.nosu.hatebuautoreader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by inoue on 2015/04/07.
 */
public class BookmarkComment {
    private Calendar mTimestamp;
    private String mComment;
    private String mUser;
    private ArrayList<String> mTags;

    public BookmarkComment(Calendar timestamp, String comment, String user, ArrayList<String> tags) {
        mTimestamp = timestamp;
        mComment = comment;
        mUser = user;
        mTags = tags;
    }

    public Calendar getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.mTimestamp = timestamp;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        this.mComment = comment;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        this.mUser = user;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

    public void setTags(ArrayList<String> tags) {
        this.mTags = tags;
    }

}
