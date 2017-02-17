package com.example.wangzeqiu.mytimeview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by WangZeQiu on 2017/2/15.
 * 蜘蛛网数据展示
 */

public class Spider extends View {
    private Paint mPaint;

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

    void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawPoint(0, 0, mPaint);
        drawStar(canvas, mPaint, Color.BLACK, 200, 6, false);
        drawStar(canvas, mPaint, Color.BLACK, 300, 6, false);
        drawStar(canvas, mPaint, Color.BLACK, 400, 6, false);
        drawStar(canvas, mPaint, Color.BLACK, 500, 6, false);
        drawStar(canvas, mPaint, Color.BLACK, 600, 6, false);
        canvas.rotate(30);
        for (int i = 0; i < 6; i++) {
            canvas.drawLine(0,0,600,0,mPaint);
            canvas.rotate(60);
        }



    }


    private void drawStar(Canvas canvas, Paint paint, @ColorInt int color, float radius, int count, boolean isStar) {
        if ((!isStar) && count < 3) {
            canvas.translate(-radius, -radius);
            return;
        }
        if (isStar && count < 5) {
            canvas.translate(-radius, -radius);
            return;
        }
        canvas.rotate(-90);
        if (paint == null) {
            paint = new Paint();
        } else {
            paint.reset();
        }
        Path path = new Path();
        float inerRadius = count % 2 == 0 ?
                (radius * (cos(360 / count / 2) - sin(360 / count / 2) * sin(90 - 360 / count) / cos(90 - 360 / count)))
                : (radius * sin(360 / count / 4) / sin(180 - 360 / count / 2 - 360 / count / 4));

        for (int i = 0; i < count; i++) {
            if (i == 0) {
                path.moveTo(radius * cos(360 / count * i), radius * sin(360 / count * i));
            } else {
                path.lineTo(radius * cos(360 / count * i), radius * sin(360 / count * i));
            }
            if (isStar) {
                path.lineTo(inerRadius * cos(360 / count * i + 360 / count / 2), inerRadius * sin(360 / count * i + 360 / count / 2));
            }
        }
        path.close();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawPath(path, paint);
        canvas.rotate(90);
    }

    /**
     * Math.sin的参数为弧度，使用起来不方便，重新封装一个根据角度求sin的方法
     *
     * @param num 角度
     * @return
     */
    float sin(int num) {
        return (float) Math.sin(num * Math.PI / 180);
    }

    /**
     * 与sin同理
     */
    float cos(int num) {
        return (float) Math.cos(num * Math.PI / 180);
    }

}
