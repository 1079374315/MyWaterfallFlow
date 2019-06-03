package com.qyt.hp.mywaterfallflow.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * 工具类说明：
 * GSLS_Tool

 //GT 须依赖的包：
 implementation 'com.google.code.gson:gson:2.8.5'         //JSON 数据解析
 implementation 'com.lzy.net:okgo:3.0.4'                  //OkGo 网络框架
 implementation 'com.squareup.okhttp3:okhttp:3.12.0'      //OkHttp 网络框架
 implementation 'com.github.bumptech.glide:glide:4.8.0'   //加载图片的 glide
 implementation 'org.jsoup:jsoup:1.10.3'                  //Jsoup格式化html数据

 *
 *
 * 更新时间:2019.5.26
 *
 *
 * 小提示：(用于 AndroidStudio )
 *      收起所有的 方法: Ctrl + Shift +  - (减号)
 *      展开所有的 方法: Ctrl + Shift +  + (加号)
 *
 */
@SuppressWarnings("unchecked")
public class GT {

    //================================== 所有属于 GT 类的属性 =======================================

    @SuppressLint("StaticFieldLeak")
    private static GT gt = null;                 //定义 GT 对象
    private static Boolean LOG_TF = true;        //控制外部所有的 Log 显示
    private static Boolean GT_LOG_TF = false;    //控制内部所有的 Log 显示
    private static Boolean TOAST_TF = true;      //控制外部所有的 toast 显示
    private static Boolean GT_TOAST_TF = false;  //控制内部所有的 toast 显示
    private Context CONTEXT;                     //设置 当前动态的 上下文对象


    //================================== 提供访问 GT 属性的接口======================================

    private GT(){}//设置不可实例化
    public static GT getGT(){
        if (gt == null) {
            synchronized (GT.class) {
                if (gt == null) {
                    gt = new GT();
                }
            }
        }
        return gt;
    }//提供 GT 访问接口

    //控制外部所有的 Log 显示 获取与设置
    public Boolean getLogTf() {
        return LOG_TF;
    }
    public void setLogTf(Boolean logTf) {
        LOG_TF = logTf;
    }

    //控制外部所有的 toast 显示 获取与设置
    public Boolean getToastTf() {
        return TOAST_TF;
    }
    public void setToastTf(Boolean toastTf) {
        TOAST_TF = toastTf;
    }

    //为外部提供访问 GT Context 接口
    public void setCONTEXT(Context CONTEXT) {
        this.CONTEXT = CONTEXT;
    }

    //为外部提供 控制内部所有的 Log 显示 接口
    public Boolean getGtLogTf() {
        return GT_LOG_TF;
    }
    public void setGtLogTf(Boolean gtLogTf) {
        GT_LOG_TF = gtLogTf;
    }

    //为外部提供 控制内部所有的 toast 显示 接口
    public Boolean getGtToastTf() {
        return GT_TOAST_TF;
    }
    public void setGtToastTf(Boolean gtToastTf) {
        GT_TOAST_TF = gtToastTf;
    }

    //============================================= 提示类 =========================================

    //提示消息 Log
    public static void log_v(Object msg){
        if(LOG_TF){
            Log.v("GT_v","------- " + msg);
        }
    }
    public static void log_d(Object msg){
        if(LOG_TF){
            Log.d("GT_d","------- " + msg);
        }
    }
    public static void log_i(Object msg){
        if(LOG_TF){
            Log.i("GT_i","------- " + msg);
        }
    }
    public static void log_w(Object msg){
        if(LOG_TF){
            Log.w("GT_w","------- " + msg);
        }
    }
    public static void log_e(Object msg){
        if(LOG_TF){
            Log.e("GT_e","------- " + msg);
        }
    }
    public static void log_v(Object title, Object msg){
        if(LOG_TF){
            Log.v("GT_v",
                    "------- Run" +
                            "\n\n---------------------" + title + "------------------------\n" +
                            "                   " + msg + "\n" +
                            "---------------------" + title + "-----------------------\n\n" +
                            "------- Close"
            );
        }

    }
    public static void log_d(Object title, Object msg){
        if(LOG_TF){
            Log.d("GT_d",
                    "------- Run" +
                            "\n\n---------------------" + title + "------------------------\n" +
                            "                   " + msg + "\n" +
                            "---------------------" + title + "-----------------------\n\n" +
                            "------- Close"
            );
        }

    }
    public static void log_i(Object title, Object msg){
        if(LOG_TF){
            Log.i("GT_i",
                    "------- Run" +
                            "\n\n---------------------" + title + "------------------------\n" +
                            "                   " + msg + "\n" +
                            "---------------------" + title + "-----------------------\n\n" +
                            "------- Close"
            );
        }

    }
    public static void log_w(Object title, Object msg){
        if(LOG_TF){
            Log.w("GT_w",
                    "------- Run" +
                            "\n\n---------------------" + title + "------------------------\n" +
                            "                   " + msg + "\n" +
                            "---------------------" + title + "-----------------------\n\n" +
                            "------- Close"
            );
        }

    }
    public static void log_e(Object title, Object msg){
        if(LOG_TF){
            Log.e("GT_e",
                    "------- Run" +
                            "\n\n---------------------" + title + "------------------------\n" +
                            "                   " + msg + "\n" +
                            "---------------------" + title + "-----------------------\n\n" +
                            "------- Close"
            );
        }

    }

    //报错提示 该提示可通过 GT 提供的接口 的实例获取
    public String getLineInfo(){
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        return "报错的文件  " + ste.getFileName() + "  行号 " + ste.getLineNumber();
    }

    //消息框 Toast
    public static void toast_s(Object msg){
        if(TOAST_TF){
            if(GT.getGT().CONTEXT != null)
                Toast.makeText(GT.getGT().CONTEXT,msg + "",Toast.LENGTH_SHORT).show();
            else
            if(LOG_TF)//设置为默认输出日志
                log_e("GT_bug","消息框错误日志：你没有为 Context 进行赋值 ，却引用了 Toast 导致该功能无法实现。");
        }
    }
    public static void toast_l(Object msg){
        if(TOAST_TF){
            if(GT.getGT().CONTEXT != null)
                Toast.makeText(GT.getGT().CONTEXT,msg + "",Toast.LENGTH_SHORT).show();
            else
            if(LOG_TF)//设置为默认输出日志
                log_e("GT_bug","消息框错误日志：你没有为 Context 进行赋值 ，却引用了 Toast 导致该功能无法实现。");
        }
    }
    public static void toast_s(Context context,Object msg){
        if(TOAST_TF)
            Toast.makeText(context,msg + "",Toast.LENGTH_SHORT).show();
    }
    public static void toast_l(Context context,Object msg){
        if(TOAST_TF)
            Toast.makeText(context,msg + "",Toast.LENGTH_LONG).show();
    }

    //AlertDialog.Builder 对话框类
    public static class GT_AlertDialog extends AlertDialog.Builder{

        public String resultButtonDialog = null;
        public String resultListDialog = null;
        public String resultSingleChoiceListDialog = null;
        public String[] resultMultiChoiceDialog = null;

        public GT_AlertDialog(Context context) {
            super(context);
        }
        //设置按钮的对话框
        public AlertDialog.Builder dialogButton(int img, String title, String message, final String[] texts){
            setTitle(title).setIcon(img).setMessage(message);   //设置 标题、图标、消息
            if(texts != null){
                resultButtonDialog = null;
                if(texts.length >= 1)
                    setNegativeButton(texts[0], new DialogInterface.OnClickListener() {
                        // 第一个按钮
                        @Override
                        public void onClick(DialogInterface dialog,int which) {
                            resultButtonDialog = texts[0];
                        }
                    });
                if(texts.length >= 2)
                    setPositiveButton(texts[1], new DialogInterface.OnClickListener() {
                        //第二个按钮
                        @Override
                        public void onClick(DialogInterface dialog,int which) {
                            resultButtonDialog = texts[1];
                        }
                    });
                if(texts.length >= 3)
                    setNeutralButton(texts[2], new DialogInterface.OnClickListener() {  //中间
                        //第三个按钮
                        @Override
                        public void onClick(DialogInterface dialog,int which) {
                            resultButtonDialog = texts[2];
                        }
                    });
            }
            return this;
        }

        //设置列表选项对话框
        public AlertDialog.Builder dialogList(int img, String title,final String[] texts){

            setIcon(img).setTitle(title);
            if(texts != null)
                setItems(texts, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resultListDialog = texts[which];
                    }
                }); //添加列表项
            return this;
        }

        //设置单选列表选项对话框
        public AlertDialog.Builder dialogSingleChoiceList(int img, String title,String buttonName, final String[] texts){
            setIcon(img).setTitle(title);
            if(texts != null)
                setSingleChoiceItems(texts, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resultSingleChoiceListDialog = texts[which];
                    }
                });//添加列表项
            if(buttonName != null)
                setPositiveButton(buttonName,null);//设置按钮
            return this;
        }

        //设置多选列表对话框
        public AlertDialog.Builder dialogMultiChoice(int img, String title,String buttonName,final String[] texts){

            final boolean[] checkedItems = new boolean[texts.length];   //初始化选中状态
            resultMultiChoiceDialog = new String[texts.length];         //初始化字符串结果

            for(int i = 0; i < checkedItems.length; i++) checkedItems[i] = false;   //初始化默认选项
            setIcon(img).setTitle(title);//设置 图标、标题

            if(texts != null)
                setMultiChoiceItems(texts, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which] = isChecked;//改变被操控列表项的状态
                    }
                });//添加列表项

            if(buttonName != null)
                setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++){
                            if(checkedItems[i]){
                                resultMultiChoiceDialog[i] = texts[i];
                            }
                        }
                    }
                });

            return this;
        }

        //设置自定义对话框
        public AlertDialog  dialogView(int img, String title,boolean ShieldngExternal,final int darkDegrees,int x,int y,int layout){
            AlertDialog alertDialog = create();// 创建对话框
            // 设置参数
            if(title != null) alertDialog.setTitle(title);//设置标题

            if(img != 0) alertDialog.setIcon(img);  //设置图标

            if(darkDegrees != -1) alertDialog.getWindow().setDimAmount(darkDegrees);//设置昏暗度为0 则表示为透明， 如果是 -1 则是默认

            alertDialog.setCancelable(ShieldngExternal);       //设置点击外面不会消失 true为点击外面可以被取消，false 为点击外面不可被取消
            // 获取布局
            view = View.inflate(getContext(), layout,null);
            alertDialog.setView(view);

            //修改 自定义对话框的显示位置
            android.view.Window dialogWindow = alertDialog.getWindow();/*随意定义个Dialog*/
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();/*实例化Window*/
            /*实例化Window操作者*/
            lp.x = x; // 新位置X坐标
            lp.y = y; // 新位置Y坐标
            dialogWindow.setAttributes(lp);


            return alertDialog;
        }
        private View view;  //定义 View
        public View getView(){
            return view;
        }




    }

    //Notification 通知类
    public static class GT_Notification{

        private static int NOTIFYID = 0x1997; //通知id
        private static String CHANEL_ID = "com.gsls.king";
        private static String CHANEL_DESCRIPTION = "GT_Android 描述";
        private static String CHANEL_NAME = "GT_Android复习";

        public void setNotifyId(int NotifyId){
            NOTIFYID = NotifyId;
        }
        public void setChanelId(String ChanelId){
            CHANEL_ID = ChanelId;
        }
        public void setChanelDescription(String ChanelDescription){
            CHANEL_DESCRIPTION = ChanelDescription;
        }
        public void setChanelName(String ChanelName){
            CHANEL_NAME = ChanelName;
        }

        private Activity activity;
        public GT_Notification(Activity activity){
            this.activity = activity;
        }

        public NotificationManagerCompat sendingNotice(int icon, String title, String text, int time, boolean voiceTF, boolean autoCancel, Class<?> cla){

            /**
             * 由于 Notification.Builder 仅支持 Android 4.1及之后的版本，为了解决兼容性问题， Notification.Builder 仅支持 API 26 与 26 之前的版本
             * Google 在 Android Support v4 中加入了 NotificationCompat.Builder 类
             */
            String channelId = createNotificationChannel(activity.getApplicationContext());//创建Notification Channel
            NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, channelId);//创建Notification并与Channel关联

            builder.setSmallIcon(icon);//设置通知图标
            builder.setAutoCancel(autoCancel);//设置通知打开后自动消失
            builder.setContentTitle(title);//设置标题
            builder.setContentText(text);//设置内容
            if(time == 0) builder.setWhen(System.currentTimeMillis());//设置系统当前时间为发送时间
            else builder.setWhen(time);//设置用户设置的发送时间
            if(voiceTF) builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);//设置默认的声音与默认的振动

            if(cla != null){    //如果 cla 不为空就设置跳转的页面
                Intent intent = new Intent(activity, cla);
                PendingIntent pi = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentIntent(pi);//设置通知栏 点击跳转
            }

            //发布通知
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(activity.getApplicationContext());
            notificationManagerCompat.notify(NOTIFYID, builder.build());
            return notificationManagerCompat;
        }

        private String createNotificationChannel(Context context) {
            // O (API 26)及以上版本的通知需要NotificationChannels。
            if (context != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                // 初始化NotificationChannel。
                NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID,CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.setDescription(CHANEL_DESCRIPTION);

                // 向系统添加 NotificationChannel。试图创建现有通知
                // 通道的初始值不执行任何操作，因此可以安全地执行
                // 启动顺序
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);

                return CHANEL_ID;
            } else {
                return null; // 为pre-O(26)设备返回 null
            }
        }

    }

    //跳转 Activity
    public static void startAct(Class activityClass){
        if(GT.getGT().CONTEXT != null){
            GT.getGT().CONTEXT.startActivity(new Intent(GT.getGT().CONTEXT, activityClass));//跳转 Activity
        }else {
            GT.log_e(getGT().getLineInfo(),"跳转 Activity 失败，CONTEXT 为 null 无法进行相应的 Activity 跳转");
        }
    }
    public static void startAct(Intent intent){

        if(GT.getGT().CONTEXT != null && intent != null){
            GT.getGT().CONTEXT.startActivity(intent);//跳转 Activity
        }else {
            GT.log_e(getGT().getLineInfo(),"跳转 Activity 失败，CONTEXT 或 Intent为 null 无法进行相应的 Activity 跳转");
        }
    }
    public static void startAct(Context context, Class activityClass){
        if(context != null){
            context.startActivity(new Intent(context, activityClass));//跳转 Activity
        }
    }
    public static void startAct(Context context, Intent intent){
        if(context != null && intent != null){
            context.startActivity(intent); //跳转 Activity
        }else {
            GT.log_e(getGT().getLineInfo(),"跳转 Activity 失败，CONTEXT 或 Intent为 null 无法进行相应的 Activity 跳转");
        }
    }

    //============================================= 数据存储类 =====================================

    //数据持久化 SharedPreferences
    public static class GT_SharedPreferences{

        private Context context;
        public void commit(){ sp_e.apply();}  // 使用 apply 手动提交 如果提交后还有后续操作，建议使用 apply，先会写入内存，然后再异步写入磁盘
        public void clear(){sp_e.clear();sp_e.commit();}//清空    //如果使用 commit 来提交事务，是直接写入磁盘 ，如果需要频繁的提交的话， apply 的性能会优于 commit
        private SharedPreferences sp; public SharedPreferences getSharedPreferences(){return sp;}           //获取 SharedPreferences
        private SharedPreferences.Editor sp_e;  public SharedPreferences.Editor getEditor(){return sp_e;}   //获取 SharedPreferences.Editor
        private boolean commit = false;             //定义是否自动提交
        public static final int PRIVATE = 0;        //只有本应用可读写
        public static final int PROTECTED = 1;      //其他应用可以只读
        public static final int PUBLIC = 2;         //其他应用可以读写
        private Gson gson = new Gson();             //是俩胡 Gson 对象

        //初始化
        public GT_SharedPreferences(Context context,String SPName,int permissions,boolean commit) {
            this.context = context;
            this.commit = commit;
            sp = context.getSharedPreferences(SPName, permissions);//打开 或 创建 SharedPreferences
            sp_e = sp.edit();//让userData处于编辑状态
        }
        //增
        public GT_SharedPreferences save(String key, Object object){
            boolean TF = true;//监测保存状态是否正常
            if(object instanceof String){
                sp_e.putString(key,object.toString());
            }else if(object instanceof Integer){
                sp_e.putInt(key,(Integer) object);
            }else if(object instanceof Long){
                sp_e.putLong(key,(Long)object);
            }else if(object instanceof Float){
                sp_e.putFloat(key,(Float)object);
            }else if(object instanceof Boolean){
                sp_e.putBoolean(key,(Boolean) object);
            }else if(object instanceof Set){
                sp_e.putStringSet(key,(Set)object);
            }else{
                if(GT_LOG_TF) log_v(context,"进行对象保存");
                String json = new Gson().toJson(object);
                String json_class = object.getClass().toString();
                sp_e.putString(key,json);                           //保存对象的 Json 数据
                sp_e.putString(key+"_class",json_class);            //保存对象的 class 数据
            }
            if(commit && TF) sp_e.apply();  //如果设置了自动提交 并且 保存状态正常 即可自定提交
            return this;

        }
        //删
        public SharedPreferences.Editor delete(String key){
            if(query(key) != null){
                sp_e.remove(key);
                if(commit)sp_e.apply();
            }else {
                if(GT_LOG_TF) log_v("删除失败  当前 sp 中无此 key");
            }
            return sp_e;
        }
        //改
        public GT_SharedPreferences updata(String key, Object object){
            if(query(key) != null){
                if(GT_LOG_TF)
                    log_v(context,"进入到 updata 查询的数据不为null");
                save(key,object);
            }
            return this;
        }
        //查
        public Object query(String key){
            Object obj = null;
            try{
                obj = sp.getInt(key,0);
            }catch (ClassCastException e1){
                if(GT_LOG_TF)
                    log_v(context,"Int 数据装换异常");
                try{
                    String str_class = sp.getString(key+"_class",null);     //获取对象 class 数据
                    String str = sp.getString(key,null);                          //获取对象 Json  数据
                    if(str_class == null){      //如果 class 数据为空
                        obj = str;              //普通的 Json 数据
                    }
                    else{
                        Object object_class = getObj(str_class);    //通过对象的 class 反射出 实例对象
                        obj = gson.fromJson(str,object_class.getClass());     //通过 Gson 与 实例对象 获取相应的 Object 对象
                    }
                }catch (ClassCastException e2){
                    if(GT_LOG_TF)
                        log_v(context,"String 数据装换异常");
                    try{
                        obj = sp.getLong(key,0);
                    }catch (ClassCastException e3){
                        if(GT_LOG_TF)
                            log_v(context,"Long 数据装换异常");
                        try{
                            obj = sp.getFloat(key,0f);
                        }catch (ClassCastException e4){
                            if(GT_LOG_TF)
                                log_v(context,"Float 数据装换异常");
                            try{
                                obj = sp.getBoolean(key,false);
                            }catch (ClassCastException e5){
                                if(GT_LOG_TF)
                                    log_v(context,"Boolean 数据装换异常");
                                try{
                                    obj = sp.getStringSet(key,null);
                                }catch (ClassCastException e6){
                                    if(GT_LOG_TF)
                                        log_v(context,"StringSet 数据装换异常");
                                    obj = null;
                                }
                            }
                        }
                    }
                }
            }
            return obj;
        }

        //查 List
        public Object queryList(String key,Type type){
            /**
             *  字符串 转 List 参数说明：
             *
             * key：与之前 save 是的 key
             *  type：
             *  Type type = new TypeToken<List<***Bean>>(){}.getType()
             *
             *  所以你只要传一个 new TypeToken<List<***Bean>>(){}.getType() 即可
             *  ***Bean 解释:   List<***Bean>   是之前 save 时 List的泛型
             *
             */
            Object obj = null;
            String str_class = sp.getString(key+"_class",null);     //获取对象 class 数据
            String str = sp.getString(key,null);                          //获取对象 Json  数据
            if(str_class != null && str_class.equals("class java.util.ArrayList")){      //如果 class 数据为空
                obj = gson.fromJson(str,type);//字符串转 List
            }
            return obj;
        }

        //通过 对象的.class 属性反射对象
        private Object getObj(String objectClass){
            Object obj = null;
            String[] strs = objectClass.split(" ");
            String str = strs[1];
            Class<?> clazz = getClass();
            try {
                clazz = Class.forName(str);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            try {
                obj = clazz.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return obj;		//返回实例化好的类

        }
        //获取所有
        public Map<String, ?> queryAll(){
            return sp.getAll();
        }

    }

    //内部存储 IO
    public static class GT_IO{

        /**
         * 使用实例：
         * 第一步：
         *          GT.GT_IO io = new GT.GT_IO(this);//创建 IO 对象
         * 第二步：
         *          io.save(editText.getText().toString(),"King");//保存数据
         * 第三步：
         *          String king = io.query("King"); //获取数据
         */

        private Context context;

        public GT_IO(Context context){
            this.context = context;
        }

        public GT_IO save(String saveData,String dataName){

            FileOutputStream fos = null;//文件输出流
            try {
                fos = context.openFileOutput(dataName,context.MODE_PRIVATE);//获取文件输出流对象
                fos.write(saveData.getBytes());//保存备忘信息
                fos.flush();//清除缓存
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos != null){
                    try {
                        fos.close();//关闭输出流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return this;
        }

        public String query(String dataName){
            FileInputStream fis = null;//文件输入流对象
            String data = null;
            byte[] buffer = null;
            try {
                fis = context.openFileInput(dataName);//获得文件输入流对象
                buffer = new byte[fis.available()];//实例化字节数组
                fis.read(buffer);//从输入流中读取数据
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fis != null){
                    try {
                        fis.close();//关闭输入流对象
                        data = new String(buffer);//把字节数组中的数据转换为字符串
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return data;
        }

    }

    //外部存储 File
    public static class GT_File{

        /**
         * 使用实例：
         * 第一步：
         *          GT.GT_File file = new GT.GT_File();//创建 File 对象
         * 第二步：
         *          file.save("第一章:","/我的小说/武侠小说/","斗罗大陆.txt");//保存数据
         * 第三步：
         *          String query = file.query("/我的小说/武侠小说/", "斗罗大陆.txt"); //获取数据
         */

        public void save(String saveData,String savePaht, String fileName){

            File fileNull = new File(Environment.getExternalStorageDirectory() + savePaht);//实例化文件对象
            if(!fileNull.exists()){
                fileNull.mkdirs();
            }

            File file = new File(Environment.getExternalStorageDirectory() + savePaht,fileName);//实例化文件对象

            FileOutputStream fos = null;//文件输出流
            try {
                fos = new FileOutputStream(file);//获取文件输出流对象
                fos.write(saveData.getBytes());//保存备忘信息
                fos.flush();//清除缓存
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos != null){
                    try {
                        fos.close();//关闭输出流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        public String query(String queryPaht, String fileName){

            File fileNull = new File(Environment.getExternalStorageDirectory() + queryPaht);//实例化文件对象
            if(!fileNull.exists()){
                fileNull.mkdirs();
            }

            File file = new File(Environment.getExternalStorageDirectory() + queryPaht,fileName);//实例化文件对象


            FileInputStream fis = null;//文件输入流对象
            byte[] buffer = null;
            String data = null;
            try {
                fis = new FileInputStream(file);//获得文件输入流对象
                buffer = new byte[fis.available()];//实例化字节数组
                fis.read(buffer);//从输入流中读取数据
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fis != null){
                    try {
                        fis.close();//关闭输入流对象
                        data = new String(buffer);//把字节数组中的数据转换为字符串
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return data;
        }

    }

    //Hibernate SQL 封装对于 SQList 的操作
    public static class Hibernate{

        // 增    INSERT INTO ROLE(NAME,AGE,SEX) VALUES(:name,:age,:sex)
        // 删    DELETE 表名 WHERE NAME = '陈启申'
        // 改    UPDATE 表名 SET NAME = ? WHERE ID = ?
        // 查    SELECT * FROM ROLE WHERE id > ?
        //str    String json = new Gson().toJson(object);
        //obj    obj = new Gson().fromJson(str,object_class.getClass());     //通过 Gson 与 实例对象 获取相应的 Object 对象
        //       sp = context.getSharedPreferences(SQLName, AppCompatActivity.MODE_WORLD_WRITEABLE);//打开 sp
        //       sp_e = sp.edit();//让userData处于编辑状态

        private Context context;
        private Map<Object,Object> sqlMap = new HashMap<>();
        private final static String DATABASE = "GT_DATABASE";

        //初始化 Hibernate
        public Hibernate(Context context) {
            this.context = context;

        }

        //创建数据库
        public void createDatabase(String databaseName,Object password){

        }

        //修改数据库
        public void alterDatabase(String formerName,String newName,Object password){

        }

        //删除数据库
        public void dropDatabase(String databaseName){

        }

        //打开数据库
        public void openDatabase(String databaseName,Object password){

        }

    }





    //=========================================== 网络类 =========================================

    //network 网络类
    public class Network{

        //监听网络状态 true 网络正常  false 网络异常
        public boolean netWorkStatus(Context context) {
            ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cwjManager.getActiveNetworkInfo() != null){
                return cwjManager.getActiveNetworkInfo().isAvailable();
            }
            return false;
        }

        //获取手机 IP 地址
        public String getIPAddress(Context context) {
            NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                    try {
                        //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                            NetworkInterface intf = en.nextElement();
                            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                                InetAddress inetAddress = enumIpAddr.nextElement();
                                if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                    return inetAddress.getHostAddress();
                                }
                            }
                        }
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }


                } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                    int ipAddress1 = wifiInfo.getIpAddress();
                    String ipAddress = (ipAddress1 & 0xFF) + "." +
                            ((ipAddress1 >> 8) & 0xFF) + "." +
                            ((ipAddress1 >> 16) & 0xFF) + "." +
                            (ipAddress1 >> 24 & 0xFF);
                    return ipAddress;
                }
            } else {
                //当前无网络连接,请在设置中打开网络
                if(GT.GT_LOG_TF){
                    GT.log_v(getGT().getLineInfo(),"当前无网络");
                }
            }
            return null;
        }

    }

    //JSON 接口解析
    public static class JSON {

        private String string;
        private int code = 0;
        private String msg = null;
        private Object data = null;
        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
        public String getData() {
            return data.toString();
        }

        //初始化 json 数据
        public JSON(String string) {
            this.string = string;
            try {
                JSONObject jsonObject = new JSONObject(string);
                code = jsonObject.getInt("code");
                data = jsonObject.get("data");
                msg = jsonObject.getString("msg");
            } catch (JSONException e) {
                if(GT_LOG_TF)
                    log_v("当前 JSON 数据中有些节点并不存在,请谨慎使用!  【" + getGT().getLineInfo() + "】");
//                e.printStackTrace();
            }
        }

        /*********************************  根据 Bean 获取数据*************************************/
        //根据 bean 类获取 bean 对象
        public Object getBean(Class<?> aClass){
            Object o = null;
            try{
                o = new Gson().fromJson(string, aClass);
            }catch (JsonSyntaxException exception){
                log_e(getGT().getLineInfo(),"你的 JSON 解析类型不匹配，请检查  " + aClass + "  是否与请求的Json数据一致！");
            }
            return o;
        }


        /*********************************  没有 Bean 获取数据*************************************/
        /**
         *
         * 用法:
         * 第一步：将请求的数据放入   GT.JSON json = new GT.JSON(“请求的数据”);
         * 第二步：初始化 JSON 数据  json.initBeanData(json.getData());
         * 第三步：获取 list 集合    JSONArray list = json.getJSONArray("list");     //获取 list 节点
         * 第四步：获取 list 内数据  Object author = json.getJSONObject(list, “节点名”, “集合list的索引”);
         *
         * 注意:
         * 如果请求的数据是 "data":{}  就用 get()方法 获取 data 再就进行初始化后 获取里面的值
         * 如果请求的数据是 "list": [] 就用 getJSONObject() 获取 List<?>
         *
         */
        private JSONObject jsonObject;
        private JSONArray jsonArray;

        public JSON initBeanData(String data){
            try {
                jsonObject = new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return this;
        } //初始化 JSON 数据

        public Object get(String key){
            Object o = null;
            if(jsonObject != null){
                try {
                    o = jsonObject.get(key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                log_e(getGT().getLineInfo(),"没有初始化 JSON 数据，无法进行 无 bean 数据解析");
            }

//            GT.log_e("在转 data 数据之前:" + data);
            o = rplStr(o.toString(),"\\","");//忽略掉转义符
//            GT.log_e("转 data 数据之后:" + data);

            return o;
        }   //获取普通的值 返回数据前会进行 忽略掉转义符

        public String rplStr(String str1,String str2,String str3){
            String strtmp="";
            int i=0,f;
            for(i=0;;i+=str2.length()){
                f=str1.indexOf(str2,i);
                if (f==-1) {
                    strtmp+=str1.substring(i);
                    break;
                }else{
                    strtmp+=str1.substring(i,f);
                    strtmp+=str3;
                    i=f;
                }
            }
            return strtmp;
        }//去掉转义符

        public JSONArray getJSONArray(String string){
            try {
                jsonArray = new JSONArray(string);
            } catch (JSONException e) {
                log_e(getGT().getLineInfo(),"没有初始化 JSON 数据，无法进行 无 bean 数据解析");
            }

            return jsonArray;
        }   //获取 对象数组

        public Object getJSONObject(JSONArray list,String key,int index){
            JSONObject jsonObject = null;
            Object o = null;
            try {
                jsonObject = (JSONObject) list.get(index);  //获取当前索引下
                o = jsonObject.get(key);        //key 值 的对象
            } catch (JSONException e) {
                log_e(getGT().getLineInfo(),"JSON 数据解析异常，无法通过 没有初始化的 JSON 数据进行解析节点");
            }
            return o;
        }   //获取 JSON 对象

        public static JSONArray addJSONArray(JSONArray jsonArray,JSONArray new_jsonArray){

            for(int i = 0; i < new_jsonArray.length(); i++){
                try {
                    Object o = jsonArray.get(i);
                    jsonArray.put(o);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return  jsonArray;
        }//添加新的 json 数据

        public static JSONArray clear(JSONArray jsonArray){
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                JSONObject obj = null;
                try {
                    obj = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                obj.remove("key");
            }
            return jsonArray;
        }



    }

    //OkGo 网络请求框架
    public static class OkGo{

        private Map<String,String> map;
        private String url;


        //初始化 map 并进行添加参数
        public OkGo addParameter(String key,String value){
            if(map == null){
                map = new HashMap<>();//初始化 map
            }else{
                if(!map.containsKey(key)){//如果当前 map 中没有此key
                    map.put(key,value);
                }
            }
            return this;
        }

        public OkGo(String url,Map<String,String> map){
            this.url = url;
            this.map = map;
        }

        public OkGo(String url){
            this.url = url;
        }

        public void loadData(StringCallback stringCallback){
            if(map == null){
                com.lzy.okgo.OkGo
                        .<String>post(url)
                        .execute(stringCallback);

            }else {
                com.lzy.okgo.OkGo
                        .<String>post(url)
                        .params(map)
                        .execute(stringCallback);
            }
        }
    }

    //okHttp 网络请求框架
    public static class OkHttp{

        private OkHttpClient mOkHttpClient = null;
        private Call call = null;
        private String url = null;
        private Map<String,String> map = null;

        public OkHttp(String url,Map<String,String> map){
            mOkHttpClient = new OkHttpClient();
            this.url = url;
            this.map = map;
        }

        public OkHttp(String url){
            mOkHttpClient = new OkHttpClient();
            this.url = url;
        }

        public void loadDAta(Callback callback){
            if(url != null){
                Request request = null;
                if(map != null && map.size() > 0){
                    FormBody.Builder builder = new FormBody.Builder();
                    for(String key:map.keySet()){
                        builder.add(key,map.get(key));
                    }
                    RequestBody formBody = builder.build();
                    request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                }else{
                    request = new Request.Builder()
                            .url(url)
                            .build();
                }
                call = mOkHttpClient.newCall(request);
                call.enqueue(callback);

            }
        }

    }

    //将网页图片 利用照腾讯X5 自定适应屏幕大小
    public static class HtmlFormat {
        public static String getNewContent(String htmltext){
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }
            return doc.toString();
        }
    }





    //============================================= 小工具类 =======================================

    //data 日期类
    public static class GT_Date{

        private String time;        //定义返回的 时间整体字符串
        private String[] times;     //定义分割后产生的 年月日 / 时分秒 数组
        private String[] ymd;       //定义分割后产生的 年月日 数组
        private String[] hms;       //定义分割后产生的 时分秒 数组

        //初始化时间 数据
        public GT_Date(){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            time = df.format(new Date());   //获取当时间
            times = time.split(" ");    //分割时间 年月日 / 时分秒 数组
            ymd = times[0].split("-");  //分割年月日 数组
            hms = times[1].split(":");  //分割时分秒 数组
        }

        //获取当前星期
        public String getWeekOfDate() {
            String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;
            return weekDays[w];
        }
        //获取当前时间
        public String getTime(){
            return time;
        }
        //获取当前 年月日
        public String getYearMonthDay(){
            return times[0];
        }
        public String getYear(){
            return ymd[0];
        }    //获取年
        public String getMonth(){
            return ymd[1];
        }   //获取月
        public String getDay(){
            return ymd[2];
        }     //获取日
        //获取当前 时分秒
        public String getHourMinuteSecond(){
            return times[1];
        }
        public String getHour(){
            return hms[0];
        }    //获取时
        public String getMinute(){
            return hms[1];
        }  //获取分
        public String getSecond(){
            return hms[2];
        }  //获取秒

        //时间戳转 时间
        public String toTime(String dataTime){
            SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            long myTime= Long.parseLong(dataTime);
            long lt = new Long(myTime*1000);//返回来的时间戳1476929029是毫秒，这里要乘1000才是正确的北京时间
            Date date = new Date(lt);
            String time=formatter.format(date);
            return time;
        }

        //时分秒
        public String toTime_hms(String dataTime){
            SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            long myTime= Long.parseLong(dataTime);
            long lt = new Long(myTime*1000);//返回来的时间戳1476929029是毫秒，这里要乘1000才是正确的北京时间
            Date date = new Date(lt);
            String time=formatter.format(date);
            time = time.substring(time.length()-8,time.length());
            return time;
        }

        //离现在过去几小时
        public String toPastTime(String dataTime){
            SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            long myTime= Long.parseLong(dataTime);
            long lt = new Long(myTime*1000);//返回来的时间戳1476929029是毫秒，这里要乘1000才是正确的北京时间
            Date date = new Date(lt);
            String time=formatter.format(date);
            time = time.substring(time.length() - 8,time.length());
            time = time.substring(0,2);
            String hour = getHour();
            int timeInt = Integer.parseInt(time);
            int hourInt = Integer.parseInt(hour);
            int showTime;

            if(hourInt < timeInt){
                timeInt = 24 - timeInt; //一天减去 当时发送的时间
                showTime = hourInt + timeInt;
            }else{
                showTime = hourInt - timeInt;
            }

            //判断当前过去的时间是否大于0 最小为 1小时前
            if(showTime > 0){
                time = showTime + "小时前";
            }else{//否则 进行分钟判断

                time=formatter.format(date);
                time = time.substring(time.length() - 8,time.length());
                time = time.substring(3,5);

                timeInt = Integer.parseInt(time);
                hourInt = Integer.parseInt(getMinute());
                showTime = hourInt - timeInt;

                if(showTime > 0){
                    time = showTime + "分钟前";
                }else{
                    time = "刚刚";
                }
            }

            return time;
        }

        //时间戳转 年月日
        public String toYearMonthDay(String dataTime){
            SimpleDateFormat formatter =new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
            long myTime= Long.parseLong(dataTime);
            long lt = new Long(myTime*1000);//返回来的时间戳1476929029是毫秒，这里要乘1000才是正确的北京时间
            Date date = new Date(lt);
            String time=formatter.format(date);
            return time;
        }
        //时间戳转 北京时间
        public String toBeijingTime(String dataTime){
            SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            long myTime= Long.parseLong(dataTime);
            long lt = new Long(myTime*1000);//返回来的时间戳1476929029是毫秒，这里要乘1000才是正确的北京时间
            Date date = new Date(lt);
            String time=formatter.format(date);
            return time;
        }

    }

    //分享功能
    public static class GT_Share{

        private Activity activity;
        public GT_Share(Activity activity){
            this.activity = activity;
        }

        public void senText(String title,String content){
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, content);
            shareIntent = Intent.createChooser(shareIntent, title);
            activity.startActivity(shareIntent);
        }


    }

    //图片优化类
    public static class ImageOptimize{

        private Activity activity;

        public ImageOptimize(){}

        public ImageOptimize(Activity activity){
            this.activity = activity;
        }

        public void loadImage(Context context, Object ImageResources,ImageView imageView){
            Glide.with(context).asDrawable().load(ImageResources).into(imageView);
        }

    }







    //============================================= UI类 ===========================================

    //Window 窗体类
    public static class Window{

        private WindowManager wm;
        private Activity activity;
        private View view = null;
        private AppCompatActivity appCompatActivity;

        //初始化
        public Window(AppCompatActivity appCompatActivity){
            this.activity = appCompatActivity;
            this.appCompatActivity = appCompatActivity;
            wm = appCompatActivity.getWindowManager();
        }
        //获取屏幕 宽度
        public int getWindowWidth(){
            int width = wm.getDefaultDisplay().getWidth();
            return width;
        }
        //获取屏幕 高度
        public int getWindowHeight(){
            int height = wm.getDefaultDisplay().getHeight();
            return height;
        }
        //隐藏  ActionBar
        public void hideActionBar (){
            appCompatActivity.getSupportActionBar().hide();
        }
        //关闭虚拟按钮,并设置全屏
        public View closeVirtualButton() {
            /**
             * 如果想全部隐藏虚拟按键，只需要在build.prop中增加qemu.hw.mainkeys=1即可。build.prop在你编译出的文件系统的system目录下。前提需要root权限。
             * 使用案列:new GT.Window(this).closeVirtualButton();
             */
            if (activity != null) view = activity.getWindow().getDecorView();
            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
                view.setSystemUiVisibility(View.GONE);
            } else if (Build.VERSION.SDK_INT >= 19) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
            }

            /**
             * 该方法请在 Activity 中 onCreate 方法里的 super.onCreate(savedInstanceState); 之下 setContentView(R.layout.***); 之前使用
             * 使用案列：new GT.Window(this).setFullScreen();
             */
            activity.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            return view;
        }



    }

    //设置组件触摸放大
    public static class ViewTouchMagnify {
        private int viewWidth;		//保存按钮宽度
        private int viewHeight;		//保存按钮高度
        //为按钮设置触摸事件
        @SuppressLint("ClickableViewAccessibility")
        public void touchZoomInView(final View view){
            //设置按钮触摸事件
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    if(arg1.getAction() == MotionEvent.ACTION_DOWN){//如果用户手指触摸屏幕
                        viewWidth = view.getWidth();		//保存按钮的宽度
                        viewHeight = view.getHeight();		//保存按钮的高度
//                        view.setTextSize(18);								//设置按钮放大时字体大小
                        layoutParams.width = viewWidth + 20;				//设置按钮放大时的宽度
                        layoutParams.height = viewHeight + 10;			//设置按钮放大时的高度
                    }
                    else if(arg1.getAction() == MotionEvent.ACTION_UP){//如果用户手指离开屏幕
//                        button.setTextSize(15);							//设置按钮为原来字体大小
                        layoutParams.width = viewWidth;				//设置按钮为原来的宽度
                        layoutParams.height = viewHeight;				//设置按钮为原来的高度
                    }
                    view.setLayoutParams(layoutParams);	//提交事务
                    return false; //设置为未完成消耗掉的时间   如果将此返回为     true  那么按钮的  单击事件将会被屏蔽掉
                }
            });

        }

    }

    //封装 Fragment 类
    public static class GT_Fragment{

        /**
         *  事务的方法
         * add(Fragment fragment, String tag) //  调用add(int, Fragment, String),填入为0的containerViewId.
         * add(int containerViewId, Fragment fragment) // 调用add(int, Fragment, String),填入为null的tag.
         * add(int containerViewId, Fragment fragment, String tag) // 向Activity中添加一个Fragment.
         * addSharedElement(View sharedElement, String name) // 添加共享元素
         * addToBackStack(String name) // 将事务添加到回退栈
         * attach(Fragment fragment) // 重新关联Fragment（当Fragment被detach时）
         * commit() // 提交事务
         * commitAllowingStateLoss() // 类似commit()，但允许在Activity状态保存之后提交（即允许状态丢失）。
         * commitNow() // 同步提交事务
         * commitNowAllowingStateLoss() // 类似commitNow()，但允许在Activity状态保存之后提交（即允许状态丢失）。
         * detach(Fragment fragment) // 将fragment保存的界面从UI中移除
         * disallowAddToBackStack() // 不允许调用addToBackStack(String)操作
         * hide(Fragment fragment) // 隐藏已存在的Fragment
         * isAddToBackStackAllowed() // 是否允许添加到回退栈
         * isEmpty() // 事务是否未包含的任何操作
         * remove(Fragment fragment) // 移除一个已存在的Fragment
         * replace(int containerViewId, Fragment fragment) // 调用replace(int, Fragment, String)填入为null的tag.
         * replace(int containerViewId, Fragment fragment, String tag) // 替换已存在的Fragment
         * setBreadCrumbShortTitle(int res) // 为事务设置一个BreadCrumb短标题
         * setBreadCrumbShortTitle(CharSequence text) // 为事务设置一个BreadCrumb短标题，将会被FragmentBreadCrumbs使用
         * setBreadCrumbTitle(int res) // 为事务设置一个BreadCrumb全标题，将会被FragmentBreadCrumbs使用
         * setBreadCrumbTitle(CharSequence text) // 为事务设置一个BreadCrumb全标题
         * setCustomAnimations(int enter, int exit, int popEnter, int popExit) // 自定义事务进入/退出以及入栈/出栈的动画效果
         * setCustomAnimations(int enter, int exit) // 自定义事务进入/退出的动画效果
         * setTransition(int transit) // 为事务设置一个标准动画
         * setTransitionStyle(int styleRes) // 为事务标准动画设置自定义样式
         * show(Fragment fragment) // 显示一个被隐藏的Fragment
         *
         */

        /**
         *
         * 注意事项：
         * 1.初始化与构造方法 建议只调用一次
         * 2.在初始化与添加新Fragment的时候，传入的 map key 中请不要有相同的如下：
         *
         *  错误的实例：
         *      map.put("f1",new Fragment_1());
         *      map.put("f1",new Fragment_2());
         *
         *  正确的实例：
         *      map.put("f1",new Fragment_1());
         *      map.put("f2",new Fragment_2());
         *
         */

        //属性
        private Activity activity;                      //获取 Activity
        private FragmentManager fm;                     //Fragment 管理器
        private FragmentTransaction transaction;        //Fragment 事务
        private int fragmentLayoutId;                   //Fragment 显示的容器 id
        private Object topFragment;                     //记录当前未加入退回栈的最顶层
        private List<String> topList;                   //记录当前 加入回退栈最顶层的 Fragment
        private Bundle savedInstanceState;              //用于鉴别当前 Activity 是否为初次创建

        //==============    提供给外部的访问接口
        public FragmentManager getFm() {
            return fm;
        }//获取 fm 管理器
        public Object getTopFragment() {
            return topFragment;
        }//获取当前最顶层 Fragment
        public Activity getActivity() {
            return activity;
        }   //获取 Activity

        //==============    构造方法 初始化 Activity、Bundle、fragmentMap 对象
        public GT_Fragment(Bundle savedInstanceState, Activity activity, FragmentManager fm){
            if(activity != null && fm != null){
                this.activity = activity;
                this.fm = fm;
                this.savedInstanceState = savedInstanceState;
            }else{
                if(GT_LOG_TF){
                    GT.log_v(GT.getGT().getLineInfo(),"实例化 GT_Fragment 时， activity 或 FragmentManager 为 null");
                }
            }
        }

        //==============    添加多个 Fragment   初始化
        public GT_Fragment initFragment(int fragmentLayoutId, Map<Object, Fragment> map, Object initFragmentLayoutKey){

            if(savedInstanceState == null){     //如果当前 Activity 是第一次创建
                if(map != null && map.size() >= 1){     //判断 map 非空 且 有新的 Fragment 数据
                    transaction = getTransaction();//开启事务
                    for(Object key : map.keySet()){ //遍历 所有 Fragment
                        transaction.add(fragmentLayoutId,map.get(key),key.toString());//添加 新的 Fragment
                        if(!key.equals(initFragmentLayoutKey)){ //判断当前循环的 Fragment 是否为 要显示的最顶层 Fragment 如果不是就 隐藏
                            transaction.hide(map.get(key));//隐藏 当前添加的 Fragment
                        }
                    }
                    transaction.commit();//提交事务
                    topFragment = initFragmentLayoutKey;//记录为 显示层 Fragment
                    this.fragmentLayoutId = fragmentLayoutId;//初始化 Fragment 显示的容器 id
                }else{
                    if(GT_LOG_TF){
                        GT.log_v(GT.getGT().getLineInfo(),"初始化 GT_Fragment 时， map 或 FragmentManager 为 null 或 map.size < 1");
                    }
                }
            }
            return this;
        }

        public GT_Fragment initFragment(int fragmentLayoutId, List<Fragment> list, int initFragmentLayoutKey){

            if(savedInstanceState == null){     //如果当前 Activity 是第一次创建
                //判断 map 非空 且 有新的 Fragment 数据、且当前指定的首页不能大于 List 索引最大值
                if(list != null && list.size() >= 1 && initFragmentLayoutKey <= (list.size()-1)){
                    transaction = getTransaction();//开启事务
                    String key = null;//定义 key
                    for(int i = 0; i < list.size(); i++){       //遍历 List
                        Fragment fragment = list.get(i);        //获取 Fragment
                        key = fragment.getClass().toString();   //解析 Fragment 唯一标识
                        transaction.add(fragmentLayoutId,fragment,key);//添加 新的 Fragment
                        if(i == initFragmentLayoutKey) continue;//默认设置 第一号元素为 首页
                        transaction.hide(fragment);//隐藏 除第一号元素以外添加的 Fragment

                    }

                    transaction.commit();//提交事务
                    topFragment = key;//记录为 显示层 Fragment
                    this.fragmentLayoutId = fragmentLayoutId;//初始化 Fragment 显示的容器 id
                }else{
                    if(GT_LOG_TF){
                        GT.log_v(GT.getGT().getLineInfo(),"初始化 GT_Fragment 时， map 或 FragmentManager 为 null 或 map.size < 1");
                    }
                }
            }
            return this;
        }

        //=============     单个的添加 Fragment  添加 Fragment
        public GT_Fragment addFragment(Object key,Fragment newFragment){
            if(key != null && newFragment != null){    //当前 key值、newFragment值 不为空
                if(fm.findFragmentByTag(key.toString()) == null){    //如果当前新添加的 Fragment 的 key 在容器中为重复 就进行添加
                    transaction = getTransaction();//开启事务
                    transaction.add(fragmentLayoutId,newFragment,key.toString());//将新的 Fragment 添加到 FragmentMap 中
                    transaction.hide(newFragment);//隐藏新添加的 Fragment
                    transaction.commit();//提交事务
                }else{
                    if(GT_LOG_TF){
                        GT.log_v(GT.getGT().getLineInfo(),"添加 addFragment 时， key 在 fragmentMap 中存在相同的 Key");
                    }
                }
            }else{
                if(GT_LOG_TF){
                    GT.log_v(GT.getGT().getLineInfo(),"添加 addFragment 时， key 或 FragmentManager 或 newFragment 为 null");
                }
            }
            return this;
        }
        public GT_Fragment addFragment(Fragment newFragment){
            if(newFragment != null){    //当前 newFragment值 不为空
                if(fm.findFragmentByTag(newFragment.getClass().toString()) == null){    //如果当前新添加的 Fragment 的 key 在容器中为重复 就进行添加
                    transaction = getTransaction();//开启事务
                    transaction.add(fragmentLayoutId,newFragment,newFragment.getClass().toString());//将新的 Fragment 添加到 FragmentMap 中
                    transaction.hide(newFragment);//隐藏新添加的 Fragment
                    transaction.commit();//提交事务
                }else{
                    if(GT_LOG_TF){
                        GT.log_v(GT.getGT().getLineInfo(),"添加 addFragment 时， key 在 fragmentMap 中存在相同的 Key");
                    }
                }
            }else{
                if(GT_LOG_TF){
                    GT.log_v(GT.getGT().getLineInfo(),"添加 addFragment 时， key 或 FragmentManager 或 newFragment 为 null");
                }
            }
            return this;
        }

        //=============     切换 Fragment
        public GT_Fragment startFragment(Object key){
            if(!key.equals(topFragment)){//判断 当前 要显示的 Fragment 是否为最顶层的 Fragment
                if(fm.findFragmentByTag(key.toString())!= null){    //如果当前要切换的 Fragment 存在 Fragment 容器中
                    transaction = getTransaction();//开启事务
                    transaction.hide(fm.findFragmentByTag(topFragment.toString()));//隐藏最顶层的 Fragment
                    transaction.show(fm.findFragmentByTag(key.toString()));//显示当前指定的 fragment
                    topFragment = key;  //切换当前最顶层 Fragment
                    transaction.commit();//提交事务
                }else{
                    if(GT_LOG_TF){
                        GT.log_v(GT.getGT().getLineInfo(),"切换 Fragment 时， 当前要切换的 Fragment:【" + key + "】 不在容器中。");
                    }
                }
            }else{
                if(GT_LOG_TF){
                    GT.log_v(GT.getGT().getLineInfo(),"切换 Fragment 时， fm 为 null 获取 当前切换的 Fragment 已在最顶层无需切换");
                }
            }
            return this;
        }

        //=============     切换新的 Fragment
        public GT_Fragment startFragment(Fragment newFragment){

            //实例化 记录 加入回退栈的 最顶层 Fragment
            if(topList == null){
                topList = new ArrayList<>();
            }
            if(newFragment != null && fm != null){
                transaction = getTransaction(); //获取事务
                String HXM = newFragment.toString();//获取 哈希码
                transaction.add(fragmentLayoutId,newFragment,HXM);//添加当前最顶层的 Fragment 且将该 Fragment 的哈希码 作为区别 Fragment 的唯一标识
                transaction.addToBackStack(HXM);//将当前的加入到退回栈
                transaction.commit();//提交事务
                topList.add(HXM);//添加当退回栈记录中
            }else{
                if(GT_LOG_TF){
                    GT.log_v(GT.getGT().getLineInfo(),"切换新的 Fragment 时 newFragment 为 null");
                }
            }
            return this;
        }

        //=============     销毁当前退回栈中最顶层的 Fragment
        public GT_Fragment finish(){

            /**
             *      执行手机的物理返回按键 ：
             *      activity.onBackPressed();
             */

            if(fm != null && topList != null && topList.size() >= 1){
                String HXM = topList.get(topList.size()-1);
                fm.popBackStack(HXM,FragmentManager.POP_BACK_STACK_INCLUSIVE);//将加入退回栈的最顶层 Fragment 进行退栈操作
                topList.remove(HXM);//移除当前已经退出栈 Fragment 的 哈希码
            }else{
                if(GT_LOG_TF){
                    GT.log_v(GT.getGT().getLineInfo(),"退回栈bug：fm、topList为 null 或 topListSize == 0");
                }
            }
            return this;
        }

        //==============        获取事务
        public FragmentTransaction getTransaction(){
            if(fm != null){
                transaction = fm.beginTransaction();
            }else{
                if(GT_LOG_TF){
                    GT.log_v(GT.getGT().getLineInfo(),"fm 管理器为 null");
                }
            }
            return transaction;
        }

    }






    //============================================= 设备监听类 ======================================

    //ScreenListener 监听屏幕状态类
    public static class ScreenListener {

        /**
         * 监听
         * 手机屏幕点亮
         * 手机屏幕锁屏
         * 手机屏幕解锁
         *
         * 使用实例:
         * GT.ScreenListener screenListener  = new GT.ScreenListener(this); //初始化 ScreenListener
         * screenListener.begin(new GT.ScreenListener.ScreenStateListener(){....} //new 一个匿名内部类 即可
         * 在销毁该 Activity 时一定要 调用该方法来注销广播
         * unregisterListener(); 方法来注销该广播
         */

        private Context context2;                                //联系上下文
        private ScreenBroadcastReceiver mScreenReceiver;        //定义一个广播
        private ScreenStateListener mScreenStateListener;       //定义个内部接口
        /**
         * 初始化
         * */
        public ScreenListener(Context context) {
            this.context2 = context;
            mScreenReceiver = new ScreenBroadcastReceiver();//初始化广播
        }

        /**
         * 自定义接口
         * */
        public interface ScreenStateListener{
            void onScreenOn();			//手机屏幕点亮
            void onScreenOff();		    //手机屏幕锁屏
            void onUserPresent();		//手机屏幕解锁
        }

        /**
         * 获取screen的状态
         * */
        private void getScreenState() {
            //初始化powerManager
            PowerManager manager = (PowerManager) context2.getSystemService(Context.POWER_SERVICE);
            if (manager.isScreenOn()){   //如果监听已经开启
                if (mScreenStateListener != null){
                    mScreenStateListener.onScreenOn();
                }
            }else {					  //如果监听没开启
                if (mScreenStateListener != null){
                    mScreenStateListener.onScreenOff();
                }
            }
        }


        /**
         * 写一个内部的广播
         * */
        private class ScreenBroadcastReceiver extends BroadcastReceiver {
            private String action = null;
            @Override
            public void onReceive(Context context, Intent intent) {
                action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)){        //屏幕亮时操作
                    mScreenStateListener.onScreenOn();
                }else if (Intent.ACTION_SCREEN_OFF.equals(action)){   //屏幕关闭时操作
                    mScreenStateListener.onScreenOff();
                }else if (Intent.ACTION_USER_PRESENT.equals(action)) {//解锁时操作
                    mScreenStateListener.onUserPresent();
                }
            }
        }
        /**
         * 开始监听广播状态
         * */
        public void begin(ScreenStateListener listener){
            mScreenStateListener = listener;
            registerListener();							    //注册监听
            getScreenState();								//获取监听
        }
        /**
         * 启动广播接收器
         * */
        private void registerListener() {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_ON);		    //屏幕亮起时开启的广播
            filter.addAction(Intent.ACTION_SCREEN_OFF);		    //屏幕关闭时开启的广播
            filter.addAction(Intent.ACTION_USER_PRESENT);	    //屏幕解锁时开启的广播
            context2.registerReceiver(mScreenReceiver, filter);	//发送广播

        }

        /**
         * 解除广播
         * */
        public void unregisterListener(){
            context2.unregisterReceiver(mScreenReceiver); //注销广播
        }


    }

    //HeadsetPlugReceiver 监听耳机是否插入
    public static class GT_HeadsetPlugReceiver{

        /**
         * 监听 耳机
         *
         * 使用实例:
         * GT.GT_HeadsetPlugReceiver gt_headsetPlugReceiver = new GT.GT_HeadsetPlugReceiver(this); //初始化 GT_HeadsetPlugReceiver
         * gt_headsetPlugReceiver.isHeadset_TF();    //获取当前耳机的状态  插入则返回 true 否则返回 false
         * 在销毁该 Activity 时一定要 调用该方法来注销广播
         * unregisterListener(); 方法来注销该广播
         */

        private Activity activity;
        private static boolean headset_TF;//定义耳机是否插入
        private HeadsetPlugReceiver headsetPlugReceiver;//监听手机是否有耳机插入广播

        public boolean isHeadset_TF() {
            registerHeadsetPlugReceiver();
            return headset_TF;
        }

        public GT_HeadsetPlugReceiver(Activity activity){
            this.activity = activity;
        }

        public void unregisterListener(){
            activity.unregisterReceiver(headsetPlugReceiver); //注销广播
        }

        public void registerHeadsetPlugReceiver() {
            headsetPlugReceiver = new HeadsetPlugReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            activity.registerReceiver(headsetPlugReceiver, intentFilter);
        }

        private static class HeadsetPlugReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra("state")){
                    if (intent.getIntExtra("state", 0) == 0){
                        if(GT_LOG_TF)
                            log_v("耳机测试: 没插入耳机");
                        headset_TF = false;
                    }
                    else if (intent.getIntExtra("state", 0) == 1){
                        if(GT_LOG_TF)
                            log_v("耳机测试: 插入耳机");
                        headset_TF = true;
                    }
                }
            }
        }

    }







    //============================================= 设备属性类 ======================================

    //AudioManager 安卓手机音量类
    public static class GT_AudioManager{
        /**
         * 提示：再设置音量大小时，请先搞清楚，该音量的最大值
         */
        private AudioManager mAudioManager;//定义 AudioManager
        private Activity activity;//定义 Activity
        private int max;//最大值
        private int current;//当前值
        public GT_AudioManager(Activity activity){
            this.activity = activity;
            mAudioManager = (AudioManager) activity.getSystemService(activity.AUDIO_SERVICE);
        }//初始化

        //获取 通话声音 最大值 与 当前通过的声音值
        public int getVoiceCall(){
            current = mAudioManager.getStreamVolume( AudioManager.STREAM_VOICE_CALL );
            return current;
        }
        public int getVoiceCallMax(){
            max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_VOICE_CALL );
            return max;
        }
        public void setVoiceCallValue(int value){
            mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, value, AudioManager.FLAG_PLAY_SOUND);//设置 通话声音 音量大小为 0 静音
        }//设置 通话声音 的音量

        //获取 系统音量 最大值 与 当前通过的声音值
        public int getVoiceSystem(){
            max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );
            current = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
            return current;
        }
        public int getVoiceSystemMax(){
            max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );
            return max;
        }
        public void setVoiceSystemValue(int value){
            mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, value, AudioManager.FLAG_PLAY_SOUND);//设置 通话声音 音量大小为 0 静音
        }//设置 系统音量 的音量

        //获取 铃声音量 最大值 与 当前通过的声音值
        public int getVoiceRing(){
            current = mAudioManager.getStreamVolume( AudioManager.STREAM_RING );
            return current;
        }
        public int getVoiceRingMax(){
            max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_RING );
            return max;
        }
        public void setVoiceRingValue(int value){
            mAudioManager.setStreamVolume(AudioManager.STREAM_RING, value, AudioManager.FLAG_PLAY_SOUND);//设置 铃声音量 音量大小为 0 静音
        }//设置 铃声音量 的音量

        //获取 音乐音量(多媒体) 最大值 与 当前通过的声音值
        public int getVoiceMusic(){
            current = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
            return current;
        }
        public int getVoiceMusicMax(){
            max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
            return max;
        }
        public void setMusicValue(int value){
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, value, AudioManager.FLAG_PLAY_SOUND);//设置多媒体音量大小为 0 静音
        }//设置 多媒体 的音量

        //获取 提示声音 音量 最大值 与 当前通过的声音值
        public int getVoiceAlarm(){
            current = mAudioManager.getStreamVolume( AudioManager.STREAM_ALARM );
            return current;
        }
        public int getVoiceAlarmMax(){
            max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_ALARM );
            return max;
        }
        public void setVoiceAlarmValue(int value){
            mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, value, AudioManager.FLAG_PLAY_SOUND);//设置 铃声音量 音量大小为 0 静音
        }//设置 提示声音 的音量

        public void gemgMusiceNoSet(){
            activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        }//游戏过程中只允许调整多媒体音量，而不允许调整通话音量。

    }

    //获取当前手机信息
    public static class MobilePhoneAttribute {

        //获取手机型号
        public String getModel(){
            return Build.MODEL;
        }

        //获取手机SDK版本号
        public String getSDK(){
            return Build.VERSION.SDK;
        }

        //获取手机系统版本号
        public String getRELEASE(){
            return Build.VERSION.RELEASE;
        }

    }







    //============================================= 多媒体类 ========================================

    // 播放音乐
    public static class GT_MediaPlayer{

        /**
         *
         * 本类使用案列：
         *  GT.GT_MediaPlayer mediaPlayer = new GT.GT_MediaPlayer(this);//实例化对象
         *  mediaPlayer.loadMusic(R.raw.bg_music);  //加载 或 更新 将要播放的 音频， 此方法可用于 更新接下来要播放的音频
         *  mediaPlayer.play_pause();//暂停 或 播放
         *   mediaPlayer.stop();//停止播放
         *   mediaPlayer.close();//释放资源
         *
         * 相关属性设置
         * int getDuration()：获取流媒体的总播放时长，单位是毫秒。
         * int getCurrentPosition()：获取当前流媒体的播放的位置，单位是毫秒。
         * void seekTo(int msec)：设置当前MediaPlayer的播放位置，单位是毫秒。
         * void setLooping(boolean looping)：设置是否循环播放。
         * boolean isLooping()：判断是否循环播放。
         * boolean  isPlaying()：判断是否正在播放。
         * void prepare()：同步的方式装载流媒体文件。
         * void prepareAsync()：异步的方式装载流媒体文件。
         * void release ()：回收流媒体资源。
         * void setAudioStreamType(int streamtype)：设置播放流媒体类型。
         * void setWakeMode(Context context, int mode)：设置CPU唤醒的状态。
         * setNextMediaPlayer(MediaPlayer next)：设置当前流媒体播放完毕，下一个播放的MediaPlayer。
         */
        private boolean isPlay = true;    //定义是否为可播放状态
        private Activity activity;
        private MediaPlayer mediaPlayer;
        private int resid = 0;
        private String url = null;

        public GT_MediaPlayer(Activity activity){
            this.activity = activity;
        }//初始化 上下文

        public MediaPlayer loadMusic(int resid){
            this.resid = resid;
            if(!isPlay){       //停止过播放
                if(mediaPlayer.isPlaying()){//如果属于播放状态
                    mediaPlayer.stop();//停止播放
                    mediaPlayer.release();//释放资源
                }
            }
            mediaPlayer = null;//清空内存中对象
            mediaPlayer = MediaPlayer.create(activity,resid);    //初始化 MediaPlayer 对象
            isPlay = true;//恢复可播放状态
            return mediaPlayer;
        }//加载 res 目录下的资源文件

        public MediaPlayer loadMusic(String url){
            this.url = url;
            if(!isPlay){       //停止过播放
                if(mediaPlayer.isPlaying()){//如果属于播放状态
                    mediaPlayer.stop();//停止播放
                }
                mediaPlayer.release();//释放资源
            }
            mediaPlayer = null;//清空内存中对象
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();//预加载音频
            } catch (IOException e) {
//                e.printStackTrace();
                GT.log_e(getGT().getLineInfo(),"你的音频资源可能 需要添加 网络或访问SD卡的读取权限，否则无法进行有效的获取资源 url:" + url);
            }
            isPlay = true;//恢复可播放状态
            return mediaPlayer;
        }//获取 网络 或 SD 上的的音频资源

        public MediaPlayer play(){
            if(mediaPlayer != null){
                recover_play();//如果音频被停止了就恢复音频可播放，在进行 start
                mediaPlayer.start();
            }
            return mediaPlayer;
        }//播放

        public MediaPlayer pause(){
            if(mediaPlayer != null){
                mediaPlayer.pause();
            }
            return mediaPlayer;
        }//暂停

        public MediaPlayer play_pause(){
            recover_play();//如果音频被停止了就恢复音频可播放，在进行 start
            if(!mediaPlayer.isPlaying()){        //如果当前的 mediaPlayer 处于暂停状态  且 播放状态为 false 没有在播放
                mediaPlayer.start();//继续播放
            }else{  //当前处于音乐暂停状态
                mediaPlayer.pause();//暂停音乐
            }
            return mediaPlayer;
        }//播放 与 暂停

        public MediaPlayer stop(){
            isPlay = false;//设置为暂停状态
            mediaPlayer.stop();
            return mediaPlayer;
        }//停止音乐

        private MediaPlayer recover_play(){
            if(!isPlay){       //停止过播放
                if(mediaPlayer.isPlaying()){//如果属于播放状态
                    mediaPlayer.stop();//停止播放
                }
                mediaPlayer.release();//释放资源
                mediaPlayer = null;//清空内存中对象
                if(resid != 0){
                    mediaPlayer = MediaPlayer.create(activity, resid);    //初始化 MediaPlayer 对象
                }else if(url != null){
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare();//预加载音频
                    } catch (IOException e) {
                        GT.log_e(getGT().getLineInfo(),"你的音频资源可能 需要添加 网络或访问SD卡的读取权限，否则无法进行有效的获取资源 url:" + url);
                    }
                }
                isPlay = true;//恢复可播放状态
            }
            return mediaPlayer;
        }//恢复可播放

        public void close(){
            if(mediaPlayer.isPlaying()){//如果属于播放状态
                mediaPlayer.stop();//停止播放
            }
            mediaPlayer.release();//释放资源
            mediaPlayer = null;
        }//释放资源



    }

    //播放音频
    public static class GT_SoundPool{

        private Context context;
        private static SoundPool soundPool;
        private AudioAttributes attr = null;
        private static Map<String,Integer> map = new HashMap<>();      //初始化 map  用于存放 音频 key 与 值
        private static Map<String,Integer> mapMusic = new HashMap<>(); //初始化 mapMusic 用于存放待播放的音频

        //初始化 Content
        public GT_SoundPool(Context context){
            this.context = context;
        }

        public GT_SoundPool setAudioAttributes(AudioAttributes attr){
            this.attr = attr;
            return this;
        }//设置音频属性

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public GT_SoundPool loadMusic(Map map){
            if(map != null){
                this.map = map;
                if(attr == null){
                    //设置音效属性 如果为空,就设置默认的音频属性
                    attr = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)//设置音效的使用场景 为游戏
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)//设置音效类型
                            .build();
                }

                //初始化
                soundPool = new SoundPool.Builder()
                        .setAudioAttributes(attr)//设置音效池的属性
                        .setMaxStreams(map.size())//最大容纳 动态添加最大值 个音频
                        .build();

                initMusic();//初始化 音频流
            }
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public GT_SoundPool addMusic(String key,int rawId){
            if(map != null){
                if(!map.containsKey(key)){ //如果当前 map 中没有此 key
                    map.put(key,rawId); //将值保存到 map 中

                    if(attr == null){
                        //设置音效属性 如果为空,就设置默认的音频属性
                        attr = new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_GAME)//设置音效的使用场景 为游戏
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)//设置音效类型
                                .build();
                    }

                    //初始化
                    soundPool = new SoundPool.Builder()
                            .setAudioAttributes(attr)//设置音效池的属性
                            .setMaxStreams(map.size())//最大容纳 动态添加最大值 个音频
                            .build();

                    initMusic();//初始化 音频流

                }else{
                    GT.log_v(getGT().getLineInfo(),"添加音频无效，当前已经包含相同的 key，无法再进行装载相同的 key");//提示无效的添加
                }
            }
            return this;
        } //添加音频

        private void initMusic(){
            if(map != null){
                for(String key:map.keySet()){
                    mapMusic.put(key,soundPool.load(context, map.get(key),1));//初始化 待播放的音频
                }
            }
        }//初始化音频

        public GT_SoundPool removalMusic(Integer key){
            if(map != null){
                if(map.containsKey(key)){
                    map.remove(key);
                    mapMusic.remove(key);
                    initMusic();//初始化音频
                }else{
                    log_v(getGT().getLineInfo(),"移除音频失败，当前并不存在此 key:" + key);
                }
            }
            return this;
        }//移除音频

        public GT_SoundPool clear(){
            if(map != null){
                map.clear();
                mapMusic.clear();
            }
            return this;
        }//清空音频

        public GT_SoundPool updateMusic(String key,Integer rawId){
            if(map != null){
                if(map.containsKey(key)){
                    map.put(key,rawId);
                    mapMusic.put(key,rawId);
                    initMusic();//初始化音频
                }else{
                    GT.log_v(getGT().getLineInfo(),"修改音频无效，当前并不存在当前 key，无法进行更新操作");//提示无效的更新
                }
            }
            return this;
        }//修改音频

        //播放音频
        public GT_SoundPool play(String key,boolean loop,float rate){
            //播放所选音频
            soundPool.play(
                    mapMusic.get(key),           //指定播放的音频id
                    1,              //左声道 为0.0 到 1.0
                    1,             //右声道 为0.0 到 1.0
                    0,                 //优先级 0
                    (loop == true ? -1 : 0),    //是否循环 0为不循环, -1 为循环
                    rate                        //速率 为正常速率 1  最低为 0.5，最高为 2
            );

            return this;
        }
    }

    //播放视频
    public static class GT_Video implements SurfaceHolder.Callback{

        /**
         * 使用说明：
         * 第一步：在 xml 中 定义好 SurfaceView 组件
         * 第二步：video = new GT.GT_Video(this,R.raw.lmh,surfaceView);//初始化 GT_Video 视频播放器
         * 第三步：播放 video.play();、暂停 video.pause();、 停止 video.stop();、释放资源 video.close();
         */

        private MediaPlayer mediaPlayer = null;
        private SurfaceView surfaceView;
        private SurfaceHolder surfaceHolder;
        private Context context;
        private int resId;
        private static boolean isPlay = true;   //定义是否被停止播放过视频

        public GT_Video(Context context,int resId,SurfaceView surfaceView){
            this.context = context;
            this.surfaceView = surfaceView;
            this.resId = resId;
            surfaceHolder = this.surfaceView.getHolder();
            surfaceHolder.addCallback(this);
        }

        public GT_Video play(){

            if(mediaPlayer != null){
                recover_play();
                mediaPlayer.start();
            }
            return this;
        }

        public GT_Video pause(){
            if(mediaPlayer != null){
                recover_play();
                mediaPlayer.pause();
            }
            return this;
        }

        public GT_Video stop(){
            if(mediaPlayer != null){
                mediaPlayer.stop();
                isPlay = false;
            }
            return this;
        }


        private MediaPlayer recover_play(){
            if(!isPlay){       //停止过播放
                close();
                mediaPlayer = null;//清空内存中对象
                if(resId != 0){
                    mediaPlayer = MediaPlayer.create(context, resId);//设置加载的视频资源
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDisplay(surfaceHolder);    //设置显示视频显示在SurfaceView上
                }
                isPlay = true;//恢复可播放状态
            }
            return mediaPlayer;
        }//恢复可播放


        public void close(){
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }//释放资源

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mediaPlayer = MediaPlayer.create(context, resId);//设置加载的视频资源
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDisplay(surfaceHolder);    //设置显示视频显示在SurfaceView上
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }






    //======================================= Run GT 的内部注解 =====================================

    //注解类
    public static class Annotations{

        //Toast 注解:用于获取调用 GT.Toast的类对象
        @Retention(RetentionPolicy.RUNTIME)
        @Target(value={ElementType.TYPE})
        public @interface Toast {}

    }
    //判断注解类
    public static class AnnotationAssist {

        /**
         * 目的：用于判断 当前类是否被 当前的注解注解过
         * 用例：new GT.AnnotationAssist(LogActivity.class, GT.Annotations.Toast.class);
         * 第一个参数：任意被
         * 再打开 GT 内部的 Log 日志
         */
        @SuppressWarnings("unchecked")
        public AnnotationAssist(Object obj,Object annotation) {

            if(GT_LOG_TF){
                log_i("obj:" + obj);
                log_i("annotation:" + annotation);
            }

            //获取所有注解
            obj = ObjectClassToObject(obj);
            Annotation[] annotations = obj.getClass().getAnnotations();
            if(GT_LOG_TF) log_i("---------------该类有所的注解---------------------");
            for(Annotation annotation1 : annotations) if(GT_LOG_TF) log_i(annotation1);
            if(GT_LOG_TF) log_i("-------------------close--------------------------");


            /*
             * 获取声明注解	[Ljava.lang.annotation.Annotation;@28c97a5
             * 			[Ljava.lang.annotation.Annotation;@28c97a5
             */
            Annotation[] deAnnos = obj.getClass().getDeclaredAnnotations();
            if(GT_LOG_TF) log_i("被声明式注解标识过:" + deAnnos);


            if(annotation != null){
                //获取被 SubAnnotation 注解过的类
                Annotation subAnnotation = obj.getClass().getAnnotation((Class<Annotation>) annotation);
                if(GT_LOG_TF) log_i("该类被 [" + subAnnotation + "] 注解过");
            }

        }

        public Object ObjectClassToObject(Object obj){
            String[] strs = obj.toString().split(" ");
            String str = strs[1];
            Class<?> clazz = getClass();
            try {
                clazz = Class.forName(str);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            try {
                obj = clazz.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return obj;
        }

    }




    //========================================== 线程 ==============================================

    //Thread 更新UI线程
    public static class Thread{

        //更新 主线程 UI
        public static void updateUI(Runnable runnable){
            //该方法属于 OkGo 中的，该方法只用于更新 UI 界面，
            // 不要把耗时操作放在这里面，因为这是在主线程上操作的
            //如果在该方法中用耗时操作会引起 ANR
            runOnUiThread(runnable);
        }

        //开启子线程
        public static void runJava(Runnable runnable){
            //注意：如果你在引用这个线程里引用了主线程的 对象 请在 run 方法中 加入 Looper.prepare(); 否则会在开始或结束Activity活动时 报异常
            new java.lang.Thread(runnable).start();
        }

        //睡眠
        public static void sleep(long millis){
            try {
                java.lang.Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //循环 计时器
        public static Timer Timer(long delay,long period,TimerTask timerTask){
            /**
             * 参数 delay : 待多少秒过后进行 开始计时器
             * 参数 period : 每隔多少毫秒进行一次计时
             * 参数 timerTask : 匿名类 new TimerTask 即可 然后在 run 方法中写耗时操作
             */
            Timer timer = new Timer();
            timer.schedule(timerTask,delay,period);
            return timer;
        }

        //简易 循环计时器
        public static Timer Timer(TimerTask timerTask){
            /**
             * 参数 timerTask : 匿名类 new TimerTask 即可 然后在 run 方法中写耗时操作
             */
            Timer timer = new Timer();
            timer.schedule(timerTask,0,1);
            return timer;
        }

        //Timer 整体封装
        public static class GT_Timer{

            private Timer timer;
            private TimerTask timerTask;

            //初始化
            public GT_Timer(TimerTask timerTask){
                this.timerTask = timerTask;
                timer = new Timer();
            }

            //开启循环计时
            public GT_Timer start(long delay,long period){
                if(timer != null && timerTask != null){
                    timer.schedule(timerTask,delay,period);
                }
                return this;
            }

            public GT_Timer start(){
                if(timer != null && timerTask != null){
                    timer.schedule(timerTask,0,1);
                }
                return this;
            }

            //结束循环计时
            public void close(){
                if(timer != null){
                    timer.cancel();//停止计时
                    timer = null;
                    timerTask = null;
                }
            }



        }

        // AsyncTask 封装
        public static GTAsyncTask asyncTask(GTAsyncTask gtAsyncTask){
            return gtAsyncTask;
        }

        //自动开启的 AsyncTask 封装
        public static GTAsyncTask asyncTask(boolean start, GTAsyncTask gtAsyncTask){
            if(start) gtAsyncTask.execute();//如果设置为
            return gtAsyncTask;
        }

        //AsyncTask 整体封装
        public static class AsyncTask{
            /**
             * 使用实列:    GT.Thread.AsyncTask asyncTask = new GT.Thread.AsyncTask(new GT.Thread.GTAsyncTask(){....}
             */
            private GTAsyncTask gtAsyncTask;    //定义 GTAsyncTask

            //获取 GTAsyncTask 对象
            public GTAsyncTask getGtAsyncTask() {
                return gtAsyncTask;
            }

            //初始化 GTAsyncTask
            public AsyncTask(GTAsyncTask gtAsyncTask){
                this.gtAsyncTask = gtAsyncTask;
            }

            //启动 GTAsyncTask
            public void start(){
                if(gtAsyncTask != null){
                    try{
                        gtAsyncTask.execute();
                    }catch (IllegalStateException e){
                        if(GT.GT_LOG_TF){
                            GT.log_v(getGT().getLineInfo(),"无法执行任务:任务已在运行。");
                        }
                    }
                }
            }

            //关闭 GTAsyncTask 并释放内存
            public void close(){
                if(gtAsyncTask != null){
                    gtAsyncTask.cancel(true);//强制关闭
                    gtAsyncTask = null;//置空
                }
            }

        }

        //定义继承后要实现的类
        public abstract static class GTAsyncTask extends android.os.AsyncTask<Object,Object,Object>{

            /** 用法：继承该类并重写，或者利用 AsyncTask 封装类进行便捷操作
             * onPreExecute 用于初始化
             * onProgressUpdate 用于更新 UI 界面
             * doInBackground 用于进行耗时操作如网络请求、、
             * onPostExecute 用于反馈耗时完成、或者进行资源释放
             */

            @Override
            protected void onPreExecute() {
                //初始化
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(Object... values) {
                //更新 UI
                super.onProgressUpdate(values);
            }

            @Override
            protected Object doInBackground(Object... objects) {
                //阻塞操作 该方法并不在 主线程中调用，不能用于更新 UI 操作
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                //在主线程中调用该方法，可以对 UI 进行修改
                super.onPostExecute(object);
            }
        }



    }





//============================================ 随机类 ===============================================

    public static class GT_Random{
        private Random random;
        public GT_Random(){
            random = new Random();
        }

        //获取一个未知数
        public int getInt(){
            int min = -2147483648;
            int max = 2147483647;
            return random.nextInt(max)%(max-min+1) + min;
        }

        //获取随机范围的数
        public int getInt(int min,int max){
            return random.nextInt(max)%(max-min+1) + min;
        }

    }

}



