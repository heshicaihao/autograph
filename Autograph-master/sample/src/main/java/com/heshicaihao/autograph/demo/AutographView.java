package com.heshicaihao.autograph.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heshicaihao.autograph.demo.R;

import com.heshicaihao.autograph.demo.constants.FileConstants;
import com.heshicaihao.autograph.demo.widget.LinePathView;

import java.io.File;
import java.io.IOException;


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

    private LinearLayout autograph_ll;
    private LinearLayout clean_ll;
    private LinePathView linepath_view;
    private TextView save_tv;
    private TextView title_tv;
    private ImageView clean_iv;
    private TextView clean_tv;
    private String token;
    private String type;
    private String workNo;
    Context context;

    public AutographView(Context context) {
        this(context, null);
        this.context = context;
    }

    public AutographView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate( R.layout.view_autograph, this);
        this.context = context;
        initView();

    }

    private void initView() {
        autograph_ll = findViewById( R.id.clean_ll);
        clean_ll = findViewById( R.id.clean_ll);
        linepath_view = findViewById( R.id.linepath_view);
        save_tv = findViewById( R.id.save_tv);
        title_tv = findViewById( R.id.title_tv);
        clean_iv = findViewById( R.id.clean_iv);
        clean_tv = findViewById(R.id.clean_tv);

        linepath_view.setPaintWidth(10);
        linepath_view.setPenColor(Color.BLACK);

        clean_ll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanOnDetails();
            }
        });
        Log.d("save_tv", "0");
        save_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("save_tv", "1");
                if (getTouched()) {
                    Log.d("save_tv", "getTouched");
                    try {
                        String paths = FileConstants.getFilePath(FileConstants.getThirdPath("autograph"), "autograph", FileConstants.PNG);
                        Log.d("save_tv", paths);
                        File file = new File(paths);
                        if (file.exists()) {
                            file.delete();
                            Log.d("save_tv", "delete");
                        }
                        save(paths, false, 10);
                        Log.d("save_tv", "save");
                        Toast.makeText(context, "签名文件放到：\n" + paths, Toast.LENGTH_SHORT).show();
                        Log.d("save_tv", "签名文件放到");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "您没有签名~", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 整体控件高度最小 130,标准330
     *
     * @param height
     */
    public void setMyHeight(int height) {
        LayoutParams params = (LayoutParams) linepath_view.getLayoutParams();
//        params.width = dip2px(context, width);
        params.height = dip2px(context, height - 130);
        linepath_view.setLayoutParams(params);
    }

    /**
     * 设置确认按钮文字
     *
     * @param text
     */
    public void setConfirmBtnText(String text) {
        save_tv.setText(text);
    }

    /**
     * 设置确认按钮文字大小
     *
     * @param size
     */
    public void setConfirmBtnTextSize(int size) {
        save_tv.setTextSize(size);
    }

    /**
     * 设置标题文字大小
     *
     * @param size
     */
    public void setTitleTextSize(int size) {
        title_tv.setTextSize(size);
    }

    /**
     * 设置清空图标大小
     *
     * @param size
     */
    public void setCleanIconSize(int size) {
        LayoutParams params = (LayoutParams) clean_iv.getLayoutParams();
        params.width = size;
        params.height = size;
        clean_iv.setLayoutParams(params);
    }

    /**
     * 设置清空文字大小
     *
     * @param size
     */
    public void setCleanTextSize(int size) {
        clean_tv.setTextSize(size);
    }

    /**
     * 传Token
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 传type
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * workNo
     *
     * @param workNo
     */
    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    /**
     * 设置画布背景
     *
     * @param color
     */
    public void setBackColor(int color) {
        linepath_view.setBackColor(color);
    }

    /**
     * 设置画笔宽度
     *
     * @param Width
     */
    public void setPaintWidth(int Width) {
        linepath_view.setPaintWidth(Width);
    }

    /**
     * 设置画笔颜色
     *
     * @param color
     */
    public void setPenColor(int color) {
        linepath_view.setPenColor(color);
    }

    /**
     * dp转为px
     *
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    private int dip2px(Context context, float dipValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }

    /**
     * 获取画板的bitmap
     *
     * @return
     */
    public LinePathView getLinePathView() {

        return linepath_view;
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
        linepath_view.save(path, clearBlank, blank);

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

    }

    public void setOnSaveListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            save_tv.setOnClickListener(onClickListener);
        }
    }


}
