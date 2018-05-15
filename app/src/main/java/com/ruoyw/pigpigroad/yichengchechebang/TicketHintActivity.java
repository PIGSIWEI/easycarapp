package com.ruoyw.pigpigroad.yichengchechebang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.SlidingLayout;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import ch.ielse.view.SwitchView;

/**
 * Created by PIGROAD on 2018/3/13.
 */

public class TicketHintActivity extends AppCompatActivity{
    private LinearLayout back_ll;
    private SwitchView switchView;
    private TextView switchView_tv;
    private LinearLayout ticket_explain_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.ticket_hint_layout);
        back_ll=findViewById(R.id.back_ll);
        switchView=findViewById(R.id.switchView);
        switchView_tv=findViewById(R.id.switchView_tv);
        ticket_explain_btn=findViewById(R.id.ticket_explain_btn);
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
        switchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                switchView_tv.setText("打印");
                switchView.setOpened(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                switchView_tv.setText("不打印");
                switchView.setOpened(false);
            }
        });
        ticket_explain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TDialog.Builder(getSupportFragmentManager())
                        .addOnClickListener(R.id.ticket_hint_confirm_btn)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()){
                                    case R.id.ticket_hint_confirm_btn:
                                        tDialog.dismiss();
                                        break;
                                }
                            }
                        })
                        .setLayoutRes(R.layout.ticket_hint_explain_layout)                        //弹窗布局
                        .setScreenWidthAspect(TicketHintActivity.this, 0.9f)               //屏幕宽度比
                        .setDimAmount(0f)                                                    //设置焦点
                        .create()
                        .show();
            }
        });

    }
    protected boolean enableSliding() {
        return true;
    }
}
