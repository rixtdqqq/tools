package com.example.administrator.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.BaseActivity;
import com.example.administrator.myapplication.entites.ContactBean;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.List;

public class AlphaNavActivity extends BaseActivity {

    private ListView lvContact;
    private EditText etAlphaSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_nav);

        initViews();
        initData();
    }

    private void initData() {
        String[] stringArray = getResources().getStringArray(R.array.myFriend);
        List<ContactBean> cbs = new ArrayList<>();
        ContactBean cb ;
        for (int i=0;i<stringArray.length;i++){
            String name = stringArray[i];
            char c = name.charAt(0);
            String[] strings = PinyinHelper.toHanyuPinyinStringArray(c);
            String alphaStr = strings[0].toUpperCase();
            char alpha = alphaStr.charAt(0);
            cb = new ContactBean(name,alpha);
            cbs.add(cb);
        }
    }

    private void initViews() {
        lvContact = (ListView) findViewById(R.id.listView_contact);
        etAlphaSearch = (EditText) findViewById(R.id.alpha_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alpha_nav, menu);
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
