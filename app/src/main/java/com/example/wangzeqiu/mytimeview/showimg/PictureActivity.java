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

public class PictureActivity extends AppCompatActivity implements StatusListener {
    public static final String X = "x";
    public static final String Y = "y";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String POSITION = "position";
    public static final String COLUMN = "column";
    public static final String HORIZONTAL_INTERVAL = "horizontal_interval";
    public static final String VERTICAL_INTERVAL = "vertical_interval";
    public static final String LIST_DATE = "list_date";


    private ViewPager mViewPager;
    private ImagePagerAdapter mAdapter;


    public static void getInstance(Context context, ArrayList<String> listPath, int position, int x, int y, int
            width, int height, int column, int horizontal_interval, int vertical_interval) {
        Bundle bundle = new Bundle();
        bundle.putInt(X, x);
        bundle.putInt(Y, y);
        bundle.putInt(WIDTH, width);
        bundle.putInt(HEIGHT, height);
        bundle.putInt(POSITION, position);
        bundle.putInt(COLUMN, column);
        bundle.putInt(HORIZONTAL_INTERVAL, horizontal_interval);
        bundle.putInt(VERTICAL_INTERVAL, vertical_interval);
        bundle.putStringArrayList(LIST_DATE, listPath);
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
        int position = intent.getIntExtra(POSITION, 0);

        mAdapter = new ImagePagerAdapter(this, this, intent);
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
