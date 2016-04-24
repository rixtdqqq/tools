package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View contentView = getLayoutInflater().inflate(R.layout.contact_item,null);
        ListView pluginLV = (ListView) contentView.findViewById(R.id.gsv);
        PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getDrawable(R.drawable.bjbj));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        List<Map<String, String>> pluginList = findPluginList();

    }



    private List<Map<String,String>> findPluginList(){
        List<Map<String,String>> pluginList = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(packageManager.GET_ACTIVITIES);
        try {
            PackageInfo currentPackageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            for (PackageInfo packageInfo : packages){
                String packageName = packageInfo.packageName;
                String sharedUserId = packageInfo.sharedUserId;
                if (currentPackageInfo.packageName.equals(packageName)||TextUtils.isEmpty(sharedUserId)||!sharedUserId.equals(currentPackageInfo.sharedUserId)){
                    continue;
                }
                //获取应用程序的名称，就是我们想要的插件
                String label = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                Map<String,String> pluginMap = new HashMap<>();
                pluginMap.put("packageName",packageName);
                pluginMap.put("label",label);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return pluginList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");

    }

}
