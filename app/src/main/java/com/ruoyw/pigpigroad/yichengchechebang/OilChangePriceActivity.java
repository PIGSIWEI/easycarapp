package com.ruoyw.pigpigroad.yichengchechebang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.MyApplication;
import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.SlidingLayout;

import org.json.JSONException;
import org.json.JSONObject;

import static com.ruoyw.pigpigroad.yichengchechebang.RuoyuAPI.MyAppApiConfig.PP_TIP;
import static com.ruoyw.pigpigroad.yichengchechebang.RuoyuAPI.MyAppApiConfig.RUOYU_URL;

/**
 * Created by XYSM on 2018/4/7.
 */

public class OilChangePriceActivity extends AppCompatActivity {
    private LinearLayout back_ll;
    private EditText country_et,car_et;
    private String country_price,car_price,oil_id;
    private String login_token;
    private Button change_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.oilprice_change_layout);
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
        back_ll=findViewById(R.id.back_ll);
        back_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        country_et=findViewById(R.id.country_et);
        car_et=findViewById(R.id.cat_et);
        country_price=getIntent().getStringExtra("country_price");
        car_price=getIntent().getStringExtra("car_price");
        oil_id=getIntent().getStringExtra("oil_id");
        car_et.setHint(car_price);
        country_et.setHint(country_price);
        car_et.setInputType(InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_FLAG_DECIMAL);
        country_et.setInputType(InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_FLAG_DECIMAL);
        SharedPreferences sp = getSharedPreferences("LoginUser", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        change_btn=findViewById(R.id.change_btn);
        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (car_et.getText().toString().trim().equals("")||country_et.getText().toString().trim().equals("")){
                    Toast.makeText(OilChangePriceActivity.this, "请正确输入修改价格，不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    postChangeOilPrice();
                }
            }
        });
    }
    protected boolean enableSliding() {
        return true;
    }
    /**
     * 修改油价操作
     */
    public void postChangeOilPrice(){
        OkGo.<String>post(RUOYU_URL +"?request=private.oil.update.oil.price.action&token=" + login_token + "&platform=android&id="+oil_id+"&country_oil_price="+country_et.getText().toString().trim()+"&merchant_oil_price="+car_et.getText().toString().trim())
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            Log.i(PP_TIP,responseStr);
                            int code = (int) jsonObject.get("code");
                            if (code == 0) {
                                Toast.makeText(OilChangePriceActivity.this, "修改油价成功!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else if (code == 1) {
                                Toast.makeText(OilChangePriceActivity.this, "暂无修改请核对当前油价", Toast.LENGTH_SHORT).show();
                            } else if (code == 999) {
                                Toast.makeText(OilChangePriceActivity.this, "你的身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
                                MyApplication.ExitClear(OilChangePriceActivity.this);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("ppppppppppppppppp", "连接失败!!!!!!!!!!!!!!!");
                        Toast.makeText(OilChangePriceActivity.this, "请检查你的网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
