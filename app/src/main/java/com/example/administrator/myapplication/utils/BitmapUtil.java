package com.example.administrator.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.News2Adapter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * BitmapUtil
 * Created by zhuyingxin at 2016/1/10 21:11.
 * QQ: 657036139
 */
public class BitmapUtil {
    /**
     * Lru 近期最少使用算法 Least Recently Used
     * android提供了LruCache这个类
     */
    private ListView mListView;
    private LruCache<String, Bitmap> mCache;
    private Set<DetailAsyncTask> mTasks;

    public BitmapUtil(ListView listView) {
        mListView = listView;
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int maxSize = maxMemory / 4;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        mTasks = new HashSet<>();
    }

    public void addBitmap2Cache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {
            mCache.put(url, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String url) {
        return mCache.get(url);
    }


    private Bitmap getBitmapFromURL(String urlString) {
        Bitmap bitmap = null;
        URL url;
        BufferedInputStream bis;
        try {
            url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bis = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(bis);
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public void showImageByAsyncTask(String url, ImageView imageView) {
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap == null) {
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    private class DetailAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String mUrl;

        public DetailAsyncTask(String url) {
            mUrl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFromURL(url);
            if (bitmap != null) {
                addBitmap2Cache(url, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            mTasks.remove(this);
        }
    }

    public void loadImages(int start, int end) {
        String url;
        Bitmap bitmap;
        DetailAsyncTask task;
        for (int i = start; i < end; i++) {
            url = News2Adapter.URLS[i];
            bitmap = getBitmapFromCache(url);
            if (bitmap == null) {
                task = new DetailAsyncTask(url);
                task.execute(url);
            } else {
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);

            }
        }
    }

    public void cancelAllTask() {
        if (mTasks != null) {
            for (DetailAsyncTask task : mTasks) {
                task.cancel(false);
            }
        }
    }

}
