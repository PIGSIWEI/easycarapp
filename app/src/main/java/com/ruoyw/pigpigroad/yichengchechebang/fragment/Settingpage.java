package com.ruoyw.pigpigroad.yichengchechebang.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ruoyw.pigpigroad.yichengchechebang.HelpActivity;
import com.ruoyw.pigpigroad.yichengchechebang.MessageActivity;
import com.ruoyw.pigpigroad.yichengchechebang.OilActivity;
import com.ruoyw.pigpigroad.yichengchechebang.PasswordActivity;
import com.ruoyw.pigpigroad.yichengchechebang.ProgressBar.HorizontalProgressBar;
import com.ruoyw.pigpigroad.yichengchechebang.R;
import com.ruoyw.pigpigroad.yichengchechebang.SQLite.MyDBOpenHelper;
import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.MyApplication;
import com.ruoyw.pigpigroad.yichengchechebang.TicketSettingActivity;
import com.ruoyw.pigpigroad.yichengchechebang.VoiceUtil.Speek;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import ch.ielse.view.SwitchView;

import static com.ruoyw.pigpigroad.yichengchechebang.RuoyuAPI.MyAppApiConfig.PP_TIP;
import static com.ruoyw.pigpigroad.yichengchechebang.VoiceUtil.MainHandlerConstant.PRINT;

/**
 * Created by PIGROAD on 2018/3/6.
 */

public class Settingpage extends Fragment  implements View.OnClickListener{
    private LinearLayout helpLinearLayout;
    private LinearLayout messageLinearLayout;
    private LinearLayout oilLinearLayout;
    private MyDBOpenHelper helper;
    private LinearLayout ticketLayout;
    private LinearLayout ticketSettingLatout;
    private LinearLayout listingsumLatout;
    private LinearLayout passwordLayout;
    private LinearLayout voiceLayout;
    private LinearLayout updataLayout;
    private Speek speek;
    private LinearLayout systemLayout;
    private LinearLayout opinionLayout;
    private SwitchView switchView,switchView2;
    private TextView state_tv,switchView2_tv,voice_tv;
    private MyDBOpenHelper myDBOpenHelper;
    private Boolean ticketstates=null;
    private TextView ticketstates_tv,listingsum_tv,exit_btn;
    private RadioButton radio_1,radio_2,radio_3;
    private HorizontalProgressBar progress;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View SettingpageLayout = inflater.inflate(R.layout.settingpage_fragment,
                container, false);

        return SettingpageLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InintDate();
        myDBOpenHelper=new MyDBOpenHelper(getContext());
        search_ticketstates();
        if (ticketstates==true){
            ticketstates_tv.setText("开启");
        }else{
            ticketstates_tv.setText("关闭");
        }

    }


    protected void handle(Message msg) {
        int what = msg.what;
        switch (what) {
            case PRINT:
                Log.i(PP_TIP, String.valueOf(msg));
                break;
            default:
                break;
        }
    }


    private void InintDate() {
        exit_btn=getActivity().findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(this);
        helpLinearLayout=getActivity().findViewById(R.id.settinglist1);
        ticketstates_tv=getActivity().findViewById(R.id.ticketstates_tv);
        listingsum_tv=getActivity().findViewById(R.id.listingsum_tv);
        voice_tv=getActivity().findViewById(R.id.voice_tv);
        helpLinearLayout.setOnClickListener(this);
        messageLinearLayout=getActivity().findViewById(R.id.settinglist2);
        messageLinearLayout.setOnClickListener(this);
        oilLinearLayout=getActivity().findViewById(R.id.settinglist3);
        oilLinearLayout.setOnClickListener(this);
        ticketLayout=getActivity().findViewById(R.id.settinglist4);
        ticketLayout.setOnClickListener(this);
        ticketSettingLatout=getActivity().findViewById(R.id.settinglist5);
        ticketSettingLatout.setOnClickListener(this);
        listingsumLatout=getActivity().findViewById(R.id.settinglist6);
        listingsumLatout.setOnClickListener(this);
        passwordLayout=getActivity().findViewById(R.id.settinglist7);
        passwordLayout.setOnClickListener(this);
        voiceLayout=getActivity().findViewById(R.id.settinglist8);
        voiceLayout.setOnClickListener(this);
        updataLayout=getActivity().findViewById(R.id.settinglist9);
        updataLayout.setOnClickListener(this);
        systemLayout=getActivity().findViewById(R.id.settinglist10);
        systemLayout.setOnClickListener(this);
        opinionLayout=getActivity().findViewById(R.id.settinglist11);
        opinionLayout.setOnClickListener(this);
        speek=new Speek(getActivity());
    }
    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.settinglist1:
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.settinglist2:
                Intent intent2 = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent2);
                break;
            case R.id.settinglist3:
                Intent intent3 = new Intent(getActivity(), OilActivity.class);
                startActivity(intent3);
                break;
            case R.id.settinglist4:
                new TDialog.Builder(getFragmentManager())
                        .setLayoutRes(R.layout.ticket_layout)
                        .addOnClickListener(R.id.btn_confirm,R.id.switchView)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()){
                                    case R.id.btn_confirm:
                                        if (ticketstates==true){
                                            ticketstates_tv.setText("开启");
                                        }else{
                                            ticketstates_tv.setText("关闭");
                                        }
                                        tDialog.dismiss();

                                        break;
                                    case R.id.switchView:
                                        switchView=tDialog.getDialog().findViewById(R.id.switchView);
                                        state_tv=tDialog.getDialog().findViewById(R.id.state_tv);
                                        SQLiteDatabase db=myDBOpenHelper.getWritableDatabase();
                                        boolean isOpened =switchView.isOpened();
                                        if (isOpened){
                                            state_tv.setText("开启");
                                            if (!ticketstates ==true) {
                                                String sql2="UPDATE usersettings SET states = '1' where widgetname= 'ticketstates' ";
                                                db.execSQL(sql2);
                                                db.close();
                                                ticketstates=true;
                                            }
                                        }else {
                                            state_tv.setText("关闭");
                                            String sql="UPDATE usersettings SET states = '0' where widgetname= 'ticketstates' ";
                                            db.execSQL(sql);
                                            db.close();
                                            ticketstates=false;
                                        }
                                        break;
                                }
                            }
                        })
                        .setScreenWidthAspect(getActivity(),0.9f)
                        .setDimAmount(0f)
                        .create()
                        .show();
                break;
            case R.id.settinglist5:
                Intent intent5 =new Intent(getActivity(),TicketSettingActivity.class);
                startActivity(intent5);
                break;

            case R.id.settinglist6:

                new TDialog.Builder(getFragmentManager())
                        .addOnClickListener(R.id.btn_listingsum_cancel,R.id.btn_listingsum_confrim,R.id.radio_tv1,R.id.radio_tv2,R.id.radio_tv3,R.id.radio_1,R.id.radio_2,R.id.radio_3)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, final View view, final TDialog tDialog) {
                                int i=0;
                                switch (view.getId()){
                                    case R.id.btn_listingsum_cancel:
                                        tDialog.dismiss();
                                        break;
                                    case R.id.btn_listingsum_confrim:
                                        tDialog.dismiss();
                                        break;
                                    case R.id.radio_tv1:
                                        radio_1=tDialog.getDialog().findViewById(R.id.radio_1);
                                        radio_1.setChecked(true);
                                        listingsum_tv.setText("挂牌金额");

                                        break;
                                    case R.id.radio_tv2:
                                        radio_2=tDialog.getDialog().findViewById(R.id.radio_2);
                                        radio_2.setChecked(true);
                                        listingsum_tv.setText("应结金额");
                                        break;
                                    case R.id.radio_tv3:
                                        radio_3=tDialog.getDialog().findViewById(R.id.radio_3);
                                        radio_3.setChecked(true);
                                        listingsum_tv.setText("协议金额");
                                        break;
                                }
                            }
                        })
                        .setLayoutRes(R.layout.listingsum_layout)                        //弹窗布局
                        .setScreenWidthAspect(getActivity(), 0.9f)               //屏幕宽度比
                        .setDimAmount(0f)                                                    //设置焦点
                        .create()
                        .show();
                break;
            case R.id.settinglist7:
                Intent intent7=new Intent(getActivity(), PasswordActivity.class);
                startActivity(intent7);
                break;
            case R.id.settinglist8:
                new TDialog.Builder(getFragmentManager())
                        .addOnClickListener(R.id.switchView2,R.id.btn_voice_confirm)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()){
                                    case R.id.btn_voice_confirm:
                                        tDialog.dismiss();
                                        break;
                                    case R.id.switchView2:
                                        switchView2=tDialog.getDialog().findViewById(R.id.switchView2);
                                        switchView2_tv=tDialog.getDialog().findViewById(R.id.switchView2_tv);
                                        boolean isOpened =switchView2.isOpened();
                                        if (isOpened){
                                            switchView2_tv.setText("开启");
                                            voice_tv.setText("开启");
                                            speek.Speeking("易成车车帮语音功能开启");
                                        }else {
                                            switchView2_tv.setText("关闭");
                                            voice_tv.setText("关闭");
                                            speek.Speeking("易成车车帮语音功能关闭");
                                        }
                                        break;
                                }
                            }
                        })
                        .setLayoutRes(R.layout.voice_layout)                        //弹窗布局
                        .setScreenWidthAspect(getActivity(), 0.9f)               //屏幕宽度比
                        .setDimAmount(0f)                                                    //设置焦点
                        .create()
                        .show();
                break;
            case R.id.settinglist9:
                new TDialog.Builder(getFragmentManager())
                        .addOnClickListener(R.id.btn_updata_cancel,R.id.btn_updata_confirm,R.id.progress)
                        .setOnBindViewListener(new OnBindViewListener() {
                            @Override
                            public void bindView(BindViewHolder viewHolder) {
                                progress=viewHolder.getView(R.id.progress);
                                progress.setProgressWithAnimation(100);
                                progress.startProgressAnimation();
                            }
                        })
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()){
                                    case R.id.btn_updata_cancel:
                                        tDialog.dismiss();
                                        break;
                                    case R.id.btn_updata_confirm:
                                        tDialog.dismiss();
                                        break;
                                }
                            }
                        })
                        .setLayoutRes(R.layout.updata_layout)                        //弹窗布局
                        .setScreenWidthAspect(getActivity(), 0.9f)               //屏幕宽度比
                        .setDimAmount(0f)                                                    //设置焦点
                        .create()
                        .show();

                break;
            case R.id.settinglist10:
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", getContext().getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", getContext().getPackageName());
                }
                startActivity(localIntent);
                break;
            case R.id.exit_btn:
                new TDialog.Builder(getFragmentManager())
                        .addOnClickListener(R.id.btn_cancel,R.id.btn_exit)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()){
                                    case R.id.btn_cancel:
                                        tDialog.dismiss();
                                        break;
                                    case R.id.btn_exit:
                                        MyApplication.ExitClear(getActivity());
                                        Toast.makeText(getActivity(),"退出登录成功",Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        })
                        .setLayoutRes(R.layout.exit_dialog)                        //弹窗布局
                        .setScreenWidthAspect(getActivity(), 0.9f)               //屏幕宽度比
                        .setDimAmount(0f)                                                    //设置焦点
                        .create()
                        .show();

                break;
        }
    }
    private boolean search_ticketstates(){
        SQLiteDatabase db=myDBOpenHelper.getWritableDatabase();
        String sql="select * from usersettings where widgetname= 'ticketstates' and states= 1 ";
        Cursor cursor = db.rawQuery(sql,new String[]{});
        while (cursor.moveToNext()) {
            db.close();
            ticketstates=true;
            return true;
        }
        ticketstates=false;
        db.close();
        return false;
    }


}