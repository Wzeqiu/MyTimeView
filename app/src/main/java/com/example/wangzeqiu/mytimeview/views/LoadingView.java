package com.example.wangzeqiu.mytimeview.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @company: 爱华盈通
 * @author: Wangzeqiu
 * @date: 2017/9/15 13:55
 * @description 空白页面加载动画
 */

public class LoadingView extends View {
    // 圆的数量
    private static final int number = 5;
    // 圆之间的间隔
    private static final int interval = 10;
    // 圆的最大半径
    private static final int radius = 30;
    // 动画的时间
    private static final long durationTime = 600;
    private int value = radius;
    private int height;
    private int width;
    private Paint mPaint;
    private List<ValueAnimator> mValueAnimators;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.height = h;
        this.width = w;
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mValueAnimators = new ArrayList<>();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
    }

    /**
     * 绘制圆
     */
    private void drawCircle(Canvas canvas) {
        if (number % 2 == 0) { // 偶数
            for (int i = 0; i < number; i++) {
                ValueAnimator valueAnimator = null;
                if (mValueAnimators.size() > i) {
                    valueAnimator = mValueAnimators.get(i);
                }
                if (valueAnimator == null) {
                    value = radius;
                } else {
                    value = (int) valueAnimator.getAnimatedValue();
                }
                canvas.drawCircle(width / 2 - ((number - 1) / 2f - i) * (radius * 2 + interval), height / 2, value, mPaint);
            }
        } else { // 奇数
            for (int i = 0; i < number; i++) {
                ValueAnimator valueAnimator = null;
                if (mValueAnimators.size() > i) {
                    valueAnimator = mValueAnimators.get(i);
                }
                if (valueAnimator == null) {
                    value = radius;
                } else {
                    value = (int) valueAnimator.getAnimatedValue();
                }
                canvas.drawCircle(width / 2 - (number / 2 - i) * (radius * 2 + interval), height / 2, value, mPaint);
            }
        }
    }

    private void animation() {
        ValueAnimator animator = ValueAnimator.ofInt(radius, 0);
        animator.setDuration(durationTime);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });
        mValueAnimators.add(animator);
        animator.start();
    }

    private Runnable addRunnable = new Runnable() {
        @Override
        public void run() {
            animation();
            if (mValueAnimators.size() == number) {
                removeCallbacks(addRunnable);
            } else {
                postDelayed(addRunnable, durationTime / number);
            }
        }
    };

    @Override
    public void onWindowSystemUiVisibilityChanged(int visible) {
        super.onWindowSystemUiVisibilityChanged(visible);
        if (visible == View.VISIBLE) {
            //可见
            addRunnable.run();
        } else if (visible == INVISIBLE || visible == GONE) {
            //不可见
            Iterator<ValueAnimator> iterator = mValueAnimators.iterator();
            while (iterator.hasNext()) {
                ValueAnimator valueAnimator = iterator.next();
                valueAnimator.cancel();
                iterator.remove();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                addRunnable.run();
                break;
        }
        return super.onTouchEvent(event);
    }
}
