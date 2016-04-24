package com.example.administrator.myapplication.base;

import android.app.Activity;
import android.os.Bundle;

import com.example.administrator.myapplication.utils.InjectUtils;

/**
 * BaseActivity 基类
 * Created by zhuyingxin at 2016/2/23 21:05.
 * QQ: 657036139
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(BaseActivity.this);
    }
}
