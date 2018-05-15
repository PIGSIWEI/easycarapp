package com.ruoyw.pigpigroad.yichengchechebang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ruoyw.pigpigroad.yichengchechebang.ClassActivity;
import com.ruoyw.pigpigroad.yichengchechebang.ExchangeActivity;
import com.ruoyw.pigpigroad.yichengchechebang.GroupBuyingActivity;
import com.ruoyw.pigpigroad.yichengchechebang.MarketingActivity;
import com.ruoyw.pigpigroad.yichengchechebang.OilPriceActivity;
import com.ruoyw.pigpigroad.yichengchechebang.R;
import com.ruoyw.pigpigroad.yichengchechebang.RefundActivity;
import com.ruoyw.pigpigroad.yichengchechebang.SpecialCarAcitivity;
import com.ruoyw.pigpigroad.yichengchechebang.StatisticsActivity;
import com.ruoyw.pigpigroad.yichengchechebang.VoiceUtil.Speek;
import com.ruoyw.pigpigroad.yichengchechebang.ZxingUtils.ScanActivity;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

/**
 * Created by PIGROAD on 2018/3/6.
 */

public class Homepage extends Fragment implements View.OnClickListener {
    private ImageView scan_iv,menu_iv;
    private LinearLayout home1, home2, home3, home4, home5, home6, home7, home8, home9;
    private DrawerLayout drawerLayout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View homepageLayout = inflater.inflate(R.layout.homepage_fragment,
                container, false);
        return homepageLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initDate();
        super.onActivityCreated(savedInstanceState);
    }

    private void initDate() {
        home1 = getActivity().findViewById(R.id.home1);
        home1.setOnClickListener(this);
        home2 = getActivity().findViewById(R.id.home2);
        home2.setOnClickListener(this);
        home3 = getActivity().findViewById(R.id.home3);
        home3.setOnClickListener(this);
        home4 = getActivity().findViewById(R.id.home4);
        home4.setOnClickListener(this);
        home5 = getActivity().findViewById(R.id.home5);
        home5.setOnClickListener(this);
        home6 = getActivity().findViewById(R.id.home6);
        home6.setOnClickListener(this);
        home7 = getActivity().findViewById(R.id.home7);
        home7.setOnClickListener(this);
        home8 = getActivity().findViewById(R.id.home8);
        home8.setOnClickListener(this);
        home9 = getActivity().findViewById(R.id.home9);
        home9.setOnClickListener(this);
        scan_iv = getActivity().findViewById(R.id.scan_iv);
        scan_iv.setOnClickListener(this);
        menu_iv=getActivity().findViewById(R.id.menu_iv);
        menu_iv.setOnClickListener(this);
        drawerLayout=getActivity().findViewById(R.id.drawer_layout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_iv:
                new IntentIntegrator(getActivity())
                        .setOrientationLocked(false)
                        .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是ScanActivity
                        .initiateScan(); // 初始化扫描
                break;
            case R.id.home1:
                Intent intent = new Intent(getActivity(), ClassActivity.class);

                startActivity(intent);
                break;
            case R.id.home2:
                Intent intent2 = new Intent(getActivity(), StatisticsActivity.class);
                startActivity(intent2);
                break;
            case R.id.home3:
                Intent intent3 = new Intent(getActivity(), GroupBuyingActivity.class);
                intent3.putExtra("chose_type","1");
                startActivity(intent3);
                break;
            case R.id.home4:
                Intent intent4 = new Intent(getActivity(), ExchangeActivity.class);
                startActivity(intent4);
                break;
            case R.id.home5:
                Intent intent5 = new Intent(getActivity(), RefundActivity.class);
                startActivity(intent5);
                break;
            case R.id.home6:
                Intent intent6 = new Intent(getActivity(), OilPriceActivity.class);
                startActivity(intent6);
                break;
            case R.id.home7:
                Intent intent7 = new Intent(getActivity(), GroupBuyingActivity.class);
                intent7.putExtra("chose_type","2");
                startActivity(intent7);
                break;
            case R.id.home8:
                Intent intent8 = new Intent(getActivity(), MarketingActivity.class);
                startActivity(intent8);
                break;
            case R.id.home9:
                new TDialog.Builder(getFragmentManager())
                        .addOnClickListener(R.id.btn_cancel,R.id.btn_confirm2)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()) {
                                    case R.id.btn_cancel:
                                        tDialog.dismiss();
                                        break;
                                    case R.id.btn_confirm2:
                                        tDialog.dismiss();
                                        Intent intent9=new Intent(getActivity(), SpecialCarAcitivity.class);
                                        startActivity(intent9);
                                        break;
                                }
                            }
                        })
                        .setLayoutRes(R.layout.confirm_dialog)                        //弹窗布局
                        .setScreenWidthAspect(getActivity(), 0.9f)               //屏幕宽度比
                        .setDimAmount(0f)                                                    //设置焦点
                        .create()
                        .show();
                break;
            case R.id.menu_iv:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }


    // 通过 onActivityResult的方法获取扫描回来的值
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getActivity(), "内容为空", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "扫描成功", Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
