package com.example.wangzeqiu.mytimeview.activitys;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wangzeqiu.mytimeview.R;

public class PermissionActivity extends AppCompatActivity {
    private static final String TAG= PermissionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (!checkStoragePermission()){
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
    }
    public boolean checkStoragePermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
    }

    void requestStoragePermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.e(TAG,"不在提示");
        } else {
            Log.e(TAG,"拒绝");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                Log.e(TAG,"失败");
                requestStoragePermission();
            }else{
                Log.e(TAG,"成功");
            }

        }
    }
}
