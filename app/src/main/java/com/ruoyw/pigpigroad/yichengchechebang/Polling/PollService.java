package com.ruoyw.pigpigroad.yichengchechebang.Polling;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ruoyw.pigpigroad.yichengchechebang.R;
import com.ruoyw.pigpigroad.yichengchechebang.SUNMI.util.AidlUtil;
import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.MyApplication;
import com.ruoyw.pigpigroad.yichengchechebang.VoiceUtil.Speek;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by PIGROAD on 2018/5/15.
 * Email:920015363@qq.com
 */

public class PollService extends Service {
    private Speek speek;
    private int notificationId =0;
    private Boolean isStart = true;

    @Override
    public IBinder onBind(Intent intent) {

        new MyThread().start();
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("oncreate()");
        speek=new Speek(MyApplication.getActivity());
        MyThread thread = new MyThread();
        thread.start();
        super.onCreate();
    }


    private class MyThread extends Thread {
        @Override
        public void run() {

            while (isStart) {

                try {
                    // 每个5秒向服务器发送一次请求
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // do something
                //实例化通知管理器
                Notification notification = new Notification.Builder(MyApplication.getContext())
                        .setSmallIcon(R.mipmap.ic_launcher)//设置小图标
                        .setContentTitle("易成车车帮")
                        .setContentText("你有新的订单")
                        .build();
                //获取Notification管理器
                NotificationManager notificationManager = (NotificationManager) MyApplication.getContext().getSystemService(NOTIFICATION_SERVICE);
                //然后用这个Notification管理器把Notification弹出去，那个0是id，用来标识这个Notification的。
                notificationManager.notify(notificationId, notification);
                notificationId =notificationId+1;
                //语音播报
               speek.Speeking("你有新的订单");
               //小票打印
                final SimpleDateFormat simpledf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                final String time=simpledf.format(new Date());
                String content = "******易成车车帮小票测试******\n" +
                        "      "+time+"  \n"+
                        "******************************";
                AidlUtil.getInstance().printText(content, 24, false, false);
            }
        }
    }

    @Override
    public void onDestroy() {
        isStart = false;
        super.onDestroy();
    }
}