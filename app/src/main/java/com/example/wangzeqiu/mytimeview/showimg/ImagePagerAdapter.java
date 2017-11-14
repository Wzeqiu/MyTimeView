package com.example.wangzeqiu.mytimeview.showimg;

import android.animation.Animator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

/**
 * @company: www.aiwinn.com
 * @author: Wangzeqiu
 * @date: 2017/11/9 17:29
 * @description
 */

class ImagePagerAdapter extends PagerAdapter {
    private static final String TAG = ImagePagerAdapter.class.getSimpleName();
    private static final int ANIMATION_TIME = 300;
    private StatusListener mListener;
    private Context mContext;
    private List<String> listPath;
    private int choosePosition;
    private int showX, showY;
    private int width, height;
    private boolean isFirst = true; // 第一次查看会有动画防止左右切换重复执行动画
    private boolean isShow = false;// 显示的时候是否
    private LinearLayout mCurrentView;
    private int mCurrentPosition;
    private int column, horizontal_interval, vertical_interval;


    ImagePagerAdapter(Context context, StatusListener listener, Intent intent) {
        this.mContext = context;
        this.mListener = listener;

        this.listPath = intent.getStringArrayListExtra(PictureActivity.LIST_DATE);
        this.listPath = intent.getStringArrayListExtra(PictureActivity.LIST_DATE);
        this.choosePosition = intent.getIntExtra(PictureActivity.POSITION, 0);
        this.showX = intent.getIntExtra(PictureActivity.X, 0);
        this.showY = intent.getIntExtra(PictureActivity.Y, 0);
        this.height = intent.getIntExtra(PictureActivity.HEIGHT, 0);
        this.width = intent.getIntExtra(PictureActivity.WIDTH, 0);
        this.column = intent.getIntExtra(PictureActivity.COLUMN, 0);
        this.horizontal_interval = DensityUtil.dip2px(context, intent.getIntExtra(PictureActivity.HORIZONTAL_INTERVAL, 0));
        this.vertical_interval = DensityUtil.dip2px(context, intent.getIntExtra(PictureActivity.VERTICAL_INTERVAL, 0));
    }


    @Override
    public int getCount() {
        return listPath == null ? 0 : listPath.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        this.mCurrentPosition = position;
        mCurrentView = (LinearLayout) object;
    }

    ImageView getPrimaryItem() {
        reckonXY();
        return (ImageView) mCurrentView.getChildAt(0);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setBackgroundColor(Color.TRANSPARENT);

        final ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        linearLayout.addView(imageView);

        GlideUtils.getBitmap(mContext, listPath.get(position), imageView);

        if (choosePosition == position && isFirst) {
            isFirst = false;
            imageView.getLayoutParams().width = width;
            imageView.getLayoutParams().height = height;
            startAnimator(imageView);
        } else {
            // 防止图片不居中显示
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            lp.height = DensityUtil.ScreenHeight(mContext);//从当前缩略图的高度到满屏
            lp.width = DensityUtil.ScreenWidth(mContext);//从当前缩略图的宽度到满屏
            lp.topMargin = 0;//从photoview的y到0
            lp.leftMargin = 0;//从photoview的x到0
            imageView.setLayoutParams(lp);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShow) {
                    endAnimator(imageView);
                    if (mListener != null) {
                        reckonXY();
                        mListener.startDismiss();
                    }
                }
            }
        });

        container.addView(linearLayout);
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 查看大图
     */
    void startAnimator(final ImageView mImageView) {
        if (mListener != null) {
            mListener.startShow();
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(ANIMATION_TIME);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = (float) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
                layoutParams.height = mEvaluator.evaluate(fraction, height, DensityUtil.ScreenHeight(mContext));
                //从当前缩略图的高度到满屏
                layoutParams.width = mEvaluator.evaluate(fraction, width, DensityUtil.ScreenWidth(mContext));
                //从当前缩略图的宽度到满屏
                layoutParams.topMargin = mEvaluator.evaluate(fraction, showY, 0);//从photoview的y到0
                layoutParams.leftMargin = mEvaluator.evaluate(fraction, showX, 0);//从photoview的x到0
                mImageView.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isShow = true;
                if (mListener != null) {
                    mListener.show();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    /**
     * 结束查看大图动画
     */
    void endAnimator(final ImageView mImageView) {
        if (!isShow) {
            return;
        }
        isShow = false;
        ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
        animator.setDuration(ANIMATION_TIME);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = (float) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
                params.height = mEvaluator.evaluate(fraction, height, DensityUtil.ScreenHeight(mContext));
                //从当前缩略图的高度到满屏
                params.width = mEvaluator.evaluate(fraction, width, DensityUtil.ScreenWidth(mContext));//从当前缩略图的宽度到满屏
                params.topMargin = mEvaluator.evaluate(fraction, showY, 0);//从photoview的y到0
                params.leftMargin = mEvaluator.evaluate(fraction, showX, 0);//从photoview的x到0
                mImageView.setLayoutParams(params);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mListener != null) {
                    mListener.dismiss();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    void reckonXY() {
        LogUtils.e(TAG, "mCurrentPosition===" + mCurrentPosition);
        LogUtils.e(TAG, "choosePosition===" + choosePosition);
        LogUtils.e(TAG, "column===" + column);
        showY = vertical_interval + showY - (choosePosition / column) * (height + vertical_interval) +
                mCurrentPosition / column * (height + vertical_interval);
        showX = horizontal_interval + mCurrentPosition % column * (width + horizontal_interval);
    }
}
