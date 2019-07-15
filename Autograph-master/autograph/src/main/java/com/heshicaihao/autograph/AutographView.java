package com.heshicaihao.autograph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heshicaihao.autograph.widget.LinePathView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/***
 *                    .::::.
 *                  .::::::::.
 *                 :::::::::::
 *             ..:::::::::::'
 *           '::::::::::::'
 *             .::::::::::
 *        '::::::::::::::..
 *             ..::::::::::::.
 *           ``::::::::::::::::
 *            ::::``:::::::::'        .:::.
 *           ::::'   ':::::'       .::::::::.
 *         .::::'      ::::     .:::::::'::::.
 *        .:::'       :::::  .:::::::::' ':::::.
 *       .::'        :::::.:::::::::'      ':::::.
 *      .::'         ::::::::::::::'         ``::::.
 *  ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 *                    '.:::::'                    ':'````..
 *
 *
 *
 * Heshicaihao
 * 2019/7/15.
 */
public class AutographView extends LinearLayout {

    private LinePathView linepath_view;
    private TextView clean_tv;
    private TextView save_tv;
    Context context;
    private String creatTime;

    public AutographView(Context context) {
        this(context, null);
        this.context = context;
    }

    public AutographView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_autograph, this);
        this.context = context;
        gainCurrenTime();
        initView();

    }

    private void initView() {
        linepath_view = findViewById(R.id.linepath_view);
        clean_tv = findViewById(R.id.clean_tv);
        save_tv = findViewById(R.id.save_tv);

        clean_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanOnDetails();
            }
        });

        //修改背景、笔宽、颜色
        linepath_view.setBackColor(Color.WHITE);
        linepath_view.setPaintWidth(20);
        linepath_view.setPenColor(Color.BLACK);
    }

    private void gainCurrenTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        creatTime = formatter.format(curDate);
    }


    /**
     * 获取画板的bitmap
     *
     * @return
     */
    public Bitmap getBitMap() {
        Bitmap bitmap = linepath_view.getBitMap();
        return bitmap;
    }

    /**
     * 保存画板
     *
     * @param path       保存到路径
     * @param clearBlank 是否清除边缘空白区域
     * @param blank      要保留的边缘空白距离
     */
    @SuppressLint("WrongThread")
    public void save(String path, boolean clearBlank, int blank) throws IOException {
        linepath_view.save(path,clearBlank,blank);

    }




    /**
     * 是否有签名
     *
     * @return
     */
    public boolean getTouched() {
        return linepath_view.getTouched();
    }

    /**
     * 清楚操作
     */
    private void cleanOnDetails() {
        linepath_view.clear();
        linepath_view.setBackColor(Color.WHITE);
        linepath_view.setPaintWidth(20);
        linepath_view.setPenColor(Color.BLACK);
    }

    public void setOnSaveListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            save_tv.setOnClickListener(onClickListener);
        }
    }



}
