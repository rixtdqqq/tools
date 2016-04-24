//package com.example.administrator.myapplication.activity;
//
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.database.Cursor;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.example.administrator.myapplication.R;
//import com.example.administrator.myapplication.base.BaseActivity;
//import com.example.administrator.myapplication.entites.MusicInfo;
//import com.example.administrator.myapplication.service.MusicService;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MusicActivity extends BaseActivity implements View.OnClickListener {
//    private ImageView play_pause_music, pre_music, next_music,reduce_vol,increase_vol;
//    private TextView music_author, time_progress, music_duration;
//    private SeekBar music_progress;
//    private int index = 0;
//    private List<MusicInfo> musicInfos = new ArrayList<>();
//    private MusicService musicService;
//    private MyServiceConnection connection;
//    private MediaPlayer mediaPlayer;
//    private Handler mHandler = new Handler();
//    private Runnable music_run = new Runnable() {
//        @Override
//        public void run() {
//            music_progress.setProgress(mediaPlayer.getCurrentPosition());
//            time_progress.setText(millisecond2String(mediaPlayer.getCurrentPosition()));
//            mHandler.postDelayed(music_run,1000);
//        }
//    };
//    private AudioManager audioManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_music);
//        initViews();
//        initMusics();
//        PlayMusic();
//mHandler.removeCallbacks(music_run);
//    }
//
//    private void PlayMusic() {
//        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//        Intent intent = new Intent();
//        connection = new MyServiceConnection();
//        //启动服务 依据是service的生命周期
//        startService(intent);
//        bindService(intent, connection, Context.BIND_AUTO_CREATE);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.play_pause_music:
//                if (mediaPlayer.isPlaying()) {
//                    musicService.pause();
//
//                }else {
//                    musicService.continuePlay();
//                }
//
//                break;
//            case R.id.pre_music:
//                playMusic(1);
//                break;
//            case R.id.next_music:
//                playMusic(2);
//                break;
//            case R.id.increase_vol:
//                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
//
//                break;
//            default:
//                break;
//        }
//    }
//
//    class MyServiceConnection implements ServiceConnection {
//        /**
//         * 连接成功
//         */
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            MusicService.MyBinder binder = (MusicService.MyBinder) service;
//            musicService = binder.getService();
//            mediaPlayer = musicService.player;
//            if (!mediaPlayer.isPlaying()) {
//                playMusic(0);
//                play_pause_music.setImageResource(R.mipmap.ic_launcher);
//            } else {
//                play_pause_music.setImageResource(R.mipmap.ic_launcher);
//            }
//        }
//
//
//
//        /**
//         * 连接失败
//         */
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    }
//
//    private void playMusic(int status) {
//        switch (status) {
//            case 0://启动播放
//
//                break;
//            case 1://上一首
//                index--;
//                if (index < 0) {
//                    index = musicInfos.size() - 1;
//                }
//                break;
//            case 2://下一首
//                index++;
//                if (index > musicInfos.size() - 1) {
//                    index = 0;
//                }
//                break;
//            case 3://点击播放
//                break;
//            default:
//                break;
//        }
//        if (mediaPlayer != null) {
//            if (musicInfos.size() > 0) {
//                musicService.play(musicInfos.get(index).getFpath());
//                music_author.setText(musicInfos.get(index).getFname());
//                music_duration.setText(musicInfos.get(index).getDuration());
//                music_progress.setMax(mediaPlayer.);
//            }
//        }
//    }
//
//    /**
//     * 获取手机上的音乐文件
//     **/
//    private void initMusics() {
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        MusicInfo info;
//        while (cursor.moveToNext()) {
//            info = new MusicInfo();
//            String fname = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
//            String fpath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
//            //毫秒值
//            int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
//            info.setFname(fname);
//            info.setFpath(fpath);
//            info.setDuration(millisecond2String(duration));
//            /***排除损坏的音乐文件**/
//            if (fpath != null && fname != null && duration != 0) {
//                musicInfos.add(info);
//            }
//        }
//    }
//
//    /****
//     * 初始化控件
//     */
//    private void initViews() {
//        play_pause_music = (ImageView) findViewById(R.id.play_pause_music);
//        play_pause_music.setOnClickListener(this);
//        pre_music = (ImageView) findViewById(R.id.pre_music);
//        pre_music.setOnClickListener(this);
//        next_music = (ImageView) findViewById(R.id.next_music);
//        next_music.setOnClickListener(this);
//        music_author = (TextView) findViewById(R.id.music_author);
//        time_progress = (TextView) findViewById(R.id.time_progress);
//        music_duration = (TextView) findViewById(R.id.music_duration);
//        music_progress = (SeekBar) findViewById(R.id.music_progress);
//        music_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (fromUser){
//                    if (mediaPlayer.isPlaying()){
//                        mediaPlayer.seekTo(music_progress.getProgress());
//                    }
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        reduce_vol= (ImageView) findViewById(R.id.reduce_vol);
//        increase_vol= (ImageView) findViewById(R.id.increase_vol);
//    }
//
//    /***
//     * 将秒转为String类型
//     **/
//    private String millisecond2String(int duration) {
//        int seconds = duration / 1000;
//        int second = seconds % 60;
//        int minutes = (seconds - second) / 60;
//        DecimalFormat df = new DecimalFormat("00");
//        return df.format(minutes) + ":" + df.format(second);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (connection != null) {
//            unbindService(connection);
//        }
//    }
//}
