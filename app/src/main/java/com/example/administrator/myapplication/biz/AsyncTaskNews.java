package com.example.administrator.myapplication.biz;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.administrator.myapplication.adapter.News2Adapter;
import com.example.administrator.myapplication.entites.NewsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * AsyncTaskNews
 * Created by zhuyingxin at 2016/1/16 2:39.
 * QQ: 657036139
 */
public class AsyncTaskNews extends AsyncTask<String,Void,List<NewsBean>> {
    private Context mContext;
    private ListView mListView;
    public AsyncTaskNews(Context context, ListView listView) {
        mContext = context;
        mListView = listView;
    }

    @Override
    protected List<NewsBean> doInBackground(String... params) {
        return getJsonDataFromURL(params[0]);
    }

    private List<NewsBean> getJsonDataFromURL(String urlString) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        try {
            URL url= new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String jsonString = readStream(connection.getInputStream());
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray data = jsonObject.getJSONArray("data");
                NewsBean newsBean;
                for (int i  = 0; i<data.length();i++){
                    newsBean = new NewsBean();
                    JSONObject object = data.getJSONObject(i);
                    newsBean.setContent(object.getString("description"));
                    newsBean.setTitle(object.getString("name"));
                    newsBean.setNewsIconUrl(object.getString("picSmall"));
                    newsBeanList.add(newsBean);
                }
                connection.disconnect();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }

    private String readStream(InputStream inputStream) throws IOException {
        String result = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        String line ;
        while ((line = br.readLine())!=null){
            result += line;
        }
        br.close();
        return result;
    }

    @Override
    protected void onPostExecute(List<NewsBean> newsBeans) {
        super.onPostExecute(newsBeans);
        News2Adapter adapter = new News2Adapter(mContext,newsBeans,mListView);
        mListView.setAdapter(adapter);
    }
}
