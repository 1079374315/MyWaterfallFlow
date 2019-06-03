package com.qyt.hp.mywaterfallflow.util;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    public static Context context;

    public static final String TYPE = "hp0023";
    public static final String URL = "http://kk6923.cn/api/public/";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //753939d60f06cb601b14dc5aa820e0dd
        //753939d60f06cb601b14dc5aa820e0
//        Bmob.initialize(this, "753939d60f06cb601b14dc5aa820e0dd");//初始化
        //
        //极光
//        JPushInterface.init(this);
//        JPushInterface.setDebugMode(true);

        X5_2();


    }


    //X5注册
    private  void X5_2(){

        //非wifi情况下，主动下载x5内核
       /* QbSdk.setDownloadWithoutWifi(true);

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }
            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);*/

    }


    public static Context getContext() {
        return context;
    }
}
