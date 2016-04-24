package com.example.administrator.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ImageLoader2
 * Created by zhuyingxin at 2016/1/30 21:11.
 * QQ: 657036139
 * 开闭原则(OCP)：
 * 单一原则
 */
public class ImageLoader2 {

    private LruCache<String,Bitmap> mCache;
    private ExecutorService mExecuteService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ImageLoader2() {
        initImageCache();
    }

    private void initImageCache() {
        final int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int maxSize = maxMemory / 4;
        mCache = new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                /***图片多少KB*/
                return value.getRowBytes()*value.getHeight() / 1024;
            }
        };
    }

    public void displayImage(final Context context,final ImageView imageView,final String imageUrl){
        imageView.setTag(imageUrl);
        this.mExecuteService.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
    private Bitmap downLoadImage(String imageUrl){
        Bitmap bitmap = null;
        try{
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
