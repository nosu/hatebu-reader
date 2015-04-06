package biz.nosu.hatebuautoreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by inoue on 2015/04/04.
 */
public class ArticleListAdapter extends ArrayAdapter<Article> {
    private LayoutInflater inflater;
    private ArrayList<Article> articles;
    public ImageLoader imageLoader;

    public ArticleListAdapter(Context context, int resource, ArrayList<Article> articles) {
        super(context, resource, articles);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.articles = articles;
    }

    private static class ViewHolder {
        public LinearLayout layout;
        public TextView titleTextView;
        public TextView countTextView;
        public TextView descriptionTextView;
        public ImageView faviconImageView;
        public ImageView thumbImageView;

        public ViewHolder(View view) {
            super();
            this.layout = (LinearLayout) view.findViewById(R.id.article_desc_and_thumb);
            this.titleTextView = (TextView) view.findViewById(R.id.article_title);
            this.countTextView = (TextView) view.findViewById(R.id.article_count);
            this.descriptionTextView = (TextView) view.findViewById(R.id.article_description);
            this.faviconImageView = (ImageView) view.findViewById(R.id.favicon);
            this.thumbImageView = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.article_row, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Article article = getItem(position);

        Log.i("MainView", "FaviconUrl: " + article.getFaviconUrl());
        Log.i("MainView", "ThumbUrl: " + article.getThumbUrl());

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MainActivity.getAppContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        // Set Favicon
        if(article.getFaviconUrl() == null) {
            Context appContext = MainActivity.getAppContext();
            Bitmap bitmap = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.favicon_dummy);
            holder.faviconImageView.setImageBitmap(bitmap);
        } else {
            ImageLoader.getInstance().displayImage(article.getFaviconUrl(), holder.faviconImageView);
//            Ion.with(holder.faviconImageView)
//                    .placeholder(R.drawable.favicon_dummy)
//                    .load(article.getFaviconUrl());
        }

        // Set Thumbnail Image
        if(article.getThumbUrl() == null) {
//            Context appContext = MainActivity.getAppContext();
//            Bitmap bitmap = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.favicon_dummy);
//            holder.thumbImageView.setImageBitmap(bitmap);
            holder.layout.removeView(holder.thumbImageView);
        } else {
            if(holder.layout.findViewById(R.id.thumbnail) == null) {
                holder.layout.addView(holder.thumbImageView);
            }
            ImageLoader.getInstance().displayImage(article.getThumbUrl(), holder.thumbImageView);
//            Ion.with(holder.thumbImageView)
//                    .placeholder(R.drawable.favicon_dummy)
//                    .load(article.getThumbUrl());
        }

        holder.titleTextView.setText(article.getTitle());
        SpannableString spannedCount = new SpannableString(article.getCount());
        spannedCount.setSpan(new UnderlineSpan(), 0, spannedCount.length(), 0);
        holder.countTextView.setText(article.getCount() + " users");
        holder.descriptionTextView.setText(article.getDescription());

//        Bitmap favicon = downloadImage(article.getFaviconUrl());
//        if(favicon == null) {
//            Context appContext = MainActivity.getAppContext();
//            Bitmap bitmap = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.favicon_dummy);
//        } else {
//            holder.faviconImageView.setImageBitmap(favicon);
//        }

//        Context appContext = MainActivity.getAppContext();
//        Bitmap bitmap = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.favicon_dummy);
//        holder.faviconImageView.setImageBitmap(bitmap);
//        ImageGetTask task = new ImageGetTask(holder.faviconImageView);
//        Log.i("MainView", "faviconURL: " + article.getFaviconUrl());
//        task.execute(article.getFaviconUrl());

        return convertView;
    }

    public Bitmap downloadImage(String url_string) {
        String imageUrl = url_string;
        URL url = null;
        Bitmap bitmap = null;

        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = null;

        try {
            is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
