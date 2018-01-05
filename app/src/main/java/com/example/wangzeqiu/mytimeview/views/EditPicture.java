package com.example.wangzeqiu.mytimeview.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.wangzeqiu.mytimeview.R;

import java.io.InputStream;

/**
 * 图片剪切
 */
public class EditPicture extends View {
    private static final String TAG = "EditPicture";
    private int spacing = 60;// 边距
    private int color = Color.BLACK; // 遮罩层的颜色
    private int alpha = 150;   // 遮罩层透明度
    private int colorPaint = Color.WHITE; // 画笔的颜色
    private int paintWidth = 3; // 画线条画笔宽度
    private int hornPaintWidth = 10;//画角三角形画笔宽度
    private int hornLength = 17;//画角三角形边宽度
    private int lineNumber = 2;//网格线的数量
    private Paint mPaint;

    private Bitmap mBitmap; // 原图的Bitmap
    private float bmpW; // Bitmap的宽度
    private float bmpH; // Bitmap的高度


    private float width; // View 的宽度
    private float height; // View 的高度

    private Point topLift = new Point();
    private Point topRight = new Point();
    private Point downLift = new Point();
    private Point downRight = new Point();
    private int area = 30;  //四个角触摸区域反馈大小


    private boolean isReset = true;
    private DownPosition mDownPosition = DownPosition.NONE;


    private float downX;
    private float downY;

    public EditPicture(Context context) {
        this(context, null);
    }

    public EditPicture(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditPicture(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setDate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(paintWidth);
    }

    public void setDate() {
        InputStream is = getResources().openRawResource(R.drawable.picture);
        BitmapDrawable bmpDraw = new BitmapDrawable(is);
        mBitmap = bmpDraw.getBitmap();
        bmpW = mBitmap.getWidth();
        bmpH = mBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        restart(canvas);
    }


    private void restart(Canvas canvas) {
        drawBitmap(canvas);
        drawFrame(canvas);
    }

    private void drawBitmap(Canvas canvas) {
        Rect bmpR = new Rect(0, 0, (int) bmpW, (int) bmpH); // 要展示的bitmap
        Rect canvasR;
        float changeHeight;
        float changeWidth;
        float deviation;    // View 的宽与缩放之后的bitmap的宽的一般,或者高
        changeHeight = height - spacing * 2;
        changeWidth = width - spacing * 2;
        if (changeWidth / width > changeHeight / height) {
            changeWidth = changeHeight / bmpH * bmpW;
            deviation = (width - changeWidth) / 2;
            canvasR = new Rect((int) (spacing + deviation), spacing, (int) (changeWidth + deviation), (int) (spacing + changeHeight));
        } else {
            changeHeight = changeWidth / bmpW * bmpH;
            deviation = (height - changeHeight) / 2;
            canvasR = new Rect(spacing, (int) (spacing + deviation), (int) (changeWidth + spacing), (int) (changeHeight + deviation));
        }
        if (isReset) {
            isReset = false;
            topLift.set(spacing, (int) (spacing + deviation));
            topRight.set((int) (changeWidth + spacing), (int) (spacing + deviation));
            downLift.set(spacing, (int) (changeHeight + deviation));
            downRight.set((int) (changeWidth + spacing), (int) (changeHeight + deviation));
        }
        canvas.drawBitmap(mBitmap, bmpR, canvasR, null);
    }

    private void drawFrame(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(colorPaint);
        mPaint.setStrokeWidth(paintWidth);
        // 边框线
        canvas.drawLine(topLift.x, topLift.y, topRight.x, topRight.y, mPaint); // 上
        canvas.drawLine(topRight.x, topRight.y, downRight.x, downRight.y, mPaint);// 右
        canvas.drawLine(downRight.x, downRight.y, downLift.x, downLift.y, mPaint);// 下
        canvas.drawLine(downLift.x, downLift.y, topLift.x, topLift.y, mPaint);// 左
        // 绘制网格
        if (mDownPosition != DownPosition.NONE) {
            for (int i = 1; i <= lineNumber; i++) {
                canvas.drawLine(topLift.x, topLift.y + (downLift.y - topLift.y) * i / (lineNumber + 1), topRight.x, topRight.y + (downRight.y - topRight.y) * i / (lineNumber + 1), mPaint);
                canvas.drawLine(topRight.x - (topRight.x - topLift.x) * i / (lineNumber + 1), topRight.y, downRight.x - (topRight.x - topLift.x) * i / (lineNumber + 1), downRight.y, mPaint);// 右
            }
        }
        //遮罩层
        mPaint.setColor(color);
        mPaint.setAlpha(alpha);
        canvas.drawRect(0, 0, width, topLift.y - paintWidth, mPaint); // 上
        canvas.drawRect(topRight.x + paintWidth, topRight.y, width, height, mPaint);// 右
        canvas.drawRect(0, downLift.y + paintWidth, downRight.x, height, mPaint); //下
        canvas.drawRect(0, topLift.y, topLift.x - paintWidth, downLift.y, mPaint); // 左
        // 三角形
        mPaint.setColor(colorPaint);
        mPaint.setStrokeWidth(hornPaintWidth);
        // 左上角
        canvas.drawLine(topLift.x - hornPaintWidth, topLift.y - hornPaintWidth / 2, topLift.x + hornLength, topLift.y - hornPaintWidth / 2, mPaint);
        canvas.drawLine(topLift.x - hornPaintWidth / 2, topLift.y, topLift.x - hornPaintWidth / 2, topLift.y + hornLength, mPaint);
        // 右上角
        canvas.drawLine(topRight.x - hornLength, topRight.y - hornPaintWidth / 2, topRight.x + hornPaintWidth, topRight.y - hornPaintWidth / 2, mPaint);
        canvas.drawLine(topRight.x + hornPaintWidth / 2, topRight.y, topRight.x + hornPaintWidth / 2, topRight.y + hornLength, mPaint);
        // 左下角
        canvas.drawLine(downLift.x - hornPaintWidth, downLift.y + hornPaintWidth / 2, downLift.x + hornLength, downLift.y + hornPaintWidth / 2, mPaint);
        canvas.drawLine(downLift.x - hornPaintWidth / 2, downLift.y - hornLength, downLift.x - hornPaintWidth / 2, downLift.y, mPaint);
        // 右下角
        canvas.drawLine(downRight.x - hornLength, downRight.y + hornPaintWidth / 2, downRight.x + hornPaintWidth, downRight.y + hornPaintWidth / 2, mPaint);
        canvas.drawLine(downRight.x + hornPaintWidth / 2, downRight.y - hornLength, downRight.x + hornPaintWidth / 2, downRight.y, mPaint);
    }

    /**
     * 判断点击区域
     *
     * @param downX
     * @param downY
     */
    private void downHorn(float downX, float downY) {
        if (topLift.x <= (downX + area) && topLift.x >= (downX - area)) {
            if (topLift.y <= (downY + area) && topLift.y >= (downY - area)) {
                mDownPosition = DownPosition.TOPLIFT;
                // 点击左上角
                Log.e(TAG, "点击左上角");
            } else if (downLift.y <= (downY + area) && downLift.y >= (downY - area)) {
                mDownPosition = DownPosition.DOWNLIFT;
                // 点击左下角
                Log.e(TAG, "点击左下角");
            }
        } else if (topRight.x <= (downX + area) && topRight.x >= (downX - area)) {
            if (topRight.y <= (downY + area) && topRight.y >= (downY - area)) {
                mDownPosition = DownPosition.TOPRIGHT;
                // 点击右上角
                Log.e(TAG, "点击右上角");
            } else if (downRight.y <= (downY + area) && downRight.y >= (downY - area)) {
                mDownPosition = DownPosition.DOWNRIGHT;
                // 点击右下角
                Log.e(TAG, "点击右下角");
            }
        } else {
            mDownPosition = DownPosition.CENTER;
        }
        invalidate();
    }

    /**
     * 拖动矩形框
     *
     * @param distanceX
     * @param distanceY
     */
    private void changeHour(int distanceX, int distanceY) {
//        float minInterval = hornLength * 3;
        switch (mDownPosition) {
            case TOPLIFT:   //
//                if (Math.abs(topLift.x - topRight.x) <= minInterval && distanceX > 0) {
//                    distanceX = 0;
//                }
//                if (Math.abs(topLift.y - downLift.y) <= minInterval && distanceY > 0) {
//                    distanceY = 0;
//                }
                topLift.offset(distanceX, distanceY);
                topRight.offset(0, distanceY);
                downLift.offset(distanceX, 0);
                break;
            case TOPRIGHT:
                topRight.offset(distanceX, distanceY);
                topLift.offset(0, distanceY);
                downRight.offset(distanceX, 0);
                break;
            case DOWNLIFT:
                downLift.offset(distanceX, distanceY);
                topLift.offset(distanceX, 0);
                downRight.offset(0, distanceY);
                break;
            case DOWNRIGHT:
                downRight.offset(distanceX, distanceY);
                topRight.offset(distanceX, 0);
                downLift.offset(0, distanceY);
                break;
        }
        invalidate();
    }


    private void downUp() {
//        int upW = topRight.x - topLift.x;
//        int upH = downLift.x - topLift.y;
//
//        float changeHeight;
//        float changeWidth;
//        float deviation;    // View 的宽与缩放之后的bitmap的宽的一般,或者高
//        changeHeight = height - spacing * 2;
//        changeWidth = width - spacing * 2;
//        if (changeWidth / width > changeHeight / height) {
//            changeWidth = changeHeight / upH * upW;
//            deviation = (width - changeWidth) / 2;
//        } else {
//            changeHeight = changeWidth / upW * upH;
//            deviation = (height - changeHeight) / 2;
//        }
//        topLift.set(spacing, (int) (spacing + deviation));
//        topRight.set((int) (changeWidth + spacing), (int) (spacing + deviation));
//        downLift.set(spacing, (int) (changeHeight + deviation));
//        downRight.set((int) (changeWidth + spacing), (int) (changeHeight + deviation));
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "Action>>>" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                downHorn(downX, downY);
                return true;
            case MotionEvent.ACTION_MOVE:
                int distanceY = (int) (event.getY() - downY);
                int distanceX = (int) (event.getX() - downX);
                changeHour(distanceX, distanceY);
                downX = event.getX();
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                mDownPosition = DownPosition.NONE;
                downUp();

                break;
        }
        return super.onTouchEvent(event);
    }

    public enum DownPosition {
        NONE,
        TOPLIFT,
        TOPRIGHT,
        DOWNLIFT,
        DOWNRIGHT,
        CENTER
    }
}
