package com.example.wangzeqiu.mytimeview.views;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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

    private int centerBitmapHeight;         //中间图片的高度
    private int centerBitmapWidth;          //中间图片的宽度
    private int centerBitmapRadius;         //中间圆的半径
    private Rect rect;                      //
    private int width;                      //画布的宽
    private int height;                     //画布的高
    private int alpha = 255;
    private int radius;
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
        mCenterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.center);
        centerBitmapHeight = mCenterBitmap.getHeight();
        centerBitmapWidth = mCenterBitmap.getWidth();
        if (centerBitmapWidth > centerBitmapHeight) {
            centerBitmapRadius = centerBitmapHeight >> 1;
        } else {
            centerBitmapRadius = centerBitmapWidth >> 1;
        }
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
        if(mValueAnimators.size()==0){
            mPaint1.setAlpha(32);
            canvas.drawCircle(width >> 1, height >> 1, 600, mPaint1);
            mPaint1.setAlpha(64);
            canvas.drawCircle(width >> 1, height >> 1, 450, mPaint1);
            mPaint1.setAlpha(96);
            canvas.drawCircle(width >> 1, height >> 1, 300, mPaint1);
            mPaint1.setAlpha(128);
            canvas.drawCircle(width >> 1, height >> 1, 150, mPaint1);
        }else{
            Iterator<ValueAnimator> iterator = mValueAnimators.iterator();
            while (iterator.hasNext()) {
                ValueAnimator valueAnimator = iterator.next();
                if ((Integer)valueAnimator.getAnimatedValue("radius")<599) {
                    System.out.println("(Integer)valueAnimator.getAnimatedValue)==="+valueAnimator.getAnimatedValue("radius"));
                    mPaint1.setAlpha((Integer) valueAnimator.getAnimatedValue("alpha"));
                    canvas.drawCircle(width >> 1, height >> 1, (Integer) valueAnimator.getAnimatedValue("radius"), mPaint1);


                } else {
                    System.out.println("???????????????????????");
                    valueAnimator.cancel();
                    iterator.remove();

                }

            }

        }



        canvas.drawBitmap(createCircleImage(mCenterBitmap), null, rect, mPaint);

    }

    /**
     * 创建圆图片
     *
     * @param bitmap
     * @return
     */
    public Bitmap createCircleImage(Bitmap bitmap) {
        Bitmap bitmap1 = Bitmap.createBitmap(centerBitmapRadius << 1, centerBitmapRadius << 1, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(bitmap1);
        canvas.drawCircle(centerBitmapRadius, centerBitmapRadius, centerBitmapRadius, paint);
        //使用SRC_IN
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bitmap1;
    }

    public void start() {
        PropertyValuesHolder holder = PropertyValuesHolder.ofInt("radius", centerBitmapRadius, 600);
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofInt("alpha", 128, 0);
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(holder, holder1);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setDuration(4000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius = (int) animation.getAnimatedValue("radius");
                alpha = (int) animation.getAnimatedValue("alpha");
                invalidate();
            }
        });
        System.out.println("mValueAnimators=========="+mValueAnimators.size());
        mValueAnimators.add(valueAnimator);
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mWaveRunable.run();
                break;
        }
        return super.onTouchEvent(event);

    }

    private Runnable mWaveRunable = new Runnable() {
        @Override
        public void run() {
            start();
            invalidate();
            postDelayed(mWaveRunable, 1000);
        }
    };

}
