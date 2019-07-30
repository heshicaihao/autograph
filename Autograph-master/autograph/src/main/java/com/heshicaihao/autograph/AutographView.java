package com.heshicaihao.autograph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heshicaihao.autograph.constants.FileConstants;
import com.heshicaihao.autograph.net.LogUtils;
import com.heshicaihao.autograph.net.RxOkHttp;
import com.heshicaihao.autograph.net.UploadPic;
import com.heshicaihao.autograph.net.UploadPic2;
import com.heshicaihao.autograph.widget.LinePathView;
import com.heshicaihao.net.RxOK.rx.ErrorBean;
import com.heshicaihao.net.RxOK.rx.ResultSubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


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
    Context context;
    private String url = "";
    private String token = "";
    private File file;
    private String type = "1";
    private String workNo = "9527";
    //    public static String HOST = "http://123.58.36.103:8086";
    public static String HOST = "http://n1.ngrok.bitbybit.work";
    //签名上传图片
    public static final String uploadPicture = "/engineer/sys/uploadPicture";

    public AutographView(Context context) {
        this( context, null );
        this.context = context;
    }

    public AutographView(Context context, AttributeSet attrs) {
        super( context, attrs );
        LayoutInflater.from( context ).inflate( R.layout.view_autograph, this );
        this.context = context;
        initView();

    }

    private void initView() {
        autograph_ll = findViewById( R.id.clean_ll );
        clean_ll = findViewById( R.id.clean_ll );
        linepath_view = findViewById( R.id.linepath_view );
        save_tv = findViewById( R.id.save_tv );

        linepath_view.setPaintWidth( 10 );
        linepath_view.setPenColor( Color.BLACK );

        clean_ll.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanOnDetails();
            }
        } );

        save_tv.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTouched()) {
//                    saveFile();

                    netPic3();
                }else {
                    Toast.makeText( context, "您没有签名~", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

    }

    private void saveFile() {
        try {

            String paths = FileConstants.getFilePath( FileConstants.getThirdPath( "autograph" ), "autograph", FileConstants.PNG );
            File file = new File( paths );
            if (file.exists()) {
                file.delete();
            }
            save( paths, false, 10 );
            if (file.exists()) {
                Toast.makeText( context, "签名文件放到：\n" + paths, Toast.LENGTH_SHORT )
                        .show();
            }
//                    netPic();
//                    netPic2();

//                    Toast.makeText( context, "签名文件放到：\n" + paths, Toast.LENGTH_SHORT ).show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void netPic3() {
        new Thread( new Runnable() {
            @Override
            public void run() {
                setCacheEnabled(true);
                Bitmap bitMap = getBitMap();
                LogUtils.d( "bitMap:" +bitMap.getHeight());
                url = HOST + uploadPicture;
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjUzNjA0NzYsInVzZXJuYW1lIjoiMTM1NDQyMjI2MzIifQ.HODQMbUX-_EthpX_ATun53v_4X_elwXZCsN6WRlnmYE";
                type = "3";
                workNo = "PA190727182203961801";
                UploadPic2.uploadUserPicture( url, token, bitMap, type, workNo );
                setCacheEnabled(false);
            }
        } ).start();
    }


    private void netPic2() {
        String paths = FileConstants.getFilePath( FileConstants.getThirdPath( "autograph" ), "autograph", FileConstants.PNG );
        file = new File( paths );
        url = HOST + uploadPicture;
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjUzNjA0NzYsInVzZXJuYW1lIjoiMTM1NDQyMjI2MzIifQ.HODQMbUX-_EthpX_ATun53v_4X_elwXZCsN6WRlnmYE";
        type = "3";
        workNo = "PA190727182203961801";
        LogUtils.d( "requestURL:" + url );
        LogUtils.d( "token:" + token );
        LogUtils.d( "file:" + file );
        LogUtils.d( "type:" + type );
        LogUtils.d( "workNo:" + workNo );


        RxOkHttp.uploadPicture( url, token, file, type, workNo )
                .subscribeOn( Schedulers.newThread() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe( new ResultSubscriber<Object>() {

                    @Override
                    public void onError(ErrorBean errorBean) {
                        LogUtils.d( "请求失败：" + errorBean.getErrMessage() );
                    }

                    @Override
                    public void onData(Object o) {
                        LogUtils.d( "请求成功" );
                        LogUtils.d( "Object:" + o.toString() );

                    }
                } );
    }


    private void netPic() {
        new Thread( new Runnable() {
            @Override
            public void run() {
                String paths = FileConstants.getFilePath( FileConstants.getThirdPath( "autograph" ), "autograph", FileConstants.PNG );
                file = new File( paths );
                url = HOST + uploadPicture;
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjUzNjA0NzYsInVzZXJuYW1lIjoiMTM1NDQyMjI2MzIifQ.HODQMbUX-_EthpX_ATun53v_4X_elwXZCsN6WRlnmYE";
                type = "3";
                workNo = "PA190727182203961801";
                UploadPic.uploadUserPicture( url, token, file, type, workNo );

            }
        } ).start();
    }

    public void setMyHeight(int height) {
        LayoutParams params = (LayoutParams) linepath_view.getLayoutParams();
//        params.width = dip2px(context, width);
        params.height = dip2px( context, height - 130 );
        linepath_view.setLayoutParams( params );
    }

    /**
     * 设置画布背景
     *
     * @param color
     */
    public void setBackColor(int color) {
        linepath_view.setBackColor( color );
    }

    /**
     * 设置画笔宽度
     *
     * @param Width
     */
    public void setPaintWidth(int Width) {
        linepath_view.setPaintWidth( Width );
    }

    /**
     * 设置画笔颜色
     *
     * @param color
     */
    public void setPenColor(int color) {
        linepath_view.setPenColor( color );
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
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics() );
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
     * 获取画板的bitmap
     *
     * @return
     */
    public void setCacheEnabled(boolean flag) {
        linepath_view.setCacheEnabled(flag);
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
        linepath_view.save( path, clearBlank, blank );

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
            save_tv.setOnClickListener( onClickListener );
        }
    }


}
