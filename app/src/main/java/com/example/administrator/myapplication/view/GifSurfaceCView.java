package com.example.administrator.myapplication.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

/**
 * GifSurfaceCView
 * Created by zhuyingxin at 2016/3/10 15:29.
 * QQ: 657036139
 */
public class GifSurfaceCView extends SurfaceView implements SurfaceHolder.Callback {

    private Movie movie;
    private SurfaceHolder mSurfaceHolder;
    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.save();
            movie.setTime((int) (System.currentTimeMillis() % movie.duration()));
            movie.draw(canvas, 0, 0);
            canvas.restore();
            mSurfaceHolder.unlockCanvasAndPost(canvas);
            mHandler.postDelayed(runnable, 41);
        }
    };

    public GifSurfaceCView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        AssetManager assets = context.getResources().getAssets();
        InputStream inputStream;
        try {
            String[] list = assets.list("");

            inputStream = assets.open("acb.gif");
            movie = Movie.decodeStream(inputStream);
//            int width = movie.width();
//            int height = movie.height();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GifSurfaceCView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mHandler.post(runnable);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHandler.removeCallbacks(runnable);
    }
}
