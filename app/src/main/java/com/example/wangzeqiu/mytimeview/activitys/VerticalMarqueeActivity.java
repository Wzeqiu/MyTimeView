package com.example.wangzeqiu.mytimeview.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.wangzeqiu.mytimeview.R;
import com.example.wangzeqiu.mytimeview.views.MyVerticalMarqueeView;

import java.util.ArrayList;

/**
 * 垂直跑马灯
 */
public class VerticalMarqueeActivity extends AppCompatActivity implements MyVerticalMarqueeView.OnClickListener {
    private MyVerticalMarqueeView mMyVerticalMarqueeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_marquee);

        mMyVerticalMarqueeView = (MyVerticalMarqueeView) findViewById(R.id.myViews);


        ArrayList<String> mList = new ArrayList<>();
        mList.add("专业测试001");
        mList.add("专业测试002");
        mList.add("专业测试003");
        mList.add("专业测试004");
        mList.add("专业测试005");
        mMyVerticalMarqueeView.setData(mList).setOnClick(this).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyVerticalMarqueeView.Destroy();
    }

    @Override
    public void onClick(int positon, String str) {
        Toast.makeText(this, positon + "=======" + str, Toast.LENGTH_LONG).show();
    }
}
