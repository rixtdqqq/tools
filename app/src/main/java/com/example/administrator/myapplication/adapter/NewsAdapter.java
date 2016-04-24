package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entites.NewsBean;
import com.example.administrator.myapplication.utils.ImageLoader;

import java.util.List;

/**
 * NewsAdapter
 * Created by zhuyingxin at 2016/1/14 21:15.
 * QQ: 657036139
 */
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{

    private List<NewsBean> mList;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private int mStart,mEnd;
    public static String[] URLS;
    /***判断是不是第一次进入*/
    private boolean isFirstIn;

    public NewsAdapter( Context context, List<NewsBean> mList, ListView listView) {
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader(listView);
        int size = mList.size();
        URLS = new String[size];
        for (int i = 0; i < size; i++) {
            URLS[i] = mList.get(i).getNewsIconUrl();
        }
        listView.setOnScrollListener(this);
        isFirstIn = true;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_layout,null);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewsBean newsBean = mList.get(position);
        String newsIconUrl = newsBean.getNewsIconUrl();
        holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        /**
         * 设置tag的目的是为了防止图片错乱
         */
        holder.ivIcon.setTag(newsIconUrl);
        /**使用多线程的方式*/
//        new ImageLoader().showImageByThread(holder.ivIcon, newsIconUrl);
        /**使用异步的方式*/
        mImageLoader.showImageByAsyncTask(holder.ivIcon, newsIconUrl);
        holder.tvTitle.setText(newsBean.getTitle());
        holder.tvContent.setText(newsBean.getContent());


        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE){
            //停止状态，加载可见项
            mImageLoader.loadImages(mStart,mEnd);
        }else {
            //滚动状态，取消加载
            mImageLoader.cancelAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        /**起到预加载的作用*/
        if (isFirstIn && visibleItemCount > 0){
            mImageLoader.loadImages(mStart,mEnd);
            isFirstIn = false;
        }
    }

    public class ViewHolder {
        public  TextView tvTitle;
        public  ImageView ivIcon;
        public  TextView tvContent;
    }
}
