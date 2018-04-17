package com.example.wangzeqiu.mytimeview.views.editpicture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zeqiu.wang
 * @date 2018/4/16
 */
public class ProgressView extends View {
    private Paint mPaint;
    private int width;          // View 宽度
    private int height;         // View 高度
    private int allLength;      // 绘制的进度条在整个View的长度
    private int interval = 20;       // 进度条距离两边的距离
    private float progress = 1f;     // 进度百分比


    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(12);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        allLength = w - interval * 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawView(canvas);
    }

    private void drawView(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(interval, height >> 1, interval + allLength, height >> 1, mPaint);
        if (progress > 0) {
            mPaint.setColor(Color.RED);
            canvas.drawLine(interval, height >> 1, (interval + allLength) * progress, height >> 1, mPaint);
        }
    }

    public void setProgress(float pro) {
        this.progress = pro;
        invalidate();
    }

}
