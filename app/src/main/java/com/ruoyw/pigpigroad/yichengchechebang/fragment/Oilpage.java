package com.ruoyw.pigpigroad.yichengchechebang.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ruoyw.pigpigroad.yichengchechebang.MainActivity;
import com.ruoyw.pigpigroad.yichengchechebang.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;
import static com.ruoyw.pigpigroad.yichengchechebang.RuoyuAPI.MyAppApiConfig.RUOYU_URL;

/**
 * Created by PIGROAD on 2018/3/14.
 */

public class Oilpage extends Fragment {
    private TextView
            oil_name1,oil_name2,oil_name3,
            oil_lit1,oil_lit2,oil_lit3,
            count1,count2,count3,
            order_money1,order_money2,order_money3,
            order_oil_lit,order_count,pay_money,
            wx_order_lit,wx_order_count,wx_money,
            wx_order_lit_total,wx_order_count_total,wx_money_total;
    private JSONObject jsonList,totalObj,refundObj;
    private JSONArray group_totalArray;
    private String login_token;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.oils_fragment_layout,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(); //初始化控件
        //拿token
        SharedPreferences sp =getActivity().getSharedPreferences("LoginUser", MODE_PRIVATE);
        login_token=sp.getString("user_token",null);
        GetClassData();
    }
    /**
     * 初始化控件
     */
    private void init() {
        oil_name1=getActivity().findViewById(R.id.oil_name1);
        oil_name2=getActivity().findViewById(R.id.oil_name2);
        oil_name3=getActivity().findViewById(R.id.oil_name3);
        oil_lit1=getActivity().findViewById(R.id.oil_lit1);
        oil_lit2=getActivity().findViewById(R.id.oil_lit2);
        oil_lit3=getActivity().findViewById(R.id.oil_lit3);
        count1=getActivity().findViewById(R.id.count1);
        count2=getActivity().findViewById(R.id.count2);
        count3=getActivity().findViewById(R.id.count3);
        order_money1=getActivity().findViewById(R.id.order_money1);
        order_money2=getActivity().findViewById(R.id.order_money2);
        order_money3=getActivity().findViewById(R.id.order_money3);
        order_oil_lit=getActivity().findViewById(R.id.order_oil_lit);
        order_count=getActivity().findViewById(R.id.order_count);
        pay_money=getActivity().findViewById(R.id.pay_money);
        wx_order_lit=getActivity().findViewById(R.id.wx_order_lit);
        wx_order_count=getActivity().findViewById(R.id.wx_order_count);
        wx_money=getActivity().findViewById(R.id.wx_money);
        wx_order_lit_total=getActivity().findViewById(R.id.wx_order_lit_total);
        wx_order_count_total=getActivity().findViewById(R.id.wx_order_count_total);
        wx_money_total=getActivity().findViewById(R.id.wx_money_total);
    }
    public void GetClassData(){
        long time = System.currentTimeMillis() / 1000;
        String paylisttime = String.valueOf(time);
        OkGo.<String>post(RUOYU_URL + "?request=private.order.ready.jiaoban.get&token=" + login_token + "&platform=android")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        Log.i("pppppppppppGetClassData", responseStr);
                        //进行json数据解析
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            //判断code是否成功
                            int code = (int) jsonObject.get("code");
                            //code 0表示成功，999表示身份过期,1表示服务器错误
                            if (code ==0){
                                jsonList = jsonObject.getJSONObject("data");
                                totalObj = jsonList.getJSONObject("total");
                                refundObj = jsonList.getJSONObject("refund");
                                group_totalArray = jsonList.getJSONArray("group_total");
                                //开始填充数据
                                Log.i("pppppppppppppp","开始填充oil数据~~(☄⊙ω⊙)☄");
                                //填充当前订单数量
                                {
                                    //第一组
                                    {
                                        JSONObject temp = new JSONObject(group_totalArray.getString((0)));
                                        oil_name1.setText(temp.getString("oil_name"));
                                        oil_lit1.setText(temp.getString("oil_lit"));
                                        count1.setText(temp.getString("count"));
                                        order_money1.setText(temp.getString("order_money"));
                                    }
                                    //第二组
                                    {
                                        JSONObject temp = new JSONObject(group_totalArray.getString((1)));
                                        oil_name2.setText(temp.getString("oil_name"));
                                        oil_lit2.setText(temp.getString("oil_lit"));
                                        count2.setText(temp.getString("count"));
                                        order_money2.setText(temp.getString("order_money"));
                                    }
                                    //第三组
                                    {
                                        JSONObject temp = new JSONObject(group_totalArray.getString((2)));
                                        oil_name3.setText(temp.getString("oil_name"));
                                        oil_lit3.setText(temp.getString("oil_lit"));
                                        count3.setText(temp.getString("count"));
                                        order_money3.setText(temp.getString("order_money"));
                                    }
                                    //总计
                                    {
                                        order_oil_lit.setText(totalObj.getString("order_oil_lit"));
                                        order_count.setText(totalObj.getString("order_count"));
                                        pay_money.setText(totalObj.getString("pay_money"));
                                    }
                                    //微信支付方面
                                    {
                                        wx_order_lit.setText(totalObj.getString("order_oil_lit"));
                                        wx_order_count.setText(totalObj.getString("order_count"));
                                        wx_money.setText(totalObj.getString("pay_money"));
                                        wx_order_lit_total.setText(totalObj.getString("order_oil_lit"));
                                        wx_order_count_total.setText(totalObj.getString("order_count"));
                                        wx_money_total.setText(totalObj.getString("pay_money"));
                                    }
                                }
                            }else if(code ==1){
                                Toast.makeText(getActivity(), "服务器错误，请联系管理员", Toast.LENGTH_SHORT).show();
                            }else if(code == 999){
                                Toast.makeText(getActivity(), "你的身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = getActivity().getSharedPreferences("LoginUser", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
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
                } );

    }
}