package com.example.wangzeqiu.mytimeview.views.editpicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zeqiu.wang
 * @date 2018/4/17
 */
public class EditPicture extends View {


    private Bitmap mBitmap; // 编辑的图片
    private int bitWidth;
    private int bitHeight;
    private Rect mBitRect = new Rect();
    private int width;
    private int height;
    private Rect mRect = new Rect();

    public EditPicture(Context context) {
        this(context, null);
    }

    public EditPicture(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditPicture(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        mRect.set(0, 0, w, h);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            return;
        }

        canvas.drawBitmap(mBitmap, mBitRect, mRect, null);



    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        bitWidth = mBitmap.getWidth();
        bitHeight = mBitmap.getHeight();
        mBitRect.set(0, 0, bitWidth, bitHeight);
        invalidate();
    }
}
