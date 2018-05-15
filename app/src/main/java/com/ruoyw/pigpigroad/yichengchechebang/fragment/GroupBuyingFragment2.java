package com.ruoyw.pigpigroad.yichengchechebang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.ruoyw.pigpigroad.yichengchechebang.R;
import com.ruoyw.pigpigroad.yichengchechebang.ZxingUtils.ScanActivity;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

/**
 * Created by XYSM on 2018/4/4.
 */

public class GroupBuyingFragment2 extends Fragment {
    private Button btn_yanzhen;
    private LinearLayout scan_ll;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.groupbuying_fragment1_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 数据初始化
     */
    private void init() {
        btn_yanzhen = getActivity().findViewById(R.id.btn_yanzhen);
        btn_yanzhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TDialog.Builder(getFragmentManager())
                        .addOnClickListener(R.id.btn_cancel)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()) {
                                    case R.id.btn_cancel:
                                        tDialog.dismiss();
                                        break;
                                }
                            }
                        })
                        .setLayoutRes(R.layout.group_buying_dialog)                        //弹窗布局
                        .setScreenWidthAspect(getActivity(), 0.9f)               //屏幕宽度比
                        .setDimAmount(0f)                                                    //设置焦点
                        .create()
                        .show();
            }
        });
        scan_ll = getActivity().findViewById(R.id.scan_ll);
        scan_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(getActivity())
                        .setOrientationLocked(false)
                        .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是ScanActivity
                        .initiateScan(); // 初始化扫描
            }
        });
    }
}
