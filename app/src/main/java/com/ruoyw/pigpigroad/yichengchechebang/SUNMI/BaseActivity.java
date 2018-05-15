package com.ruoyw.pigpigroad.yichengchechebang.SUNMI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.MyApplication;

/**
 * Created by PIGROAD on 2018/4/10.
 * Email:920015363@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity {
    MyApplication baseApp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseApp = (MyApplication) getApplication();
    }

}
