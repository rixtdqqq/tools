package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.NewsAdapter;
import com.example.administrator.myapplication.biz.NewsAsyncTask;
import com.example.administrator.myapplication.config.AsynConfig;
import com.example.administrator.myapplication.entites.NewsBean;

import java.util.List;


/***
 * http://www.imooc.com/api/teacher?type=4&num=30
 * ListView滑动停止后才加载可见项，滑动时取消所有加载项
 * */
public class FourActivity extends Activity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        mListView = (ListView) findViewById(R.id.lv_main);
        NewsAsyncTask nat= new NewsAsyncTask(this,mListView);
        nat.execute(AsynConfig.URL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_four, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
