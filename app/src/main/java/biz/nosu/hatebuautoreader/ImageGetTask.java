package biz.nosu.hatebuautoreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by inoue on 2015/04/05.
 */
class ImageGetTask extends AsyncTask<String,Void,Bitmap> {
    private ImageView imageView;
    private String tag;

    public ImageGetTask(ImageView _imageView) {
        imageView = _imageView;
//        tag = imageView.getTag().toString();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String imageUrl = params[0];
        URL url = null;
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
        Context appContext = MainActivity.getAppContext();
        Bitmap bitmap = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.favicon_dummy);

        try {
            is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;

    }

    @Override
    protected void onPostExecute(Bitmap result) {
          imageView.setImageBitmap(result);
//        if (tag.equals(imageView.getTag())) {
//            imageView.setImageBitmap(result);
//        }
    }
}
