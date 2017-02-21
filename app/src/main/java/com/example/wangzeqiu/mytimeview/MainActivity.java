package com.example.wangzeqiu.mytimeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        }

    }

}
