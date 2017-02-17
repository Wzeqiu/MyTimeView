package com.example.wangzeqiu.mytimeview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZeQiu on 2017/2/15.
 * 饼状图
 */

public class Cake extends View {
    private Paint mPaint;
    // 颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private int width;
    private int height;
    private List<Integer> mIntegers = new ArrayList<>();

    public Cake(Context context) {
        this(context, null);
    }

    public Cake(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Cake(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mIntegers.add(36);
        mIntegers.add(72);
        mIntegers.add(120);
        mIntegers.add(108);
        mIntegers.add(24);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    int sweepAngle = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float r = (float) (Math.min(width, height) / 2 * 0.8);  // 饼状图半径
        canvas.translate(width / 2, height / 2);
        RectF rect = new RectF(-r, -r, r, r);
        for (int i = 0; i < mIntegers.size(); i++) {
            mPaint.setColor(mColors[i]);
            canvas.drawArc(rect, sweepAngle, mIntegers.get(i), true, mPaint);
            sweepAngle += mIntegers.get(i);
        }
    }
}
