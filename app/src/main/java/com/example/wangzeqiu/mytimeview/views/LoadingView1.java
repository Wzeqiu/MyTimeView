package com.example.wangzeqiu.mytimeview.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @company: www.aiwinn.com
 * @author: Wangzeqiu
 * @date: 2017/10/23 18:33
 * @description
 */

public class LoadingView1 extends View {
    private Paint mPaint;
    private int mCenterX, mCenterY;
    private int radius = 150;
    private int radius1 = 180;
    private int radius2 = 210;
    private int radius3 = 220;
    private int angle1 = 90;
    private int angle = 90;

    public LoadingView1(Context context) {
        this(context, null);
    }

    public LoadingView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w >> 1;
        mCenterY = h >> 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Draw(canvas);
    }

    void Draw(Canvas canvas) {
        mPaint.setAlpha(255);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint.setStrokeWidth(25);
        canvas.drawCircle(mCenterX, mCenterY, radius, mPaint);

        mPaint.setStrokeWidth(3);
        canvas.drawCircle(mCenterX, mCenterY, radius1, mPaint);

        mPaint.setStrokeWidth(15);
        RectF oval = new RectF(mCenterX - radius2, mCenterY - radius2,
                mCenterX + radius2, mCenterY + radius2);

        for (int i = 1; i <= 20; i++) {
            mPaint.setColor(Color.BLUE);
            mPaint.setAlpha(255 / i);
            canvas.drawArc(oval, angle + 90, -i, false, mPaint);
            mPaint.setColor(Color.RED);
            mPaint.setAlpha(255 / i);
            canvas.drawArc(oval, -angle1 + 90, i, false, mPaint);
        }

    }


    void animation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 359);
        animator.setDuration(1200);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                angle = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    void animation1() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 359);
        animator.setDuration(1800);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                angle1 = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                animation();
                animation1();

                break;
        }
        return super.onTouchEvent(event);
    }
}
