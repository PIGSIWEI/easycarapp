package com.ruoyw.pigpigroad.yichengchechebang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ruoyw.pigpigroad.yichengchechebang.Adapter.OilPriceAdapter;
import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.MyApplication;
import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.SlidingLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ruoyw.pigpigroad.yichengchechebang.RuoyuAPI.MyAppApiConfig.RUOYU_URL;

/**
 * Created by XYSM on 2018/4/4.
 */

public class OilPriceActivity extends AppCompatActivity{
    private LinearLayout back_ll;
    private String login_token;
    private List<Map<String, Object>> dataList;
    private ListView oilprice_lv;
    private OilPriceAdapter adapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.oilprice_layout);
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
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        SharedPreferences sp = getSharedPreferences("LoginUser", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        oilprice_lv=findViewById(R.id.oilprice_lv);
        postOilPrice();
    }

    @Override
    protected void onResume() {
        postOilPrice();
        super.onResume();
    }

    protected boolean enableSliding() {
        return true;
    }
    /**
     * 获取油品油价
     */
    public void postOilPrice(){
        OkGo.<String>post(RUOYU_URL + "?request=private.oil.today.oil.price.get&token=" + login_token + "&platform=android")
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
                                JSONArray jsonList = jsonObject.getJSONArray("data");
                                dataList = new ArrayList<Map<String, Object>>();
                                for (int i = 0; i < jsonList.length(); i++) {
                                    final Map<String, Object> map = new HashMap<String, Object>();
                                    JSONObject temp = new JSONObject(jsonList.getString((i)));
                                    map.put("oil_name", temp.getString("oil_name"));
                                    map.put("country_oil_price", temp.getString("country_oil_price"));
                                    map.put("merchant_oil_price", temp.getString("merchant_oil_price"));
                                    map.put("oil_id",temp.getString("id"));
                                    dataList.add(map);
                                }
                                initAdapter();
                            } else if (code == 1) {
                                Toast.makeText(OilPriceActivity.this, "服务器错误，请联系管理员", Toast.LENGTH_SHORT).show();
                            } else if (code == 999) {
                                Toast.makeText(OilPriceActivity.this, "你的身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
                                MyApplication.ExitClear(OilPriceActivity.this);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("ppppppppppppppppp", "连接失败!!!!!!!!!!!!!!!");
                        Toast.makeText(OilPriceActivity.this, "请检查你的网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 填充适配器
     */
    private void initAdapter() {
        adapter = new OilPriceAdapter(OilPriceActivity.this, dataList, R.layout.oilprice_listview);
        adapter.notifyDataSetChanged();
        oilprice_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
