package com.example.wangzeqiu.mytimeview.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wangzeqiu.mytimeview.R;
import com.example.wangzeqiu.mytimeview.showimg.PictureActivity;

import java.util.ArrayList;

public class ShowImgActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageView, mImageView2, mImageView3, mImageView4;
    private ArrayList<String> listPath = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_img);

        mImageView = (ImageView) findViewById(R.id.iv_img);
        mImageView2 = (ImageView) findViewById(R.id.iv_img1);
        mImageView3 = (ImageView) findViewById(R.id.iv_img2);
        mImageView4 = (ImageView) findViewById(R.id.iv_img3);
        mImageView.setOnClickListener(this);
        mImageView2.setOnClickListener(this);
        mImageView3.setOnClickListener(this);
        mImageView4.setOnClickListener(this);

        listPath.add("http://f.hiphotos.baidu.com/image/pic/item/2fdda3cc7cd98d1004fc53762a3fb80e7bec9048.jpg");
        listPath.add("http://d.hiphotos.baidu.com/image/pic/item/b219ebc4b74543a909dda78b15178a82b80114b6.jpg");
        listPath.add("http://g.hiphotos.baidu.com/image/pic/item/7a899e510fb30f242c19b744c395d143ac4b03f1.jpg");
        listPath.add("http://f.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f4134d20654392e1f95cad1c85e47.jpg");


        Glide.with(this).load(listPath.get(0)).into(mImageView);
        Glide.with(this).load(listPath.get(1)).into(mImageView2);
        Glide.with(this).load(listPath.get(2)).into(mImageView3);
        Glide.with(this).load(listPath.get(3)).into(mImageView4);
    }

    @Override
    public void onClick(View view) {
        int choose = 0;
        switch (view.getId()) {
            case R.id.iv_img:
                choose = 0;
                break;
            case R.id.iv_img1:
                choose = 1;
                break;
            case R.id.iv_img2:
                choose = 2;
                break;
            case R.id.iv_img3:
                choose = 3;
                break;
        }
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        PictureActivity.getInstance(ShowImgActivity.this, listPath, choose, x, y, mImageView.getMeasuredWidth(), mImageView
                .getMeasuredHeight(),1,3,3);
    }

}
