package com.example.wangzeqiu.mytimeview.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.wangzeqiu.mytimeview.R;

/**
 * Created by WangZeQiu on 2017/2/15.
 */

public class CheckView extends View implements View.OnClickListener {
    Bitmap mBitmap;
    private Paint mPaint;
    private int intvater;

    public CheckView(Context context) {
        this(context, null);
    }

    public CheckView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    void init() {

        setOnClickListener(this);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.checkmark);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(0, 0, mBitmap.getWidth()*2 / 3, mPaint);
        Rect rect = new Rect(0, 0,intvater, mBitmap.getHeight());
        Rect rect1 = new Rect(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2,intvater-mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, rect, rect1, mPaint);
    }

    void start() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mBitmap.getWidth());
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                intvater = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    public void onClick(View v) {
        start();
    }
}