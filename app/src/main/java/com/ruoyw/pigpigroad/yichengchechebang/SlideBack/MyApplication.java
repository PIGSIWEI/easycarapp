package com.ruoyw.pigpigroad.yichengchechebang.SlideBack;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ruoyw.pigpigroad.yichengchechebang.MainActivity;
import com.ruoyw.pigpigroad.yichengchechebang.SUNMI.util.AidlUtil;


/**
 * Created by PIGROAD on 2018/3/16.
 */
public class MyApplication extends Application {
    private static Context context;
    private static MyApplication sMyApplication;
    private boolean isAidl;
    private static Activity activity;
    @Override
    public void onCreate() {
        super.onCreate();
        sMyApplication = this;
        isAidl = true;
        AidlUtil.getInstance().connectPrinterService(this);
        context=getApplicationContext();
        activity=this.activity;
    }
    public static void ExitClear(Activity activity){
        SharedPreferences sp = activity.getSharedPreferences("LoginUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    public boolean isAidl() {
        return isAidl;
    }

    public void setAidl(boolean aidl) {
        isAidl = aidl;
    }
    public static Context getContext(){
        return context;
    }
    public static Activity getActivity(){
        return activity;
    }
}


