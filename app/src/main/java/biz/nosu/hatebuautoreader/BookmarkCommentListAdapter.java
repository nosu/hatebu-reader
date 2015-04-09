package biz.nosu.hatebuautoreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by inoue on 2015/04/07.
 */
public class BookmarkCommentListAdapter extends ArrayAdapter<BookmarkComment> {
    private LayoutInflater inflater;
    private ArrayList<BookmarkComment> bookmarkComments;
    public ImageLoader imageLoader;

    public BookmarkCommentListAdapter(Context context, int resource, ArrayList<BookmarkComment> bookmarkComments) {
        super(context, resource, bookmarkComments);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bookmarkComments = bookmarkComments;
    }

    private static class ViewHolder {
        public ImageView hatenaIconImageView;
        public TextView commentTextView;
        public TextView timestampTextView;

        public ViewHolder(View view) {
            super();
            this.hatenaIconImageView = (ImageView) view.findViewById(R.id.hatenaIcon);
            this.commentTextView = (TextView) view.findViewById(R.id.comment_text);
            this.timestampTextView = (TextView) view.findViewById(R.id.comment_timestamp);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comment_row, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BookmarkComment bookmarkComment = getItem(position);

//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .build();
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MainActivity.getAppContext())
//                .defaultDisplayImageOptions(defaultOptions)
//                .build();
//        ImageLoader.getInstance().init(config);

        // Set Hatena Icon
        if(bookmarkComment.getUser() == null) {
            Context appContext = MainActivity.getAppContext();
            Bitmap bitmap = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.favicon_dummy);
            holder.hatenaIconImageView.setImageBitmap(bitmap);
        } else {
            String userName = bookmarkComment.getUser();
            String iconUrl =
                    "http://cdn1.www.st-hatena.com/users/"
                    + userName.substring(0,2)
                    + "/" + userName
                    + "/profile.gif";
            ImageLoader.getInstance().displayImage(iconUrl, holder.hatenaIconImageView);
        }

        StyleSpan boldStyleSpan = new StyleSpan(Typeface.BOLD);
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(bookmarkComment.getUser());
        sb.setSpan(boldStyleSpan, 0, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.append(bookmarkComment.getComment());
        holder.commentTextView.setText(sb);

        Calendar cal = bookmarkComment.getTimestamp();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timestamp = sdf.format(cal.getTime());
        holder.timestampTextView.setText(timestamp);

        return convertView;
    }

}
