package com.heshicaihao.autograph.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.heshicaihao.autograph.AutographView;

public class MainActivity extends AppCompatActivity {


    private AutographView autograph_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gainPermission();
        initView();

    }

    private void gainPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
            } else {
                Toast.makeText(this, "已开启权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        autograph_view = findViewById(R.id.autograph_view);
        autograph_view.setMyHeight(330);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 4:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "已打开权限！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "请打开权限！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


}
