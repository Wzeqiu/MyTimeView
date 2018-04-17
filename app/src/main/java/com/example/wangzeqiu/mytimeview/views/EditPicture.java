package com.example.wangzeqiu.mytimeview.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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

import javax.security.auth.login.LoginException;

/**
 * 图片剪切
 */
public class EditPicture extends View {
    private static final String TAG = "EditPicture";
    private int spacing = 60;// 边距
    private int color = Color.BLACK; // 遮罩层的颜色
    private int alpha = 180;   // 遮罩层透明度
    private int colorPaint = Color.RED; // 画笔的颜色
    private float paintWidth = 1; // 画线条画笔宽度
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

    private float degrees = 0;
    private boolean isReset = true;
    private DownPosition mDownPosition = DownPosition.NONE;

    private float downX;
    private float downY;


    private float distanceX, distanceY;// 放大缩小前的距离
    private float scale = 1; //缩放比例
    private float scaleMultiple = 1;

    public boolean isReset() {
        return isReset;
    }

    public void setReset(boolean reset) {
        isReset = reset;
        scaleMultiple = 1;
        scale = 1;
        invalidate();
    }

    public float getDegrees() {
        return degrees;
    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
    }

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
        spacing = (int) (width / 10);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(paintWidth);
    }

    public void setDate() {
        InputStream is = getResources().openRawResource(R.drawable.picture2);
        BitmapDrawable bmpDraw = new BitmapDrawable(is);
        mBitmap = bmpDraw.getBitmap();
        mBitmap = Bitmap.createBitmap(mBitmap);
        bmpW = mBitmap.getWidth();
        bmpH = mBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.rotate(90, width / 2, height / 2);

        restart(canvas);

    }


    private void restart(Canvas canvas) {
        if (mDownPosition == DownPosition.CENTER || mDownPosition == DownPosition.NONE) {
            scaleMultiple = scale * scaleMultiple;
            canvas.scale(scale, scale, width / 2, height / 2);
        }
        drawBitmap(canvas);
        if (mDownPosition == DownPosition.CENTER || mDownPosition == DownPosition.NONE) {
            canvas.scale(1 / scaleMultiple, 1 / scaleMultiple, width / 2, height / 2);
        }
        drawFrame(canvas);
    }

    private void drawBitmap(Canvas canvas) {
        Rect bmpR = new Rect(0, 0, (int) bmpW, (int) bmpH); // 要展示的bitmap
        Rect canvasR;
        float changeHeight;
        float changeWidth;
        float deviationX = spacing, deviationY = spacing;    // View 的宽与缩放之后的bitmap的宽的一般,或者高
        changeHeight = height - deviationY * 2;
        changeWidth = width - deviationX * 2;
        if (changeWidth / bmpW > changeHeight / bmpH) {
            changeWidth = changeHeight / bmpH * bmpW;
            deviationX = (width - changeWidth) / 2;
            canvasR = new Rect((int) deviationX, (int) deviationY, (int) (changeWidth + deviationX), (int) (deviationY + changeHeight));
        } else {
            changeHeight = changeWidth / bmpW * bmpH;
            deviationY = (height - changeHeight) / 2;
            canvasR = new Rect((int) deviationX, (int) deviationY, (int) (changeWidth + deviationX), (int) (changeHeight + deviationY));
        }

        if (isReset) {
            isReset = false;
            topLift.set((int) deviationX, (int) deviationY);
            topRight.set((int) (changeWidth + deviationX), (int) deviationY);
            downLift.set((int) deviationX, (int) (changeHeight + deviationY));
            downRight.set((int) (changeWidth + deviationX), (int) (changeHeight + deviationY));
            distanceX = Math.abs(topRight.x - topLift.x);
            distanceY = Math.abs(downLift.y - topLift.y);
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
        canvas.drawRect(topRight.x + paintWidth * 2, topRight.y, width, height, mPaint);// 右
        canvas.drawRect(0, downLift.y + paintWidth, downRight.x + paintWidth, height, mPaint); //下
        canvas.drawRect(0, topLift.y, topLift.x, downLift.y, mPaint); // 左
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
            return;
        }
        invalidate();
    }

    /**
     * 改变矩形框的大小
     *
     * @param distanceX
     * @param distanceY
     */
    private void changeHour(int distanceX, int distanceY) {
        float minInterval = hornLength * 3;
        switch (mDownPosition) {
            case TOPLIFT:   //
                topLift.offset(distanceX, distanceY);
                topRight.offset(0, distanceY);
                downLift.offset(distanceX, 0);
                if (Math.abs(topLift.x - topRight.x) <= minInterval && distanceX >= 0) {
                    topLift.x = (int) (topRight.x - minInterval);
                    downLift.x = (int) (topRight.x - minInterval);
                }
                if (Math.abs(topLift.y - downLift.y) <= minInterval && distanceY >= 0) {
                    topRight.y = (int) (downLift.y - minInterval);
                    topLift.y = (int) (downLift.y - minInterval);
                }
                break;
            case TOPRIGHT:
                topRight.offset(distanceX, distanceY);
                topLift.offset(0, distanceY);
                downRight.offset(distanceX, 0);
                if (Math.abs(topLift.x - topRight.x) <= minInterval && distanceX <= 0) {
                    topRight.x = (int) (topLift.x + minInterval);
                    downRight.x = (int) (topLift.x + minInterval);
                }
                if (Math.abs(downRight.y - topRight.y) <= minInterval && distanceY >= 0) {
                    topRight.y = (int) (downRight.y - minInterval);
                    topLift.y = (int) (downRight.y - minInterval);
                }
                break;
            case DOWNLIFT:
                downLift.offset(distanceX, distanceY);
                topLift.offset(distanceX, 0);
                downRight.offset(0, distanceY);
                if (Math.abs(downLift.x - downRight.x) <= minInterval && distanceX >= 0) {
                    downLift.x = (int) (downRight.x - minInterval);
                    topLift.x = (int) (downRight.x - minInterval);
                }
                if (Math.abs(topLift.y - downLift.y) <= minInterval && distanceY <= 0) {
                    downLift.y = (int) (topLift.y + minInterval);
                    downRight.y = (int) (topLift.y + minInterval);
                }
                break;
            case DOWNRIGHT:
                downRight.offset(distanceX, distanceY);
                topRight.offset(distanceX, 0);
                downLift.offset(0, distanceY);
                if (Math.abs(downLift.x - downRight.x) <= minInterval && distanceX <= 0) {
                    downRight.x = (int) (downLift.x + minInterval);
                    topRight.x = (int) (downLift.x + minInterval);
                }
                if (Math.abs(topRight.y - downRight.y) <= minInterval && distanceY <= 0) {
                    downRight.y = (int) (topRight.y + minInterval);
                    downLift.y = (int) (topRight.y + minInterval);
                }
                break;
            default:
                return;
        }
        invalidate();
    }


    /**
     * 抬手之后修改框的大小
     */
    private void downUp() {
        if (mDownPosition == DownPosition.NONE || mDownPosition == DownPosition.CENTER) {
            return;
        }
        int upW = Math.abs(topRight.x - topLift.x);
        int upH = Math.abs(downLift.y - topLift.y);

        float changeHeight;
        float changeWidth;
        float deviationX = spacing, deviationY = spacing;    // View 的宽与缩放之后的bitmap的宽的一般,或者高
        changeHeight = height - deviationY * 2;
        changeWidth = width - deviationX * 2;
        if (changeWidth / upW > changeHeight / upH) {
            changeWidth = changeHeight / upH * upW;
            deviationX = (width - changeWidth) / 2;
        } else {
            changeHeight = changeWidth / upW * upH;
            deviationY = (height - changeHeight) / 2;
        }
        scale = Math.min(upW / distanceX, upH / distanceY);
        topLift.set((int) deviationX, (int) deviationY);
        topRight.set((int) (changeWidth + deviationX), (int) deviationY);
        downLift.set((int) deviationX, (int) (changeHeight + deviationY));
        downRight.set((int) (changeWidth + deviationX), (int) (changeHeight + deviationY));
        distanceX = Math.abs(topRight.x - topLift.x);
        distanceY = Math.abs(downLift.y - topLift.y);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                Log.e(TAG, "downX>>>>" + downX);
                Log.e(TAG, "downY>>>>" + downY);
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
                downUp();
                mDownPosition = DownPosition.NONE;
                invalidate();
                return true;
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
