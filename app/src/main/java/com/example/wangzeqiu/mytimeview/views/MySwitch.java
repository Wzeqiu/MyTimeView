package com.example.wangzeqiu.mytimeview.views;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Checkable;


/**
 * Created by WangZeQiu on 2017/2/10.
 */

public class MySwitch extends View implements Checkable {


    private Paint mPaint;           //绘制椭圆
    private Paint mPaint1;          //绘制阴影层
    private Paint mPaint2;          //绘制圆

    private int width;              //可展示内容的宽度
    private int height;             //可展示内容的高度
    private int fillet;             //四个圆角的半径
    private int radius;             //圆的半径
    private int spacing = 10;        //圆与外层椭圆的间距
    private int centerCircleX;      //圆的X轴坐标
    private int centerCircleY;      //圆的Y轴坐标
    private int alpha = 255;        //椭圆的透明度
    private int minAlpha = 96;      //阴影层最小的透明度
    private int maxAlpha = 200;     //阴影层最大的透明度
    private boolean isEnd = true;   //动画是否结束
    private boolean isChecked = true;  //选中状态
    private boolean isCancal = false;
    private int color = 0xFF228B22; //颜色
    private int DURATION_TIME = 5000;  //动画时间

    private OnCheckedChangeListener mChangeListener;
    private ValueAnimator animator;


    public MySwitch(Context context) {
        this(context, null);
    }

    public MySwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        init();
    }

    void init() {
        //椭圆画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(color);
        //阴影画笔
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint1.setStrokeWidth(1);
        mPaint1.setColor(Color.WHITE);
        //圆的画笔
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setStrokeWidth(1);
        mPaint2.setColor(Color.WHITE);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        fillet = height >> 1;
        radius = (height >> 1) - spacing;
        centerCircleX = centerCircleY = fillet;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制椭圆
        RectF rect = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rect, fillet, fillet, mPaint);

        //绘制阴影层
        mPaint1.setColor(Color.WHITE);
        mPaint1.setAlpha(255);
        RectF rect1 = new RectF(spacing, spacing, isChecked ? centerCircleX + fillet - spacing : centerCircleX == fillet ? width - spacing : centerCircleX + radius, height - spacing);
        canvas.drawRoundRect(rect1, fillet, fillet, mPaint1);
        mPaint1.setColor(color);
        mPaint1.setAlpha(isChecked ? alpha : centerCircleX == fillet ? minAlpha : alpha);
        canvas.drawRoundRect(rect1, fillet, fillet, mPaint1);
        //圆
        canvas.drawCircle(isChecked ? centerCircleX : centerCircleX == fillet ? width - fillet : centerCircleX, centerCircleY, radius, mPaint2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isEnd) {
                    startAnimator();
                    isEnd = false;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void startAnimator() {
        PropertyValuesHolder holder = PropertyValuesHolder.ofInt("scrollX", isChecked ? fillet : width - fillet, isChecked ? width - fillet : fillet);
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofInt("alpha", isChecked ? maxAlpha : minAlpha, isChecked ? minAlpha : maxAlpha);
        animator = ValueAnimator.ofPropertyValuesHolder(holder, holder1);
        animator.setDuration(DURATION_TIME);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int scrollX = (int) animation.getAnimatedValue("scrollX");
                int alpha1 = (int) animation.getAnimatedValue("alpha");
                alpha = alpha1;
                centerCircleX = scrollX;
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isEnd = true;
                if (!isCancal) {
                    isChecked = !isChecked;
                }
                isCancal = false;
                if (mChangeListener != null) {
                    mChangeListener.onCheckedChanged(isChecked);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isCancal = true;
                if (isChecked) {
                    centerCircleX = fillet;
                } else {
                    alpha=minAlpha;
                    centerCircleX = width - fillet;
                }
                invalidate();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void cancelAnimation() {
        if (animator != null) {
            animator.cancel();
        }
    }

    public void setColor(@ColorRes int color) {
        this.color = color;
    }


    public void setOnCheckedChangeWidgetListener(@Nullable OnCheckedChangeListener onCheckedChangeListener) {
        this.mChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isOpen);
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
        cancelAnimation();
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {

    }


}
