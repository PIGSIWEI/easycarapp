package com.ruoyw.pigpigroad.yichengchechebang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruoyw.pigpigroad.yichengchechebang.Polling.PollService;
import com.ruoyw.pigpigroad.yichengchechebang.VoiceUtil.Speek;
import com.ruoyw.pigpigroad.yichengchechebang.fragment.Homepage;
import com.ruoyw.pigpigroad.yichengchechebang.fragment.Orderpage;
import com.ruoyw.pigpigroad.yichengchechebang.fragment.Settingpage;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.ruoyw.pigpigroad.yichengchechebang.RuoyuAPI.MyAppApiConfig.PP_TIP;

/**
 * Created by PIGROAD on 2018/3/6.
 */

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Homepage homepage_fragment;
    private Orderpage orderpage_fragment;
    private Settingpage settingpage_fargment;
    private List<View> bottomTabs;
    private View homepage;
    private View settingpage;
    private View orderpage;
    private ImageView home_iv;
    private ImageView order_iv;
    private ImageView setting_iv;
    private TextView home_tv;
    private TextView order_tv;
    private TextView setting_tv;
    private FragmentManager fragmentManager;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Speek speek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getSupportActionBar().hide();
        SharedPreferences sp = getSharedPreferences("LoginUser", MODE_PRIVATE);
        String login_token = sp.getString("user_token", null);
        if (login_token == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.info_acitivity);
        initViews();
        fragmentManager = getSupportFragmentManager();
        setSelectTab(0);
        Log.i(PP_TIP,"Start polling service...");
        PollService pollService=new PollService();
        pollService.onCreate();
        speek=new Speek(this);
    }

    /**
     * 返回键的调用
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            new TDialog.Builder(getSupportFragmentManager())
                    .addOnClickListener(R.id.btn_cancel, R.id.btn_exit, R.id.exit_text)
                    .setOnBindViewListener(new OnBindViewListener() {
                        @Override
                        public void bindView(BindViewHolder viewHolder) {
                            TextView textView = viewHolder.getView(R.id.exit_text);
                            textView.setText("确认退出吗？");
                        }
                    })
                    .setOnViewClickListener(new OnViewClickListener() {
                        @Override
                        public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                            switch (view.getId()) {
                                case R.id.btn_cancel:
                                    tDialog.dismiss();
                                    break;
                                case R.id.btn_exit:
                                    tDialog.dismiss();
                                    finish();
                                    break;
                            }
                        }
                    })
                    .setLayoutRes(R.layout.exit_dialog)                        //弹窗布局
                    .setScreenWidthAspect(InfoActivity.this, 0.9f)               //屏幕宽度比
                    .setDimAmount(0f)                                                    //设置焦点
                    .create()
                    .show();
        }
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(PP_TIP,"Stop polling service...");
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        home_iv = (ImageView) findViewById(R.id.home_iv);
        order_iv = (ImageView) findViewById(R.id.order_iv);
        setting_iv = (ImageView) findViewById(R.id.setting_iv);
        home_tv = (TextView) findViewById(R.id.home_tv);
        order_tv = (TextView) findViewById(R.id.order_tv);
        setting_tv = (TextView) findViewById(R.id.setting_tv);
        mDrawerLayout=findViewById(R.id.drawer_layout);
        homepage = findViewById(R.id.homepage);
        orderpage = findViewById(R.id.orderpage);
        settingpage = findViewById(R.id.settingpage);
        homepage.setOnClickListener(this);
        orderpage.setOnClickListener(this);
        settingpage.setOnClickListener(this);
        bottomTabs = new ArrayList<>(3);
        bottomTabs.add(homepage);
        bottomTabs.add(orderpage);
        bottomTabs.add(settingpage);
        mNavigationView=findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.printer_test:
                        Intent intent=new Intent(InfoActivity.this,PrinterTestActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * Fragment选择方法
     */
    public void setSelectTab(int index) {
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        switch (index) {
            case 0:
                home_iv.setImageResource(R.drawable.home_select_bg);
                home_tv.setTextColor(Color.parseColor("#6dc77f"));

                if (homepage_fragment == null) {
                    homepage_fragment = new Homepage();
                    transaction.add(R.id.content, homepage_fragment);

                } else {
                    transaction.show(homepage_fragment);
                }
                break;
            case 1:
                order_iv.setImageResource(R.drawable.order_select_bg);
                order_tv.setTextColor(Color.parseColor("#6dc77f"));
                if (orderpage_fragment == null) {
                    orderpage_fragment = new Orderpage();
                    transaction.add(R.id.content, orderpage_fragment);
                } else {
                    transaction.show(orderpage_fragment);
                }
                break;
            case 2:
                setting_iv.setImageResource(R.drawable.setting_select_bg);
                setting_tv.setTextColor(Color.parseColor("#6dc77f"));
                if (settingpage_fargment == null) {
                    settingpage_fargment = new Settingpage();
                    transaction.add(R.id.content, settingpage_fargment);
                } else {
                    transaction.show(settingpage_fargment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /**
     * 开启fragment事物
     */
    private void clearSelection() {
        home_iv.setImageResource(R.drawable.home_bg);
        home_tv.setTextColor(Color.parseColor("#000000"));
        order_iv.setImageResource(R.drawable.order_bg);
        order_tv.setTextColor(Color.parseColor("#000000"));
        setting_iv.setImageResource(R.drawable.setting_bg);
        setting_tv.setTextColor(Color.parseColor("#000000"));

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (homepage_fragment != null) {
            transaction.hide(homepage_fragment);
        }
        if (orderpage_fragment != null) {
            transaction.hide(orderpage_fragment);
        }
        if (settingpage_fargment != null) {
            transaction.hide(settingpage_fargment);
        }

    }

    /**
     * 底部栏切换
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homepage:
                setSelectTab(0);
                break;
            case R.id.orderpage:
                setSelectTab(1);

                break;
            case R.id.settingpage:
                setSelectTab(2);
                break;
        }
    }

}
