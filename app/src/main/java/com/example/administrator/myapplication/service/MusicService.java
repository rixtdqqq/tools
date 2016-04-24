package com.example.administrator.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

/**
 * MusicService 音乐后台播放服务类
 * Created by zhuyingxin at 2016/2/26 21:33.
 * QQ: 657036139
 */
public class MusicService extends Service {

    MyBinder binder = new MyBinder();
    MediaPlayer player = new MediaPlayer();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * 播放
     */
    public void play(String fpath) {
        player.reset();
        try {
            //根据路径加载文件
            player.setDataSource(fpath);
            //缓存
            player.prepare();
            //是否缓存成功-----播放
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/**暂停*/
    public void pause(){
        if (player.isPlaying()){
            player.pause();
        }
    }
    /**继续播放*/
    public void continuePlay(){
        if (!player.isPlaying()){

        }
    }


    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
