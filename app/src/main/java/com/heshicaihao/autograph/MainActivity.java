package com.heshicaihao.autograph;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinePathView autograph_view;
    private TextView clean_tv;
    private TextView save_tv;
    private ImageView show_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        show_iv =  findViewById(R.id.show_iv);
        autograph_view = findViewById(R.id.autograph_view);
        clean_tv = findViewById(R.id.clean_tv);
        save_tv = findViewById(R.id.save_tv);


        clean_tv.setOnClickListener(this);
        save_tv.setOnClickListener(this);

        //修改背景、笔宽、颜色
        autograph_view.setBackColor(Color.WHITE);
        autograph_view.setPaintWidth(20);
        autograph_view.setPenColor(Color.BLACK);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clean_tv :
                autograph_view.clear();
                autograph_view.setBackColor(Color.WHITE);
                autograph_view.setPaintWidth(20);
                autograph_view.setPenColor(Color.BLACK);

                break;

            case R.id.save_tv :
                if (autograph_view.getTouched()) {
                    try {
                        autograph_view.save("/sdcard/qm.png", true, 10);
                        setResult(100);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    Bitmap bitmap =  autograph_view.getBitMap();
//                    show_iv.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(MainActivity.this, "您没有签名~", Toast.LENGTH_SHORT).show();
                }

                break;

                default:
        }
    }
}
