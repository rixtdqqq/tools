package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entites.NewsBean;
import com.example.administrator.myapplication.utils.BitmapUtil;

import java.util.List;

/**
 * news2Adapter
 * Created by zhuyingxin at 2016/1/16 2:47.
 * QQ: 657036139
 */
public class News2Adapter extends BaseAdapter implements AbsListView.OnScrollListener{

    private List<NewsBean> mData;
    private LayoutInflater mInflater;
    private BitmapUtil util;
    private int mStart,mEnd;
    public static String[] URLS;
    private boolean isFirstIn;

    public News2Adapter(Context context, List<NewsBean> list, ListView listView) {
        mData = list;
        mInflater = LayoutInflater.from(context);
        util = new BitmapUtil(listView);
        int size = list.size();
        URLS = new String[size];
        for (int i=0;i<size;i++){
            URLS[i] = list.get(i).getNewsIconUrl();
        }
        isFirstIn = true;
        listView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        NewsBean newsBean = mData.get(position);
        if (null == convertView){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_layout,null);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String iconUrl = newsBean.getNewsIconUrl();
        holder.ivIcon.setTag(iconUrl);
        util.showImageByAsyncTask(iconUrl,holder.ivIcon);
        holder.tvTitle.setText(newsBean.getTitle());
        holder.tvContent.setText(newsBean.getContent());
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE){
            util.loadImages(mStart, mEnd);
        }else {
            util.cancelAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem+visibleItemCount;
        if (isFirstIn && visibleItemCount > 0){
            util.loadImages(mStart,mEnd);
            isFirstIn = false;
        }
    }

    class ViewHolder{
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView ivIcon;
    }

}
