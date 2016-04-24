package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.BaseActivity;
import com.example.administrator.myapplication.utils.ContextView;
import com.example.administrator.myapplication.utils.ViewInject;

@ContextView(R.layout.activity_inject)
public class InjectActivity extends BaseActivity {
    @ViewInject(R.id.tv_inject_title)
    private TextView tv_inject_title;
    @ViewInject(R.id.btn_inject)
    private Button btn_inject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,"启动",Toast.LENGTH_LONG).show();
        btn_inject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InjectActivity.this,"启动成功",Toast.LENGTH_LONG).show();

            }
        });
    }


}
