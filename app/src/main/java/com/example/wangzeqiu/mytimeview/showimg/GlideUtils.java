package com.example.wangzeqiu.mytimeview.showimg;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

/**
 * @company: www.aiwinn.com
 * @author: Wangzeqiu
 * @date: 2017/11/10 9:39
 * @description
 */

class GlideUtils {


    /**
     * 设置bitmap能够防止放大的时候失真
     *
     * @param context
     * @param url
     * @param imageView
     */
    static void getBitmap(final Context context, final String url, final ImageView imageView) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//                        imageView.setImageBitmap(BitmapUtils.compressImage(resource));
                        imageView.setImageBitmap(resource);
                    }
                });
    }
}
