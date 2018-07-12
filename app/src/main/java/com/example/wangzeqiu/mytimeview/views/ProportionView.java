package com.example.wangzeqiu.mytimeview.views;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.animation.ValueAnimatorCompat;
import android.util.AttributeSet;
import android.view.CollapsibleActionView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.wangzeqiu.mytimeview.R;

public class ProportionView extends View {
    private int r, r1;
    private int difference = 20;
    private int w1 = 20;
    private int w2 = w1 + difference;
    private Paint mPaint;
    private int[] colors = {Color.RED, Color.BLUE};
    private int[] colors1 = {Color.YELLOW, Color.RED};

    public ProportionView(Context context) {
        this(context, null);
    }

    public ProportionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProportionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        mPaint = new Paint();
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        r = w / 3;
        r1 = r + difference / 2;

        rectF = new RectF(-r, -r, r, r);
        rectF1 = new RectF(-r1, -r1, r1, r1);
    }

    private RectF rectF, rectF1;
    private SweepGradient sweepGradient = new SweepGradient(0, 0, colors, null);
    private SweepGradient sweepGradient1 = new SweepGradient(0, 0, colors1, null);

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth() / 2, getHeight() / 2);

        mPaint.setShader(sweepGradient);
        mPaint.setStrokeWidth(w1);
        canvas.drawArc(rectF, sweepAngle, 360, false, mPaint);

        mPaint.setShader(sweepGradient1);
        mPaint.setStrokeWidth(w2);
        canvas.drawArc(rectF1, 0, sweepAngle, false, mPaint);
    }

    int sweepAngle;

    public void data(float f) {
        ValueAnimator animator = ValueAnimator.ofInt(0, (int) (360 * f));
        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                data(0.6f);
                break;
        }

        return super.onTouchEvent(event);
    }
}
