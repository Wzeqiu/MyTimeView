package com.example.wangzeqiu.mytimeview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyVerticalMarqueeView.OnClickListener {


    private MyVerticalMarqueeView mMyVerticalMarqueeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyVerticalMarqueeView = (MyVerticalMarqueeView) findViewById(R.id.myViews);


        ArrayList<String> mList = new ArrayList<>();
        mList.add("专业测试001");
        mList.add("专业测试002");
        mList.add("专业测试003");
        mList.add("专业测试004");
        mList.add("专业测试005");
        mMyVerticalMarqueeView.setData(mList).setOnClick(this).start();


    }


    private int sp2px(Context context, int sp) {
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * density + 0.5f);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyVerticalMarqueeView.stop();
    }

    @Override
    public void onClick(int positon, String str) {
        Toast.makeText(this,positon+"======="+str,Toast.LENGTH_LONG).show();

    }
}
