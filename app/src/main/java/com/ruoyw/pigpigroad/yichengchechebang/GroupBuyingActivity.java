package com.ruoyw.pigpigroad.yichengchechebang;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.SlidingLayout;
import com.ruoyw.pigpigroad.yichengchechebang.fragment.GroupBuyingFragment1;
import com.ruoyw.pigpigroad.yichengchechebang.fragment.GroupBuyingFragment2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PIGROAD on 2018/3/16.
 */

public class GroupBuyingActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout back_ll, scan_ll;
    private Button btn_yanzhen;
    private List<View> bottomTabs;
    private FragmentManager fragmentManager;
    private TextView gba_tv1,gba_tv2;
    private LinearLayout gb_fragment1,gb_fragment2;
    private GroupBuyingFragment1 gbf_1;
    private GroupBuyingFragment2 gbf_2;
    private String chose_type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.group_buying_layout);
        init();

        back_ll = findViewById(R.id.back_ll);
        back_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
    }

    /**
     * 数据初始化
     */
    private void init() {
        gb_fragment1=findViewById(R.id.gb_fragment1);
        gb_fragment2=findViewById(R.id.gb_fragment2);
        gb_fragment1.setOnClickListener(this);
        gb_fragment2.setOnClickListener(this);
        gba_tv1=findViewById(R.id.gba_tv1);
        gba_tv2=findViewById(R.id.gba_tv2);
        bottomTabs = new ArrayList<>(2);
        bottomTabs.add(gb_fragment1);
        bottomTabs.add(gb_fragment2);
        fragmentManager = getSupportFragmentManager();
        chose_type= getIntent().getStringExtra("chose_type");
        switch (chose_type){
            case "1":
                setSelectTab(0);
                break;
            case "2":
                setSelectTab(1);
                break;
        }
    }
    /**
     *  清理fragment
     */
    private void clearSelection() {
        gb_fragment1.setBackgroundColor(Color.parseColor("#ffffff"));
        gba_tv1.setTextColor(Color.parseColor("#ababab"));
        gb_fragment2.setBackgroundColor(Color.parseColor("#ffffff"));
        gba_tv2.setTextColor(Color.parseColor("#ababab"));
    }
    protected boolean enableSliding() {
        return true;
    }
    /**
     * fragment隐藏
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (gbf_1 != null) {
            transaction.hide(gbf_1);
        }
        if (gbf_2 != null) {
            transaction.hide(gbf_2);
        }
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
                gb_fragment2.setBackgroundColor(Color.parseColor("#f3f3f3"));
                gba_tv1.setTextColor(Color.parseColor("#0fb75f"));
                if (gbf_1 == null) {
                    gbf_1 = new GroupBuyingFragment1();
                    transaction.add(R.id.content, gbf_1);

                } else {
                    transaction.show(gbf_1);
                }
                break;
            case 1:
                gb_fragment1.setBackgroundColor(Color.parseColor("#f3f3f3"));
                gba_tv2.setTextColor(Color.parseColor("#0fb75f"));
                if (gbf_2 == null) {
                    gbf_2 = new GroupBuyingFragment2();
                    transaction.add(R.id.content, gbf_2);
                } else {
                    transaction.show(gbf_2);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gb_fragment1:
                setSelectTab(0);
                break;
            case R.id.gb_fragment2:
                setSelectTab(1);
                break;
        }
    }
}

