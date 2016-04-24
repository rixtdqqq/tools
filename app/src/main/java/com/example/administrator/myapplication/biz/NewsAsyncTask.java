package com.example.administrator.myapplication.biz;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.administrator.myapplication.activity.FourActivity;
import com.example.administrator.myapplication.adapter.NewsAdapter;
import com.example.administrator.myapplication.entites.NewsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * NewsAsyncTask
 * Created by zhuyingxin at 2016/1/14 19:57.
 * QQ: 657036139
 */
public class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

    private Context mContext;
    private ListView mListView;

    public NewsAsyncTask(Context context, ListView listView) {
        this.mContext = context;
        mListView = listView;
    }

    @Override
    protected List<NewsBean> doInBackground(String... params) {

        return getJsonData(params[0]);//参数只有一个，就是请求的网址
    }

    @Override
    protected void onPostExecute(List<NewsBean> newsBeans) {
        super.onPostExecute(newsBeans);
        NewsAdapter adapter = new NewsAdapter(mContext, newsBeans, mListView);
        mListView.setAdapter(adapter);
    }


    /**
     * 从网络上获取json数据,并解析
     *
     * @param url 数据地址
     * @return 集合
     */
    private List<NewsBean> getJsonData(String url) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray data = jsonObject.getJSONArray("data");
            try {
                int len = data.length();
                NewsBean newsBean;
                JSONObject object;
                for (int i = 0; i < len; i++) {
                    object = data.getJSONObject(i);
                    newsBean = new NewsBean();
                    newsBean.setNewsIconUrl(object.getString("picSmall"));
                    newsBean.setTitle(object.getString("name"));
                    newsBean.setContent(object.getString("description"));
                    newsBeanList.add(newsBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }

    private String readStream(InputStream is) {
        InputStreamReader isr = null;
        String result = "";
        BufferedReader br = null;
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;

    }
}
