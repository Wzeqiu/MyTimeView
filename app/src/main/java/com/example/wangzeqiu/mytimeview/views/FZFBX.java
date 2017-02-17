package com.example.wangzeqiu.mytimeview.views;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.wangzeqiu.mytimeview.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by WangZeQiu on 2017/2/14.
 * 仿支付宝咻咻
 */

public class FZFBX extends View {

    private Paint mPaint;                   //中间圆
    private Paint mPaint1;                  //波纹
    private Bitmap mCenterBitmap;
    private Bitmap mCircleImageBitmap;

    private int centerBitmapHeight;         //中间图片的高度
    private int centerBitmapWidth;          //中间图片的宽度
    private int centerBitmapRadius;         //中间圆的半径
    private Rect rect;                      //
    private int width;                      //画布的宽
    private int height;                     //画布的高
    private int alpha = 255;
    private int radius;
    private int rippleRadius;
    private int DURATION_TIME = 5000;
    private int INTERVAL_TIME = 1000;
    private int maxAlpha = 128;
    private int maxSize = DURATION_TIME / INTERVAL_TIME;
    private List<ValueAnimator> mValueAnimators;

    public FZFBX(Context context) {
        this(context, null);
    }

    public FZFBX(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FZFBX(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        mCircleImageBitmap = createCircleImage();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setColor(Color.BLUE);
        mValueAnimators = new ArrayList<>();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        if (width > height) {
            rippleRadius = height >> 1;
        } else {
            rippleRadius = width >> 1;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCenterBitmap(canvas);
    }

    /**
     * 绘制中间圆
     *
     * @param canvas
     */
    public void drawCenterBitmap(Canvas canvas) {
        if (rect == null) {
            rect = new Rect((width >> 1) - centerBitmapRadius, (height >> 1) - centerBitmapRadius, (width >> 1) + centerBitmapRadius, (height >> 1) + centerBitmapRadius);
        }
        if (mValueAnimators.size() == 0) {
            for (int i = 1; i <= maxSize; i++) {
                mPaint1.setAlpha(maxAlpha * i / maxSize - 1 >> 1);
                canvas.drawCircle(width >> 1, height >> 1, rippleRadius * i / maxSize - 1, mPaint1);
            }
        } else {
            Iterator<ValueAnimator> iterator = mValueAnimators.iterator();
            while (iterator.hasNext()) {
                ValueAnimator valueAnimator = iterator.next();
                if ((Integer) valueAnimator.getAnimatedValue("radius") < rippleRadius - 1) {
                    mPaint1.setAlpha((Integer) valueAnimator.getAnimatedValue("alpha"));
                    canvas.drawCircle(width >> 1, height >> 1, (Integer) valueAnimator.getAnimatedValue("radius"), mPaint1);
                } else {
                    valueAnimator.cancel();
                    iterator.remove();
                }
            }
        }
        if (mCircleImageBitmap == null) {
            mCircleImageBitmap = createCircleImage();
        }
        canvas.drawBitmap(mCircleImageBitmap, null, rect, mPaint);
        if (mValueAnimators.size() >= 3) {
            Rect rect1 = new Rect((width >> 1) - centerBitmapRadius * 3, (height >> 1) - centerBitmapRadius, (width >> 1) - centerBitmapRadius, (height >> 1) + centerBitmapRadius);
            canvas.drawBitmap(mCircleImageBitmap, null, rect1, mPaint);
        }

    }

    /**
     * 创建圆图片
     *
     * @return
     */
    public Bitmap createCircleImage() {
        Bitmap mCenterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.center);
        centerBitmapHeight = mCenterBitmap.getHeight();
        centerBitmapWidth = mCenterBitmap.getWidth();
        if (centerBitmapWidth > centerBitmapHeight) {
            centerBitmapRadius = centerBitmapHeight >> 1;
        } else {
            centerBitmapRadius = centerBitmapWidth >> 1;
        }
        Bitmap bitmap1 = Bitmap.createBitmap(centerBitmapRadius << 1, centerBitmapRadius << 1, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(bitmap1);
        canvas.drawCircle(centerBitmapRadius, centerBitmapRadius, centerBitmapRadius, paint);
        //使用SRC_IN
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mCenterBitmap, 0, 0, paint);
        mCenterBitmap.recycle();
        return bitmap1;
    }


    public void start() {

        PropertyValuesHolder holder = PropertyValuesHolder.ofInt("radius", centerBitmapRadius, rippleRadius);
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofInt("alpha", maxAlpha, 0);
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(holder, holder1);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setDuration(DURATION_TIME);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius = (int) animation.getAnimatedValue("radius");
                alpha = (int) animation.getAnimatedValue("alpha");
                invalidate();
            }
        });
        System.out.println("mValueAnimators==========" + mValueAnimators.size());
        mValueAnimators.add(valueAnimator);
        valueAnimator.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isStart) {
                    mWaveRunable.run();
                    isStart = true;
                }

                break;
        }
        return super.onTouchEvent(event);

    }

    boolean isStart = false;
    private Runnable mWaveRunable = new Runnable() {
        @Override
        public void run() {
            start();
            invalidate();
            postDelayed(mWaveRunable, INTERVAL_TIME);
        }
    };






}
