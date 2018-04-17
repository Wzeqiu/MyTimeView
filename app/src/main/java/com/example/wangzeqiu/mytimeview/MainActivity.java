package com.example.wangzeqiu.mytimeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wangzeqiu.mytimeview.activitys.CakeActivity;
import com.example.wangzeqiu.mytimeview.activitys.EditPictureActivity;
import com.example.wangzeqiu.mytimeview.activitys.LoadViewActivity;
import com.example.wangzeqiu.mytimeview.activitys.PermissionActivity;
import com.example.wangzeqiu.mytimeview.activitys.RadarActivity;
import com.example.wangzeqiu.mytimeview.activitys.ShowImgActivity;
import com.example.wangzeqiu.mytimeview.activitys.SwitchActivity;
import com.example.wangzeqiu.mytimeview.activitys.TimeActivity;
import com.example.wangzeqiu.mytimeview.activitys.VerticalMarqueeActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.get_permission).setOnClickListener(this);
        findViewById(R.id.edit_picture).setOnClickListener(this);
        findViewById(R.id.up_power).setOnClickListener(this);
        //test1
        //test2
        //test2
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                startActivity(new Intent(this, TimeActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, VerticalMarqueeActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(this, RadarActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(this, SwitchActivity.class));
                break;
            case R.id.button5:
                startActivity(new Intent(this, CakeActivity.class));
                break;
            case R.id.button6:
                startActivity(new Intent(this, LoadViewActivity.class));
                break;
            case R.id.button7:
                startActivity(new Intent(this, ShowImgActivity.class));
                break;
            case R.id.get_permission:
                startActivity(new Intent(this, PermissionActivity.class));
                break;
            case R.id.edit_picture:
                startActivity(new Intent(this, EditPictureActivity.class));
                break;
        }

    }


}
