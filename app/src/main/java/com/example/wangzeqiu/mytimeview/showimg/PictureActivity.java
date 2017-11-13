package com.example.wangzeqiu.mytimeview.showimg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.wangzeqiu.mytimeview.R;

import java.util.ArrayList;
import java.util.List;

public class PictureActivity extends AppCompatActivity implements StatusListener {
    private ViewPager mViewPager;
    private ImagePagerAdapter mAdapter;
    private List<String> listPath;


    public static void getInstance(Context context, ArrayList<String> listPath, int position, int x, int y, int
            width, int height, int column, int horizontal_interval, int vertical_interval) {
        Bundle bundle = new Bundle();
        bundle.putInt("x", x);
        bundle.putInt("y", y);
        bundle.putInt("width", width);
        bundle.putInt("height", height);
        bundle.putInt("position", position);
        bundle.putInt("column", column);
        bundle.putInt("horizontal_interval", horizontal_interval);
        bundle.putInt("vertical_interval", vertical_interval);
        bundle.putStringArrayList("listPath", listPath);
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_picture);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        Intent intent = getIntent();
        int x = intent.getIntExtra("x", 0);
        int y = intent.getIntExtra("y", 0);
        int width = intent.getIntExtra("width", 0);
        int height = intent.getIntExtra("height", 0);
        int position = intent.getIntExtra("position", 0);
        int column = intent.getIntExtra("column", 0);
        int horizontal_interval = intent.getIntExtra("horizontal_interval", 0);
        int vertical_interval = intent.getIntExtra("vertical_interval", 0);
        listPath = intent.getStringArrayListExtra("listPath");


        mAdapter = new ImagePagerAdapter(this, this, listPath, position, x, y, width, height, column,
                horizontal_interval, vertical_interval);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position);
        setBgAlphaAnimation();
    }


    @Override
    public void startShow() {

    }

    @Override
    public void show() {

    }

    @Override
    public void startDismiss() {
        mViewPager.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void dismiss() {
        finish();
    }

    /**
     * 查看大图背景透明度改变
     */
    public void setBgAlphaAnimation() {
        mViewPager.setBackgroundColor(Color.BLACK);
//        AlphaAnimation bgAlphaAnimation = new AlphaAnimation(0, (float) 1);
//        bgAlphaAnimation.setDuration(500);
//        bgAlphaAnimation.setFillAfter(true);
//        mViewPager.startAnimation(bgAlphaAnimation);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mViewPager.setBackgroundColor(Color.TRANSPARENT);
            mAdapter.endAnimator(mAdapter.getPrimaryItem());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
