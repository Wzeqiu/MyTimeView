package com.example.wangzeqiu.mytimeview.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.wangzeqiu.mytimeview.R;
import com.example.wangzeqiu.mytimeview.views.MySwitch;

public class SwitchActivity extends AppCompatActivity implements MySwitch.OnCheckedChangeListener {

    MySwitch mMySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        mMySwitch = (MySwitch) findViewById(R.id.mySwitch);
        mMySwitch.setOnCheckedChangeWidgetListener(this);

        mMySwitch.postDelayed(new Runnable() {
            @Override
            public void run() {

                mMySwitch.setChecked(false);
            }
        },2000);


    }

    @Override
    public void onCheckedChanged(boolean isOpen) {
        Toast.makeText(this, isOpen + "", Toast.LENGTH_SHORT).show();

    }
}
