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

        listPath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510569790440&di=59f62ff2ea0200a9af8815d800b63dce&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201407%2F27%2F20140727123700_JyHky.jpeg");
        listPath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510569790439&di=5a1d090c15d03f9d9ee65b55edd34ea0&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201407%2F27%2F20140727015429_4RFkR.jpeg");
        listPath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510569840563&di=40e68f8bf76aedc74a1576a9e56d6712&imgtype=jpg&src=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D329377316%2C1618219808%26fm%3D214%26gp%3D0.jpg");
        listPath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510569790437&di=9a4b4784b26f5c828daf17778675ac22&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201407%2F27%2F20140727021834_hrPL3.jpeg");


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
