package com.ruoyw.pigpigroad.yichengchechebang.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouwei.library.CustomPopWindow;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ruoyw.pigpigroad.yichengchechebang.Adapter.PaylayoutListviewAdapter;
import com.ruoyw.pigpigroad.yichengchechebang.PigListview.PullToRefreshLayout;
import com.ruoyw.pigpigroad.yichengchechebang.R;
import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.MyApplication;
import com.ruoyw.pigpigroad.yichengchechebang.ZxingUtils.ScanActivity;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.ruoyw.pigpigroad.yichengchechebang.RuoyuAPI.MyAppApiConfig.RUOYU_URL;

/**
 * Created by PIGROAD on 2018/3/6.
 */
public class Orderpage extends Fragment implements View.OnClickListener {
    private LinearLayout pay;
    private LinearLayout nooil;
    private TextView pay_tv;
    private TextView nooil_tv;
    private SearchView mSearchView;
    private LinearLayout paylist1;
    private CustomPopWindow PayPopWindow;
    private CustomPopWindow BackOrderPopWindow;
    private ImageView scan2_iv;
    private List<Map<String, Object>> dataList;
    private ListView paylayout_lv;
    private String login_token;
    private View view_more;
    private ProgressBar pb;
    private TextView tvLoad;
    private static Runnable add;
    private TextView nowpayorder;
    private PaylayoutListviewAdapter adapter;
    private PullToRefreshLayout ptrl;
    private ListView pay_lv;
    int totalcount, pagacount = 1, itemcount;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View orderpageLayout = inflater.inflate(R.layout.orderpage_fragment,
                container, false);
        return orderpageLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitAll();
        InitSearch();
        SharedPreferences sp = getActivity().getSharedPreferences("LoginUser", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        postPayLayout();

    }

    /**
     * 初始化所有控件
     */
    private void InitAll() {
        pay = getActivity().findViewById(R.id.pay);
        nooil = getActivity().findViewById(R.id.nooil);
        mSearchView = getActivity().findViewById(R.id.search);
        pay_tv = getActivity().findViewById(R.id.pay_tv);
        nooil_tv = getActivity().findViewById(R.id.nooil_tv);
        scan2_iv = getActivity().findViewById(R.id.scan2_iv);
        pay_lv = getActivity().findViewById(R.id.pay_lv);
        nowpayorder = getActivity().findViewById(R.id.nowpayorder);
        ptrl = ((PullToRefreshLayout) getActivity().findViewById(R.id.refresh_view));
        pay.setOnClickListener(this);
        nooil.setOnClickListener(this);
        scan2_iv.setOnClickListener(this);
        //刷新调取数据
        ptrl.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        itemcount = adapter.getCount();
                        if (itemcount < totalcount) {
                            pagacount = pagacount + 1;
                            postPayLayoutMore();
                            ptrl.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                            int newic = itemcount + 24;
                            if (newic <= totalcount) {
                                nowpayorder.setText("目前订单数量:" + newic);
                            } else {
                                nowpayorder.setText("目前订单数量:" + totalcount);
                            }
                        } else {
                            Toast.makeText(getActivity(), "不要再拉啦，已经到底了", Toast.LENGTH_SHORT).show();
                            ptrl.loadmoreFinish(PullToRefreshLayout.FAIL);
                        }
                    }
                }, 2000);
            }
        });
    }

    /**
     * 自定义搜索框样式
     */
    private void InitSearch() {
        if (mSearchView == null) {
            return;
        } else {
            int imgId = mSearchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
            int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView textView = (TextView) mSearchView.findViewById(id);
            ImageView searchButton = (ImageView) mSearchView.findViewById(imgId);
            searchButton.setImageResource(R.drawable.search_bg);
            mSearchView.setIconifiedByDefault(false);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//14sp
            //设置提示字体颜色
            textView.setHintTextColor(getActivity().getResources().getColor(R.color.search_hint_color));
            //设置字体颜色
            textView.setTextColor(getActivity().getResources().getColor(R.color.white));
            mSearchView.setFocusable(false);
            try {        //--拿到字节码
                Class<?> argClass = mSearchView.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(mSearchView);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay:
                // 此处为得到焦点时的处理内容
                //设置选中颜色
                pay_tv.setTextColor(Color.parseColor("#017136"));
                nooil_tv.setTextColor(Color.parseColor("#ffffff"));
                itemcount = adapter.getCount();
                nowpayorder.setText("目前订单数量:" + itemcount);
                break;
            case R.id.nooil:
                pay_tv.setTextColor(Color.parseColor("#ffffff"));
                nooil_tv.setTextColor(Color.parseColor("#017136"));
                ShowNooilLayout();
                break;
            case R.id.scan2_iv:
                new IntentIntegrator(getActivity())
                        .setOrientationLocked(false)
                        .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是ScanActivity
                        .initiateScan(); // 初始化扫描
                break;
        }

    }

    /**
     * 支付窗口的调用
     */
    /**
     * 支付适配器调用
     */
    private void initPayAdapter() {
        adapter = new PaylayoutListviewAdapter(getActivity(), dataList, R.layout.paylayout_listview);
        adapter.notifyDataSetChanged();
        pay_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        itemcount = adapter.getCount();
        nowpayorder.setText("目前订单数量:" + itemcount);
        pay_tv.setTextColor(Color.parseColor("#017136"));
    }

    /**
     * 非油页面的管理
     */
    private void ShowNooilLayout() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.nooillayout, null);
        //给支付页面绑定窗口
        NooilList(contentView);
        //弹出非油页面
        PayPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //支付页面消失时候回调
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        nooil_tv.setTextColor(Color.parseColor("#ffffff"));
                    }
                })
                .create()
                .showAsDropDown(nooil_tv);
    }

    /**
     * 非油页面的管理器
     *
     * @param contentView
     */
    private void NooilList(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.nooillist1:
                        new TDialog.Builder(getFragmentManager())
                                .addOnClickListener(R.id.backorder_back)
                                .setOnViewClickListener(new OnViewClickListener() {

                                    @Override
                                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                        switch (view.getId()) {
                                            case R.id.backorder_back:
                                                tDialog.dismiss();
                                                break;
                                        }
                                    }
                                }).setLayoutRes(R.layout.nooilorder)
                                .setScreenWidthAspect(getActivity(), 1.0f)
                                .setGravity(Gravity.BOTTOM)
                                .setDimAmount(0f)
                                .create()
                                .show();
                        break;
                    case R.id.nooillist2:
                        new TDialog.Builder(getFragmentManager())
                                .addOnClickListener(R.id.backorder_back)
                                .setOnViewClickListener(new OnViewClickListener() {

                                    @Override
                                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                        switch (view.getId()) {
                                            case R.id.backorder_back:
                                                tDialog.dismiss();
                                                break;
                                        }
                                    }
                                }).setLayoutRes(R.layout.nooilorder)
                                .setScreenWidthAspect(getActivity(), 1.0f)
                                .setGravity(Gravity.BOTTOM)
                                .setDimAmount(0f)
                                .create()
                                .show();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.nooillist1).setOnClickListener(listener);
        contentView.findViewById(R.id.nooillist2).setOnClickListener(listener);
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

    //支付订单的post请求
    public void postPayLayout() {
        Log.i("ppppppppppTOKEN", login_token);
        OkGo.<String>post(RUOYU_URL + "?request=private.order.working.items.list.get&token=" + login_token + "&platform=android&page_size=24&curpage=1")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        Log.i("ppppppppppppppppp", responseStr);
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = (int) jsonObject.get("code");
                            if (code == 0) {
                                JSONObject jsonList = jsonObject.getJSONObject("data");
                                final JSONArray jsonArray = jsonList.getJSONArray("data");
                                Log.i("pppppppppp", "开始写数据了！！");
                                //判断code的状态，如果code为0表示成功，1参数错误，999身份失效
                                totalcount = jsonList.getInt("total");
                                dataList = new ArrayList<Map<String, Object>>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    final Map<String, Object> map = new HashMap<String, Object>();
                                    JSONObject temp = new JSONObject(jsonArray.getString((i)));
                                    //给Listview添加数据
                                    int img[] = {R.drawable.order_backlogo};
                                    map.put("gun_number", temp.getString("gun_id") + "号油枪");
                                    map.put("pay_money", temp.getString("oil_money") + "元");
                                    map.put("gun_kind", temp.getString("oil_name"));
                                    map.put("pay_time", temp.getString("pay_time"));
                                    map.put("orderid", temp.getString("orderid"));
                                    //订单详情
                                    map.put("orderid", temp.getString("orderid"));
                                    map.put("pay_time", temp.getString("pay_time"));
                                    map.put("order_time", temp.getString("order_time"));
                                    map.put("name", temp.getString("name"));
                                    map.put("merchant_oil_price", temp.getString("merchant_oil_price"));
                                    map.put("use_oil_price", temp.getString("use_oil_price"));
                                    map.put("oil_money", temp.getString("oil_money"));
                                    map.put("coupon_money", temp.getString("coupon_money"));
                                    map.put("gun_id", temp.getString("gun_id"));
                                    map.put("oil_name", temp.getString("oil_name"));
                                    map.put("oil_lit", temp.getString("oil_lit"));
                                    map.put("pay_money", temp.getString("pay_money"));
                                    int order_state = temp.getInt("order_status");
                                    // 3表示成功,-1表示退款
                                    if (order_state == 3) {
                                        map.put("pay_state", 0);
                                    } else if (order_state == -1) {
                                        map.put("pay_state", img[0]);
                                    }
                                    dataList.add(map);

                                }
                                initPayAdapter();
                            } else if (code == 1) {
                                Toast.makeText(getActivity(), "服务器错误，请联系管理员", Toast.LENGTH_SHORT).show();
                            } else if (code == 999) {
                                Toast.makeText(getActivity(), "你的身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
                                MyApplication.ExitClear(getActivity());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("ppppppppppppppppp", "连接失败!!!!!!!!!!!!!!!");
                        Toast.makeText(getActivity(), "请检查你的网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //下拉获取更多订单
    public void postPayLayoutMore() {
        OkGo.<String>post(RUOYU_URL + "?request=private.order.working.items.list.get&token=" + login_token + "&platform=android&page_size=24&curpage=" + pagacount)
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = (int) jsonObject.get("code");
                            if (code == 0) {
                                JSONObject jsonList = jsonObject.getJSONObject("data");
                                final JSONArray jsonArray = jsonList.getJSONArray("data");
                                Log.i("pppppppppp", "开始写数据了！！");
                                //判断code的状态，如果code为0表示成功，1参数错误，999身份失效
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    final Map<String, Object> map = new HashMap<String, Object>();
                                    JSONObject temp = new JSONObject(jsonArray.getString((i)));
                                    //给Listview添加数据
                                    int img[] = {R.drawable.order_backlogo};
                                    map.put("gun_number", temp.getString("gun_id") + "号油枪");
                                    map.put("pay_money", temp.getString("oil_money") + "元");
                                    map.put("gun_kind", temp.getString("oil_name"));
                                    map.put("pay_time", temp.getString("pay_time"));
                                    map.put("orderid", temp.getString("orderid"));
                                    //订单详情
                                    map.put("orderid", temp.getString("orderid"));
                                    map.put("pay_time", temp.getString("pay_time"));
                                    map.put("order_time", temp.getString("order_time"));
                                    map.put("name", temp.getString("name"));
                                    map.put("merchant_oil_price", temp.getString("merchant_oil_price"));
                                    map.put("use_oil_price", temp.getString("use_oil_price"));
                                    map.put("oil_money", temp.getString("oil_money"));
                                    map.put("coupon_money", temp.getString("coupon_money"));
                                    map.put("gun_id", temp.getString("gun_id"));
                                    map.put("oil_name", temp.getString("oil_name"));
                                    map.put("oil_lit", temp.getString("oil_lit"));
                                    map.put("pay_money", temp.getString("pay_money"));
                                    int order_state = temp.getInt("order_status");
                                    // 3表示成功,-1表示退款
                                    if (order_state == 3) {
                                        map.put("pay_state", 0);
                                    } else if (order_state == -1) {
                                        map.put("pay_state", img[0]);
                                    }
                                    dataList.add(map);
                                }
                                adapter.notifyDataSetChanged();
                            } else if (code == 1) {
                                Toast.makeText(getActivity(), "服务器错误，请联系管理员", Toast.LENGTH_SHORT).show();
                            } else if (code == 999) {
                                Toast.makeText(getActivity(), "你的身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
                                MyApplication.ExitClear(getActivity());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("ppppppppppppppppp", "连接失败!!!!!!!!!!!!!!!");
                        Toast.makeText(getActivity(), "请检查你的网络设置", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}


