package com.example.wangzeqiu.mytimeview.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by WangZeQiu on 2017/2/15.
 * 蜘蛛网数据展示
 */

public class Spider extends View {
    private int count = 6;          //边数

    private Paint mPaint;           //绘制正多边形
    private Paint mPaint1;          //绘制图形
    private Paint textPaint;        //绘制文字

    private int radius;             //图形半径
    private int animatorRadius;     //动画执行半径变化
    private int centerX;            //图形X轴中点
    private int centerY;            //图形Y轴中点
    private int spacing;            //每层间距

    private int DURATION_TIME = 5000;//动画执行时间


    private float radian = (float) (Math.PI * 2 / count);           //正多边形每个角对应的弧度
    private double datas[] = new double[]{};      //数据比例


    public Spider(Context context) {
        this(context, null);
    }

    public Spider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Spider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }


    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);


        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStrokeWidth(2);
        textPaint.setTextSize(30);

        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint1.setColor(Color.RED);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(widthMeasureSpec);
        radius = (int) (Math.min(width, height) / 2 * 0.9f);
        centerX = width >> 1;
        centerY = height >> 1;
        spacing = radius / (count - 1);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        positiveMultilateral(canvas);
        drawLin(canvas);
        drawText(canvas);
        drawGraphical(canvas);
    }


    /**
     * 绘制正多边形
     *
     * @param canvas
     */
    private void positiveMultilateral(Canvas canvas) {
        Path path = new Path();
        for (int i = 1; i < count; i++) {
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(centerX + spacing * i, centerY);
                }
                float x = (float) (centerX + spacing * i * Math.cos(radian * j));
                float y = (float) (centerY + spacing * i * Math.sin(radian * j));
                path.lineTo(x, y);
            }
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 绘制直线
     *
     * @param canvas
     */
    private void drawLin(Canvas canvas) {
        for (int i = 0; i < count; i++) {
            float x = (float) (centerX + spacing * (count - 1) * Math.cos(radian * i));
            float y = (float) (centerY + spacing * (count - 1) * Math.sin(radian * i));
            canvas.drawLine(centerX, centerY, x, y, mPaint);
        }
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        if (datas.length == 0) {
            return;
        }
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) (centerX + (spacing * (count - 1) + fontHeight/2) * Math.cos(radian * i));
            float y = (float) (centerY + (spacing * (count - 1) + fontHeight/2) * Math.sin(radian * i));
            if(radian*i>=0&&radian*i<=Math.PI/2){//第4象限
                canvas.drawText(String.valueOf(datas[i]), x,y,textPaint);
            }else if(radian*i>=3*Math.PI/2&& radian*i<=Math.PI*2){//第3象限
                canvas.drawText(String.valueOf(datas[i]), x,y,textPaint);
            }else if(radian*i>Math.PI/2&&radian*i<=Math.PI){//第2象限
                float dis = textPaint.measureText(String.valueOf(datas[i]));//文本长度
                canvas.drawText(String.valueOf(datas[i]), x-dis,y,textPaint);
            }else if(radian*i>=Math.PI&&radian*i<3*Math.PI/2){//第1象限
                float dis = textPaint.measureText(String.valueOf(datas[i]));//文本长度
                canvas.drawText(String.valueOf(datas[i]), x-dis,y,textPaint);
            }
        }

    }

    /**
     * 绘制图形
     *
     * @param canvas
     */
    private void drawGraphical(Canvas canvas) {
        if (datas.length == 0) {
            return;
        }
        Path path = new Path();
        mPaint1.setAlpha(255);
        float x;
        float y;
        for (int i = 0; i < count; i++) {
            if (animatorRadius > radius * datas[i]) {
                x = (float) (centerX + radius * datas[i] * Math.cos(radian * i));
                y = (float) (centerY + radius * datas[i] * Math.sin(radian * i));
            } else {
                x = (float) (centerX + animatorRadius * Math.cos(radian * i));
                y = (float) (centerY + animatorRadius * Math.sin(radian * i));
            }
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, 10, mPaint1);
        }
        path.close();
        mPaint1.setAlpha(128);
        canvas.drawPath(path, mPaint1);
    }


    /**
     * 动画
     */
    private void startAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, radius);
        valueAnimator.setDuration(DURATION_TIME);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorRadius = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }


    /**
     * 设置数据
     *
     * @param datas
     */
    public void setDatas(@NonNull double... datas) {
        count = datas.length;
        radian = (float) (Math.PI * 2 / count);
        spacing = radius / (count - 1);
        this.datas = datas;
        startAnimator();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setDatas(new double[]{0.8, 0.7, 1, 0.5, 0.1, 0.9});
                break;
        }
        return super.onTouchEvent(event);
    }
}
