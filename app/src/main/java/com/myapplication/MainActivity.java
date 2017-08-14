package com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import bindview.data.BindMode;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BindViewManager bindViewManager = new BindViewManager(MainActivity.this);  //初始化
                bindViewManager.setBindModeAndViewIds(BindMode.BIND_ALL);
                bindViewManager.bindContainerWithItem("fire_list", R.layout.listview_item);  //可选项目，传入ListView的Item的布局
                bindViewManager.bindData(new testData()); //绑定数据到View中。
            }
        });
    }
}
