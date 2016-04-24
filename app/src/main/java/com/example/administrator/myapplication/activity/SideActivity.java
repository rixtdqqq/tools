package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entites.ContactBean;
import com.example.administrator.myapplication.view.SideView;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SideActivity extends Activity implements SideView.OnTouchDownUpListener {
    private ListView mListView;
    private TextView tvAlpha;
    private SideView sideView;
    private EditText etSearch;
    private List<ContactBean> contactBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side);
        initViews();
        initData();
        setListener();

    }

    private void setListener() {
        sideView.setOnTouchDownUpListener(this);
    }

    private void initData() {
        contactBeans = new ArrayList<>();
        ContactBean contactBean;
        String name;
        char c;
        String[] stringArray = getResources().getStringArray(R.array.myFriend);
        for (int i = 0; i < stringArray.length; i++) {
            name = stringArray[i];
            char[] chars = name.toCharArray();
            String[] strings = PinyinHelper.toHanyuPinyinStringArray(chars[0]);
            String s = strings[0].toUpperCase();
            c = s.charAt(0);
            contactBean = new ContactBean(name, c);
            contactBeans.add(contactBean);
        }
        Collections.sort(contactBeans, new Comparator<ContactBean>() {
            @Override
            public int compare(ContactBean lhs, ContactBean rhs) {
                return String.valueOf(lhs.getAlpha()).compareTo(String.valueOf(rhs.getAlpha()));
            }
        });
        mListView.setAdapter(new MyListAdapter());
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.lv_contact);
        tvAlpha = (TextView) findViewById(R.id.tv_alpha);
        sideView = (SideView) findViewById(R.id.sv);
        etSearch = (EditText) findViewById(R.id.et_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_side, menu);
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

    @Override
    public void onActionDown(char c) {
        for (int i = 0; i < contactBeans.size(); i++) {
            if (c == contactBeans.get(i).getAlpha()) {
                mListView.setSelection(i);
                tvAlpha.setVisibility(View.VISIBLE);
                tvAlpha.setText(c + "");
            }
        }
    }

    @Override
    public void onActionUp() {

        tvAlpha.setVisibility(View.GONE);
    }

    class MyListAdapter extends BaseAdapter {

        private char pre_c = '~';

        @Override
        public int getCount() {
            return contactBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return contactBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.contact_item, null);
                holder.tvAlpha = (TextView) convertView.findViewById(R.id.tv_alpha);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ContactBean contactBean = contactBeans.get(position);
            char c = contactBean.getAlpha();
            holder.tvAlpha.setText(c + "");
            holder.tvName.setText(contactBean.getName());
            if (pre_c == '~' || pre_c != c) {
                holder.tvAlpha.setVisibility(View.VISIBLE);
            } else {
                holder.tvAlpha.setVisibility(View.GONE);
            }
            pre_c = c;
            return convertView;
        }

        class ViewHolder {
            TextView tvAlpha;
            TextView tvName;
        }
    }


}
