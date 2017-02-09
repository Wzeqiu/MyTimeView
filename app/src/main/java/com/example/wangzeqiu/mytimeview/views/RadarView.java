package com.example.wangzeqiu.mytimeview.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by WangZeQiu on 2017/2/9
 * 雷达.
 */

public class RadarView extends View {
    private Paint mPaint;           //画圆环和线条
    private Paint mPaint1;          //扫描

    private SweepGradient mSweepGradient;   //扫描
    private int rotate;
    private Matrix mMatrix;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLACK);

        mPaint1 = new Paint();
        mPaint1.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint1.setAntiAlias(true);
        mPaint1.setStrokeWidth(10);

        mMatrix = new Matrix();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 100, mPaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 200, mPaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 300, mPaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 400, mPaint);

        canvas.drawLine(getWidth() / 2 - 400, getHeight() / 2, getWidth() / 2 + 400, getHeight() / 2, mPaint);
        canvas.drawLine(getWidth() / 2, getHeight() / 2 - 400, getWidth() / 2, getHeight() / 2 + 400, mPaint);

        if (mSweepGradient == null) {
            mSweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2, Color.TRANSPARENT, Color.RED);
        }
        mPaint1.setShader(mSweepGradient);
        mMatrix.setRotate(rotate, getWidth() / 2, getHeight() / 2);
        canvas.concat(mMatrix);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 400, mPaint1);


        ValueAnimator animator = ValueAnimator.ofInt(0, 359);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotate = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        if (isFirst) {
            animator.start();
            isFirst = false;
        }

    }

    boolean isFirst = true;
}
