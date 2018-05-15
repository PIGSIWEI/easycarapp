package com.ruoyw.pigpigroad.yichengchechebang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.SlidingLayout;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

/**
 * Created by PIGROAD on 2018/3/13.
 */

public class TicketSettingActivity extends AppCompatActivity{
    private LinearLayout back_ll;
    private LinearLayout ticketsetting1,ticketsetting2,ticketsetting3,ticketsetting4;
    private RadioButton ticketpause_1,ticketpause_2,ticketpause_3,ticketpause_4;
    private TextView ticket_pause_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.ticket_setting_layout);
        back_ll=findViewById(R.id.back_ll);
        back_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ticket_pause_tv=findViewById(R.id.ticket_pause_tv);
        ticketsetting1=findViewById(R.id.ticketsettinglist1);
        ticketsetting2=findViewById(R.id.ticketsettinglist2);
        ticketsetting3=findViewById(R.id.ticketsettinglist3);
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
        ticketsetting4=findViewById(R.id.ticketsettinglist4);
        ticketsetting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TicketSettingActivity.this,TicketNumberActivity.class);
                startActivity(intent);
            }
        });
        ticketsetting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplication(),TicketStyleAcitvity.class);
                startActivity(intent);

            }
        });
        ticketsetting3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TDialog.Builder(getSupportFragmentManager())
                        .addOnClickListener(R.id.btn_cancel,R.id.btn_confirm,R.id.ticketpause_tv1,R.id.ticketpause_tv2,R.id.ticketpause_tv3,R.id.ticketpause_tv4)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()){
                                    case R.id.btn_cancel:
                                        tDialog.dismiss();
                                        break;
                                    case R.id.btn_confirm:
                                        tDialog.dismiss();
                                        break;
                                    case R.id.ticketpause_tv1:
                                        ticketpause_1=tDialog.getDialog().findViewById(R.id.ticketpause_1);
                                        ticketpause_1.setChecked(true);
                                        ticket_pause_tv.setText("(默认)");
                                        break;
                                    case R.id.ticketpause_tv2:
                                        ticketpause_2=tDialog.getDialog().findViewById(R.id.ticketpause_2);
                                        ticketpause_2.setChecked(true);
                                        ticket_pause_tv.setText("5秒");
                                        break;
                                    case R.id.ticketpause_tv3:
                                        ticketpause_3=tDialog.getDialog().findViewById(R.id.ticketpause_3);
                                        ticketpause_3.setChecked(true);
                                        ticket_pause_tv.setText("10秒");
                                        break;
                                    case R.id.ticketpause_tv4:
                                        ticketpause_4=tDialog.getDialog().findViewById(R.id.ticketpause_4);
                                        ticketpause_4.setChecked(true);
                                        ticket_pause_tv.setText("15秒");
                                        break;
                                }
                            }
                        })
                        .setLayoutRes(R.layout.ticket_pause_layout)                        //弹窗布局
                        .setScreenWidthAspect(TicketSettingActivity.this, 0.9f)               //屏幕宽度比
                        .setDimAmount(0f)                                                    //设置焦点
                        .create()
                        .show();
            }
        });

        ticketsetting4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplication(),TicketHintActivity.class);
                startActivity(intent);
            }
        });

    }
    protected boolean enableSliding() {
        return true;
    }
}
