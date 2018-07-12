package com.example.wangzeqiu.mytimeview.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wangzeqiu.mytimeview.views.ProportionView;

public class ProportionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ProportionView(this));
    }
}
