package com.example.wangzeqiu.mytimeview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WangZeQiu on 2017/2/8.
 * 钟表
 */

public class MyTime extends View {

    Paint mPaint;

    public MyTime(Context context) {
        this(context, null);
    }

    public MyTime(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTime(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    void init() {

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 3, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawPoint(getWidth() / 2, getHeight() / 2, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        RectF rectF = new RectF(getWidth() / 6, getHeight() / 2 - getWidth() / 3, getWidth() * 5 / 6, getHeight() / 2 + getWidth() / 3);
        canvas.drawArc(rectF, 0, 360, true, mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5);
        //将坐标原点移到圆心处
        canvas.translate(getWidth() / 2, getHeight() / 2);
        for (int i = 0; i < 60; i++) {
            //这里刻度线长度我设置为25
            if (i % 5 == 0) {
                canvas.drawLine(getWidth() / 3 - 50, 0, getWidth() / 3, 0, mPaint);
            } else {
                canvas.drawLine(getWidth() / 3 - 25, 0, getWidth() / 3, 0, mPaint);
            }
            canvas.rotate(6);
        }

        mPaint.setTextSize(45);
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                drawNum(canvas, 0, "12", mPaint);
            } else {
                drawNum(canvas, i * 30, i + "", mPaint);
            }
        }
//秒针
        canvas.save();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
//其实坐标点（0,0）终点坐标（0，-190），这里的190为秒针长度
        canvas.rotate(mSecondDegree);
        canvas.drawLine(0, 0, 0,
                -getWidth() / 4, mPaint);
        canvas.restore();
//分针
        canvas.save();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        canvas.rotate(mMinDegree);
        canvas.drawLine(0, 0, 0,
                -getWidth() / 6, mPaint);
        canvas.restore();
//时针
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(7);
        canvas.rotate(mHourDegree);
        canvas.drawLine(0, 0, 0,
                -getWidth() / 9, mPaint);
        canvas.restore();


    }

    private void drawNum(Canvas canvas, int degree, String text, Paint paint) {
        Rect textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);
        canvas.rotate(degree);
        canvas.translate(0, 80 - getWidth() / 3);//这里的50是坐标中心距离时钟最外边框的距离，当然你可以根据需要适当调节
        canvas.rotate(-degree);
        canvas.drawText(text, -textBound.width() / 2,
                textBound.height() / 2, paint);
        canvas.rotate(degree);
        canvas.translate(0, getWidth() / 3 - 80);
        canvas.rotate(-degree);
    }

    private float mSecondDegree;//秒针的度数
    private float mMinDegree;
    private float mHourDegree;
    private Timer mTimer = new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {//具体的定时任务逻辑
            if (mSecondDegree == 360) {
                mSecondDegree = 0;
            }
            if (mMinDegree == 360) {
                mMinDegree = 0;
            }
            if (mHourDegree == 360) {
                mHourDegree = 0;
            }
            mSecondDegree = mSecondDegree + 6;//秒针
            mMinDegree = mMinDegree + 0.1f;//分针
            mHourDegree = mHourDegree + 1.0f / 120;//时针
            postInvalidate();
        }
    };

    /**
     * 开启定时器
     */
    public void start() {
        mTimer.schedule(task, 0, 10);
    }

    public void stop() {
        mTimer.cancel();
        task.cancel();
    }
}
