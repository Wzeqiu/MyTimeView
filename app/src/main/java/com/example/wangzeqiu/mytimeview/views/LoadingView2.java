package com.example.wangzeqiu.mytimeview.views;

import android.animation.Animator;
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
 * @date: 2017/11/2 17:58
 * @description
 */

public class LoadingView2 extends View {
    private Paint mPaint;
    private ValueAnimator animator;
    private int mCenterX, mCenterY;
    private int RADIUS = 100;
    private RectF oval;
    private int angle = 0;
    private int sweep = 1;
    private int defateSweep = 0;

    public LoadingView2(Context context) {
        this(context, null);
    }

    public LoadingView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
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
        canvas.drawColor(Color.GRAY);
        oval = new RectF(mCenterX - RADIUS, mCenterY - RADIUS,
                mCenterX + RADIUS, mCenterY + RADIUS);
        canvas.drawArc(oval, angle, sweep, false, mPaint);
        canvas.drawArc(oval, angle - 180, sweep, false, mPaint);
    }

    int number = 0;

    void animation() {
        animator = ValueAnimator.ofInt(-179, 0);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {


                number++;
                ValueAnimator animator1;
                if (sweep == 140) {
                    animator1 = ValueAnimator.ofInt(140, 1);
                } else {
                    animator1 = ValueAnimator.ofInt(1, 140);
                }
                animator1.setDuration(200);
                animator1.setInterpolator(new LinearInterpolator());
                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        sweep = (int) valueAnimator.getAnimatedValue();
                    }
                });
                animator1.start();
//                if (number % 4 == 0) {
//                    ValueAnimator animator2;
//                    animator2 = ValueAnimator.ofInt(RADIUS, -RADIUS);
//                    animator2.setDuration(800);
//                    animator2.setInterpolator(new LinearInterpolator());
//                    animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                            int radius = (int) valueAnimator.getAnimatedValue();
//                            if (radius <= 0) {
//                                RADIUS = -radius;
//                            } else {
//                                RADIUS = radius;
//                            }
//                        }
//                    });
//                    animator2.start();
//                }
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                angle = (int) valueAnimator.getAnimatedValue();
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
                break;
        }
        return super.onTouchEvent(event);
    }
}
