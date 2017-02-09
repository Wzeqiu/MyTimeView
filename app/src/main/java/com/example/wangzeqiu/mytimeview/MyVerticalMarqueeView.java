package com.example.wangzeqiu.mytimeview;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WangZeQiu on 2017/2/9.
 */

public class MyVerticalMarqueeView extends View implements View.OnClickListener {
    private Paint mPaint;

    private int width;                          //View宽度
    private int height;                         //View高度
    private int centerX;                        //View在X轴中点
    private int centerY;                        //View在Y轴中点

    private List<String> mList;                 //轮询的数据
    private List<PaintText> mPaintTexts;        //集合放置两个PaintText实体类

    private int DURATION_TIME = 1000;           //单次动画持续事件
    private int INTERVAL_TIME = 3000;           //动画间隔时间
    private OnClickListener mClick;             //点击接口

    //定时器实现动画间隔时间
    private Timer mTimer;
    private TimerTask mTimerTask;
    private Handler mHandler;


    public MyVerticalMarqueeView(Context context) {
        this(context, null);
    }

    public MyVerticalMarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVerticalMarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        setOnClickListener(this);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(60);

        mPaintTexts = new ArrayList<>();


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                startAnimator();
            }
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mList == null || this.mList.size() == 0) {
            Log.e("MyVerticalMarqueeView", "the mList size is illegal");
            return;
        }
        mPaintTexts.clear();

        width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        centerX = width / 2;
        centerY = height / 2;

        PaintText paintText = new PaintText(mPaint, mList.get(0), centerY, 0).setAlpha(255);
        paintText.reset();
        mPaintTexts.add(paintText);
        if (mList.size() > 1) {         //超出View可见范围
            PaintText paintText1 = new PaintText(mPaint, mList.get(1), centerY + height, 1).setAlpha(255);
            paintText1.reset();
            mPaintTexts.add(paintText1);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PaintText paintText : mPaintTexts) {
            paintText.draw(canvas);
        }
    }


    private void startAnimator() {
        PropertyValuesHolder holder = PropertyValuesHolder.ofInt("scrollY", centerY, centerY - height); //滚动的距离
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofInt("alpha", 255, 0);                     //透明度变化
        ValueAnimator animator = ValueAnimator.ofPropertyValuesHolder(holder, holder1);
        animator.setDuration(DURATION_TIME);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int scrollY = (int) animation.getAnimatedValue("scrollY");
                int alpha = (int) animation.getAnimatedValue("alpha");
                mPaintTexts.get(0).setAlpha(alpha).setCenterY(scrollY).reset();
                mPaintTexts.get(1).setAlpha(255 - alpha).setCenterY(scrollY + height).reset();

                postInvalidate();
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                int position = mPaintTexts.get(1).getPosition();
                PaintText paintText = mPaintTexts.remove(0);
                if (position == mList.size() - 1) {
                    position = 0;
                } else {
                    position++;
                }
                paintText.setCenterY(centerY + height).setText(mList.get(position)).setPosition(position);
                paintText.reset();
                mPaintTexts.add(paintText);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    //动画持续时间
    public MyVerticalMarqueeView setDurationTime(int time) {
        this.DURATION_TIME = time;
        return this;
    }

    //动画间隔时间
    public MyVerticalMarqueeView setIntervalTime(int time) {
        this.INTERVAL_TIME = time;
        return this;
    }


    //设置数据
    public MyVerticalMarqueeView setData(ArrayList<String> datas) {
        this.mList = datas == null ? new ArrayList<String>() : datas;
        return this;
    }


    public MyVerticalMarqueeView setOnClick(OnClickListener click) {
        this.mClick = click;
        return this;
    }

    //点击事件接口
    public interface OnClickListener {
        void onClick(int positon, String str);
    }

    //界面销毁
    public void Destroy() {
        stop();
    }

    //开始动画
    public void start() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendMessage(mHandler.obtainMessage());
            }
        };
        mTimer.schedule(mTimerTask, INTERVAL_TIME, INTERVAL_TIME + DURATION_TIME);
    }

    //结束动画
    public void stop() {
        mTimer.cancel();
        mTimerTask.cancel();
    }


    @Override
    public void onClick(View v) {
        if (mClick != null) {
            int positon = mPaintTexts.get(0).getPosition();
            mClick.onClick(positon, mList.get(positon));
        }
    }


    private class PaintText {
        private Paint mPaint;
        private String text;
        private int centerY;
        private int drawX;
        private int drawY;
        private int position;
        private int alpha;


        public PaintText(Paint paint, String text, int centerY, int positon) {
            this.mPaint = paint;
            this.text = text;
            this.centerY = centerY;
            this.position = positon;
        }


        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public PaintText setAlpha(@Size(max = 255, min = 0) int alpha) {
            this.alpha = alpha;
            return this;
        }

        public PaintText setCenterY(int centerY) {
            this.centerY = centerY;
            return this;
        }

        public PaintText setText(String str) {
            this.text = str;
            return this;
        }

        //计算距离
        public void reset() {
            int measureWidth = (int) mPaint.measureText(text);
            drawX = (width - measureWidth) / 2;
            Paint.FontMetrics metrics = mPaint.getFontMetrics();
            drawY = (int) (centerY + (metrics.bottom - metrics.top) / 2 - metrics.bottom);
        }


        //绘制文字
        public void draw(Canvas canvas) {
            mPaint.setAlpha(alpha);
            canvas.drawText(text, drawX, drawY, mPaint);
        }
    }
}
