package com.example.wangzeqiu.mytimeview.views.editpicture;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by User on 2018/1/8.
 */

public class EditPictureLinearLayout extends LinearLayout {
    public EditPictureLinearLayout(Context context) {
        this(context, null);
    }

    public EditPictureLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditPictureLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private LinearLayout bottomLayout;

    private void init() {
        setOrientation(VERTICAL);
        final EditPicture editPicture = new EditPicture(getContext());
        LinearLayout.LayoutParams edlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 4);
        editPicture.setLayoutParams(edlp);
        addView(editPicture);
        LinearLayout bottomLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams bmlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        bottomLayout.setLayoutParams(bmlp);
        bottomLayout.setOrientation(HORIZONTAL);
        addView(bottomLayout);

        Button button = new Button(getContext());
        LinearLayout.LayoutParams bu = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(bu);
        button.setText("还原");
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editPicture.setReset(true);
            }
        });
        bottomLayout.addView(button);
    }
}
