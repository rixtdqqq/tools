package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.myapplication.R;

/***
 * 流式布局：实现思路是1.继承ViewGroup,布局文件xml，尺寸（wrap_content,match_parent、固定值）
 * onMeasure
 * 布局onLayout
 * 1）测量模式：AT_MOST（至多，最多给分配多少的尺寸）、EXACTLY(精确值对应match_content,固定值)、UNSPECIFIED
 * （AdapterView【GridView ListView】）使用适配的这些View里面使用
 */
public class FlowLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flow_layout, menu);
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
