package com.example.wangzeqiu.mytimeview.views;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wangzeqiu.mytimeview.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HorizontalRollingView extends LinearLayout {
    private int SHOW_NUMBER = 6;
    private int durationTime = 1000;
    private List<Integer> showDatas = new LinkedList<>();

    public HorizontalRollingView(Context context) {
        this(context, null);
    }

    public HorizontalRollingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        showDatas.add(R.drawable.center);
        showDatas.add(R.drawable.center);
        showDatas.add(R.drawable.center);
        showDatas.add(R.drawable.center);
        showDatas.add(R.drawable.center);
        showDatas.add(R.drawable.center);
        showDatas.add(R.drawable.center);
        setData();
    }

    private void setData() {
        for (int i = 0; i < SHOW_NUMBER + 1; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels / SHOW_NUMBER, LayoutParams.MATCH_PARENT);
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(imageView, layoutParams);
        }
        startAnimator();
    }


    private void startAnimator() {
        PropertyValuesHolder holder = PropertyValuesHolder.ofInt("scrollX", 0, getResources().getDisplayMetrics().widthPixels / SHOW_NUMBER); //滚动的距离
        ValueAnimator animator = ValueAnimator.ofPropertyValuesHolder(holder);
        animator.setDuration(durationTime);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(Integer.MAX_VALUE - 1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setScrollX((Integer) animation.getAnimatedValue("scrollX"));
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                ImageView view = (ImageView) getChildAt(0);
                removeView(view);

                int data = showDatas.remove(0);
                showDatas.add(data);
                view.setImageResource(data);

                addView(view);
            }
        });
        animator.start();
    }

    public void add() {
        showDatas.add(0, R.drawable.checkmark);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("HorizontalRollingView", "onTouchEvent");
                add();
                break;
        }
        return super.onTouchEvent(event);
    }
}
