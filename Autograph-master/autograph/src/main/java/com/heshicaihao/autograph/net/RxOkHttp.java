package com.heshicaihao.autograph.net;
import com.heshicaihao.net.RxOK.exception.ServerApiException;
import com.heshicaihao.net.RxOK.interceptor.RetryIntercepter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



/**
 *heshicaihao
 * 20190716
 */

public class RxOkHttp {
    /**
     * 懒汉 安全 加同步
     * 1.私有的静态成员变量 只声明不创建
     * 2.私有的构造方法
     * 3.提供返回实例的静态方法
     */
    private static OkHttpClient okHttpClient = null;

    public static OkHttpClient getInstance() {

        if (okHttpClient == null) {
            //加同步安全
            synchronized (RxOkHttp.class) {
                if (okHttpClient == null) {
                    //okhttp可以缓存数据....指定缓存路径
//                    File sdcache = new File(Environment.getExternalStorageDirectory(),
//                            "cache");
                    //指定缓存大小
                    int cacheSize = 10 * 1024 * 1024;

                    okHttpClient = new OkHttpClient.Builder()//构建器
                            .addInterceptor(new RetryIntercepter(2))//重试
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
//                            .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))
//设置缓存
                            .build();
                }
            }

        }

        return okHttpClient;
    }


    /**
     *
     * 上传图片 请求
     * @param url
     * @param token
     * @param file
     * @param type
     * @param workNo
     * @return
     */
    public static Observable<Object> uploadPicture(final String url, final String token , final File file  , final String type , final String workNo) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                OkHttpClient client = getInstance();

                MultipartBody multipartBody =new MultipartBody.Builder()
                        .setType( MultipartBody.FORM)
                        .addFormDataPart("type",type)//添加键值对参数
                        .addFormDataPart("workNo",workNo)
                        .addFormDataPart("file",file.getName(), RequestBody.create( MediaType.parse("file/*"), file))//添加文件
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .header("Content-Type", "multipart/form-data")
                        .addHeader("X-Access-Token", token)
                        .post(multipartBody)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String text = response.body().string();
                        LogUtils.d("code:" + response.code());
                        LogUtils.d("response:\n" + text);
                        if (text == null || text.length() < 2) {
                            subscriber.onError(new ServerApiException(201, "返回数据为空"));
                            return;
                        }
                        if (response.code() != 200) {
                            subscriber.onError(new ServerApiException(201, "返回数据为空"));
                            return;
                        }
                        subscriber.onNext(text);
                        subscriber.onCompleted();
                    }
                });

            }
        })
//                .retryWhen(new RetryWithDelay(1,3000))
                .subscribeOn(Schedulers.io())//订阅操作网络请求在io线程执行
                .unsubscribeOn(Schedulers.io())
                .observeOn( AndroidSchedulers.mainThread());//返回给主线程操作界面

    }



}
