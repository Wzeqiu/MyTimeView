package com.example.wangzeqiu.mytimeview.views.editpicture;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangzeqiu.mytimeview.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zeqiu.wang
 * @date 2018/4/17
 */
public class EditPictureLinearLayout extends FrameLayout {
    private List<EditBlock> mOperates = new ArrayList<>();
    private LinearLayout mLinContent;


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


    private void init() {
        mOperates.add(new EditBlock("滤镜", R.drawable.filter, EditBlock.TYPE_FILTER));
        mOperates.add(new EditBlock("剪切", R.drawable.cut, EditBlock.TYPE_CUT));
        mOperates.add(new EditBlock("调整", R.drawable.adjust, 0));
        mOperates.add(new EditBlock("贴纸", R.drawable.adjust, 0));


        addTop();
        addContent();
    }

    private void addTop() {
    }

    private void addContent() {
        mLinContent = new LinearLayout(getContext());
        mLinContent.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams fraParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLinContent.setLayoutParams(fraParams);

        // 添加绘制图片的View
        EditPicture picture = new EditPicture(getContext());
        InputStream is = getResources().openRawResource(R.drawable.picture2);
        BitmapDrawable bmpDraw = new BitmapDrawable(is);
        picture.setBitmap(bmpDraw.getBitmap());
        LinearLayout.LayoutParams picParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        mLinContent.addView(picture, picParams);


        // 添加图片编辑的按钮
        LinearLayout linOperate = new LinearLayout(getContext());
        linOperate.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linOperateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linOperate.setLayoutParams(linOperateParams);
        addOperateButton(linOperate);
        mLinContent.addView(linOperate, linOperateParams);

        addView(mLinContent);
    }


    /**
     * 编辑按钮
     */
    private void addOperateButton(LinearLayout layout) {
        LinearLayout.LayoutParams linOperateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        for (int i = 0; i < mOperates.size(); i++) {
            TextView mTvOperate = new TextView(getContext());
            Drawable drawable = getResources().getDrawable(mOperates.get(i).getRes());
            mTvOperate.setCompoundDrawablePadding(5);
            mTvOperate.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            mTvOperate.setGravity(Gravity.CENTER);
            mTvOperate.setText(mOperates.get(i).getData());
            mTvOperate.setTag(mOperates.get(i).getType());
            mTvOperate.setOnClickListener(this::showEdit);
            layout.addView(mTvOperate, linOperateParams);
        }
    }

    /**
     * 处理点击结果
     *
     * @param view
     */
    private void showEdit(View view) {
        switch ((Integer) view.getTag()) {
            case EditBlock.TYPE_FILTER:
                showFilterView();
                break;
            case EditBlock.TYPE_CUT:
                break;
        }
    }


    private void showFilterView() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ProgressView progressView = new ProgressView(getContext());
        progressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 350));
        layout.addView(progressView);
        mLinContent.addView(layout, linParams);
    }

}
