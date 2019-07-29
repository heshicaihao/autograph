package com.heshicaihao.autograph.demo.net;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import com.heshicaihao.autograph.demo.net.LogUtils;

import android.content.Context;


public class UploadPic {

//	public String requestURL = MyURL.ADDPICTURE_URL;
	public String requestURL ="";
	public String imgid;
	private int readTimeOut = 10 * 1000; // 读取超时
	private int connectTimeout = 10 * 1000; // 超时时间
	private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
	// 随机生成
	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";
	private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
	public Context context;
	public boolean mIsUpdatePic = false;
	private String mWorkId;
	private String account_id;
	private String account_token;
	private String mSelectImgid;
	private String TAG = "UploadPic";


	/**
	 * 同步上传图片
	 */
	private void uploadUserPicture(String pic_id, File file) {
		String pic_name = "a001";
		String pic_info = "";
		Map<String, String> params = new HashMap<String, String>();

		if (mIsUpdatePic) {
			String pic_old_name = "";

			params.put("account_id", account_id);
			params.put("account_token", account_token);
			params.put("work_id", mWorkId);
			params.put("pic_old_id,", mSelectImgid);
			params.put("pic_old_name", pic_old_name);
			params.put("pic_new_id", pic_id);
			params.put("pic_new_name", pic_name);
			params.put("pic_new_info", pic_info);

		} else {
			params.put("account_id", account_id);
			params.put("account_token", account_token);
			params.put("work_id", mWorkId);
			params.put("pic_id", pic_id);
			params.put("pic_name", pic_name);
			params.put("pic_info", pic_info);
		}

		String fileKey = "file";
//		upload2Net(file, fileKey, requestURL, params);
		upload2Net(bm, fileKey, requestURL, params);
//		if (AndroidUtils.isNetworkAvailable(context)) {
//			upload2Net(file, fileKey, requestURL, params);
//		} else {
//			LogUtils.logd(TAG, "无网络了");
//			return;
//		}
	}

	/**
	 * 上传 到网络
	 * 
	 * @param file
	 * @param fileKey
	 * @param RequestURL
	 * @param param
	 */
	public void upload2Net(Bitmap bm, String fileKey, String RequestURL,
			Map<String, String> param) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		String result = null;
		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(readTimeOut);
			conn.setConnectTimeout(connectTimeout);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", "utf-8"); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			/**
			 * 当文件不为空，把文件包装并且上传
			 */
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
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
					String value = param.get(key);
					sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
					sb.append("Content-Disposition: form-data; name=\"")
							.append(key).append("\"").append(LINE_END)
							.append(LINE_END);
					sb.append(value).append(LINE_END);
					params = sb.toString();
					dos.write(params.getBytes());
					// dos.flush();
				}
			}
			sb = null;
			params = null;
			sb = new StringBuffer();
			// 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
			// filename是文件的名字，包含后缀名的 比如:abc.png
			sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
			sb.append("Content-Disposition:form-data; name=\"" + fileKey
					+ "\"; filename=\"" + file.getName() + "\"" + LINE_END);
			sb.append("Content-Type:image/pjpeg" + LINE_END);
			sb.append(LINE_END);
			params = sb.toString();
			sb = null;
			dos.write(params.getBytes());
			/** 上传文件 */
			InputStream is = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int len = 0;
			@SuppressWarnings("unused")
			int curLen = 0;
			while ((len = is.read(bytes)) != -1) {
				curLen += len;
				dos.write(bytes, 0, len);
			}
			is.close();
			dos.write(LINE_END.getBytes());
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
					.getBytes();
			dos.write(end_data);
			dos.flush();
			// 获取响应码 200=成功 当响应成功，获取响应的流
			int res = conn.getResponseCode();
			if (res == 200) {
				// LogUtils.logd(TAG, "网络请求成功");
				InputStream input = conn.getInputStream();
				StringBuffer sb1 = new StringBuffer();
				int ss;
				while ((ss = input.read()) != -1) {
					sb1.append((char) ss);
				}
				result = sb1.toString();
				 LogUtils.d( "result:"+result);
				return;
			} else {
				LogUtils.d( "网络请求失败");
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
