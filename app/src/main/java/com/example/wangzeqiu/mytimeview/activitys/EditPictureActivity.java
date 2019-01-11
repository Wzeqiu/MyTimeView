package com.example.wangzeqiu.mytimeview.activitys;


import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangzeqiu.mytimeview.R;
import com.example.wangzeqiu.mytimeview.views.editpicture.EditPictureLinearLayout;

public class EditPictureActivity extends AppCompatActivity {

    private TextView views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(new EditPictureLinearLayout(this));
        setContentView(R.layout.activity_edit_picture);

        views = (TextView) findViewById(R.id.views);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });

    }

    public void test() {
        ValueAnimator animator = ValueAnimator.ofInt(((LinearLayout.LayoutParams) views.getLayoutParams()).bottomMargin, 0);
        animator.setDuration(800);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                Log.e("EditPictureActivity", "value>>>>>>" + value);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) views.getLayoutParams();
                params.setMargins(0, 0, 0, value);
                views.setLayoutParams(params);
            }
        });
        animator.start();
    }

}
