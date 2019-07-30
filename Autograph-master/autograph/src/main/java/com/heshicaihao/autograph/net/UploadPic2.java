package com.heshicaihao.autograph.net;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;


public class UploadPic2 {

    public static int readTimeOut = 10 * 1000; // 读取超时
    public static int connectTimeout = 10 * 1000; // 超时时间
    private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
    // 随机生成
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
    public Context context;
    private String TAG = "UploadPic";
    private String token;

    /**
     * 同步上传图片
     */
    public static void uploadUserPicture(String url, String token, Bitmap file, String type, String workNo) {
        String pic_name = "a001";
        String pic_info = "";
        Map<String, String> params = new HashMap<String, String>();
        params.put( "type", type );
        params.put( "workNo", workNo );
        String fileKey = "file";
        upload2Net( url, token, file, fileKey, params );
//		upload2Net(bm, fileKey, requestURL, params);
    }

    /**
     * 上传 到网络
     *
     * @param file
     * @param fileKey
     * @param param
     */
    public static void upload2Net(String requestURL, String token, Bitmap file, String fileKey,
                                  Map<String, String> param) {
        LogUtils.d( "requestURL:"+ requestURL);
        LogUtils.d( "token:"+ token);
        LogUtils.d( "file:"+ file);
        LogUtils.d( "fileKey:"+ fileKey);
        LogUtils.d( "param:"+ param.toString());
        String result = null;
        try {
            URL url = new URL( requestURL );
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout( readTimeOut );
            conn.setConnectTimeout( connectTimeout );
            conn.setDoInput( true ); // 允许输入流
            conn.setDoOutput( true ); // 允许输出流
            conn.setUseCaches( false ); // 不允许使用缓存
            conn.setRequestMethod( "POST" ); // 请求方式
            conn.setRequestProperty( "Charset", "utf-8" ); // 设置编码
            conn.setRequestProperty( "connection", "keep-alive" );
            conn.setRequestProperty( "user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)" );
            conn.setRequestProperty( "Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY );
            conn.setRequestProperty("X-Access-Token", token  );

            /**
             * 当文件不为空，把文件包装并且上传
             */
            DataOutputStream dos = new DataOutputStream( conn.getOutputStream() );
            StringBuffer sb = null;
            String params = "";
            /***
             * 以下是用于上传参数
             */
            if (param != null && param.size() > 0) {
                Iterator<String> it = param.keySet().iterator();
                while (it.hasNext()) {
                    sb = null;
                    sb = new StringBuffer();
                    String key = it.next();
                    String value = param.get( key );
                    sb.append( PREFIX ).append( BOUNDARY ).append( LINE_END );
                    sb.append( "Content-Disposition: form-data; name=\"" )
                            .append( key ).append( "\"" ).append( LINE_END )
                            .append( LINE_END );
                    sb.append( value ).append( LINE_END );
                    params = sb.toString();
                    dos.write( params.getBytes() );
                    // dos.flush();
                }
            }
            sb = null;
            params = null;
            sb = new StringBuffer();
            // 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
            // filename是文件的名字，包含后缀名的 比如:abc.png
            sb.append( PREFIX ).append( BOUNDARY ).append( LINE_END );
            sb.append( "Content-Disposition:form-data; name=\"" + fileKey
                    + "\"; filename=\"" +  "autograph.png" + "\"" + LINE_END );
            sb.append( "Content-Type:image/pjpeg" + LINE_END );
            sb.append( LINE_END );
            params = sb.toString();
            sb = null;
            dos.write( params.getBytes() );
            /** 上传文件 */
//            InputStream is = new FileInputStream( file );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            file.compress( Bitmap.CompressFormat.PNG, 100, baos);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            byte[] bytes = new byte[1024];
            int len = 0;
            @SuppressWarnings("unused")
            int curLen = 0;
            while ((len = is.read( bytes )) != -1) {
                curLen += len;
                dos.write( bytes, 0, len );
            }
            is.close();
            dos.write( LINE_END.getBytes() );
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                    .getBytes();
            dos.write( end_data );
            dos.flush();
            // 获取响应码 200=成功 当响应成功，获取响应的流
            int res = conn.getResponseCode();
            if (res == 200) {
                // LogUtils.logd(TAG, "网络请求成功");
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                BufferedReader reader = null;
                reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "UTF-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb1.append(line);
                }
                result = sb1.toString();
                LogUtils.d( "result:" + result );
                return;
            } else {
                LogUtils.d( "网络请求失败" );
                return;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }




}
