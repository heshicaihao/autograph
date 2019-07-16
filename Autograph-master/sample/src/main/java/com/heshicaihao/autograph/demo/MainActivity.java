package com.heshicaihao.autograph.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.heshicaihao.autograph.AutographView;
import com.heshicaihao.autograph.constants.FileConstants;
import com.heshicaihao.autograph.widget.LinePathView;

import java.io.IOException;

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
//        LinePathView linepath_view =  autograph_view.getLinePathView();
//
//        linepath_view.setBackColor(Color.WHITE);
//        linepath_view.setPaintWidth(10);
//        linepath_view.setPenColor(Color.BLACK);
        autograph_view.setBackColor(Color.WHITE);
        autograph_view.setPaintWidth(5);
        autograph_view.setPenColor(Color.BLACK);
        autograph_view.setHeight(300);

        autograph_view.setOnSaveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autograph_view.getTouched()) {
                    try {
                        String paths = FileConstants.getFilePath(FileConstants.getThirdPath("autograph"),"autograph",FileConstants.PNG);
                        autograph_view.save(paths, false, 10);
                        Toast.makeText(getApplicationContext(), "签名文件放到：\n"+paths, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "您没有签名~", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
