package com.ruoyw.pigpigroad.yichengchechebang.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruoyw.pigpigroad.yichengchechebang.R;

import java.util.List;
import java.util.Map;

/**
 * Created by PIGROAD on 2018/3/23.
 */

public class PaylayoutListviewAdapter extends BaseAdapter{
    private List<Map<String, Object>> dataList;
    private Context context;
    private int resource;
    /**
     *  有参构造
     * @param context   界面
     * @param dataList     数据
     * @param resoure       列表资源文件
     */
    public PaylayoutListviewAdapter(Context context,List<Map<String, Object>> dataList, int resoure){
        this.context=context;
        this.dataList=dataList;
        this.resource=resoure;
    }

    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        // 声明内部类
        Util util = null;
        // 中间变量
        final int flag = i;
        /**
         * 根据listView工作原理，列表项的个数只创建屏幕第一次显示的个数。
         * 之后就不会再创建列表项xml文件的对象，以及xml内部的组件，优化内存，性能效率
         */
        if (view == null) {
            util = new Util();
            // 给xml布局文件创建java对象
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
            util.gun_number=view.findViewById(R.id.gun_number);
            util.pay_money=view.findViewById(R.id.pay_money);
            util.gun_kind=view.findViewById(R.id.gun_kind);
            util.pay_time=view.findViewById(R.id.pay_time);
            util.pay_state=view.findViewById(R.id.pay_state);
            // 增加额外变量
            view.setTag(util);
        }else {
            util = (Util) view.getTag();
        }
        // 获取数据显示在各组件
        final Map<String, Object> map = dataList.get(i);
        util.gun_number.setText((String) map.get("gun_number"));
        util.pay_money.setText((String) map.get("pay_money")+"元");
        util.gun_kind.setText((String) map.get("gun_kind"));
        util.pay_time.setText((String) map.get("pay_time"));
        util.pay_state.setImageResource((Integer) map.get("pay_state"));
        final int paystate =(Integer) map.get("pay_state");
        final String order_id= (String) map.get("orderid");
        final String pay_time= (String) map.get("pay_time");
        final String order_time= (String) map.get("order_time");
        final String name_= (String) map.get("name");
        final String merchant_oil_price= (String) map.get("merchant_oil_price");
        final String use_oil_price= (String) map.get("use_oil_price");
        final String oil_money= (String) map.get("oil_money");
        final String coupon_money= (String) map.get("coupon_money");
        final String oil_lit= (String) map.get("oil_lit");
        final String oil_name= (String) map.get("oil_name");
        final String gun_id= (String) map.get("gun_id");
        final String pay_money= (String) map.get("pay_money");
        view.setOnClickListener(new View.OnClickListener() {
            private TextView orderid,paytime,ordertime,name,gun,price,oillit,oilmoney,realpay;
            @Override

            public void onClick(View view) {
              if (paystate ==0){
                  AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                  LayoutInflater inflater = LayoutInflater.from(view.getContext());
                  view = inflater.inflate(R.layout.backorder2, null);
                  final Dialog dialog = builder.create();
                  dialog.show();
                  dialog.setContentView(view);
                  dialog.getWindow().setGravity(Gravity.BOTTOM);//可以设置显示的位置setContentView(view);//自定义布局应该在这里添加，要在dialog.show()的后面
                  Window window = dialog.getWindow();
                  window.setGravity(Gravity.BOTTOM);
                  window.setWindowAnimations(R.style.dialog_animation);
//                  window.getDecorView().setPadding(0, 0, 0, 0);
                  WindowManager.LayoutParams lp = window.getAttributes();
                  lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                  lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                  window.setAttributes(lp);
                  orderid=view.findViewById(R.id.orderid);
                  paytime=view.findViewById(R.id.pay_time);
                  ordertime=view.findViewById(R.id.order_time);
                  name=view.findViewById(R.id.name);
                  gun= view.findViewById(R.id.gun);
                  price=view.findViewById(R.id.price);
                  oillit=view.findViewById(R.id.oillit);
                  oilmoney=view.findViewById(R.id.oilmoney);
                  realpay=view.findViewById(R.id.realpay);
                  realpay.setText(String.valueOf(pay_money)+"元(共优惠"+String.valueOf(coupon_money)+"元)");
                  oilmoney.setText(String.valueOf(oil_money)+"元");
                  oillit.setText(String.valueOf(oil_lit)+"升");
                  price.setText("挂牌"+String.valueOf(use_oil_price)+"元/升 (实际"+String.valueOf(merchant_oil_price)+"元/升)");
                  gun.setText(String.valueOf(gun_id)+"号汽油 "+String.valueOf(oil_name));
                  paytime.setText(String.valueOf(pay_time));
                  ordertime.setText(String.valueOf(order_time));
                  name.setText(String.valueOf(name_));
                  orderid.setText(String.valueOf(order_id));

              }else {
                  AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                  LayoutInflater inflater = LayoutInflater.from(view.getContext());
                  view = inflater.inflate(R.layout.backorder, null);
                  final Dialog dialog = builder.create();
                  dialog.show();
                  dialog.setContentView(view);
                  dialog.getWindow().setGravity(Gravity.BOTTOM);//可以设置显示的位置setContentView(view);//自定义布局应该在这里添加，要在dialog.show()的后面
                  Window window = dialog.getWindow();
                  window.setGravity(Gravity.BOTTOM);
                  window.setWindowAnimations(R.style.dialog_animation);
//                  window.getDecorView().setPadding(0, 0, 0, 0);
                  WindowManager.LayoutParams lp = window.getAttributes();
                  lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                  lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                  window.setAttributes(lp);
                  orderid=view.findViewById(R.id.orderid);
                  paytime=view.findViewById(R.id.paytime);
                  ordertime=view.findViewById(R.id.ordertime);
                  name=view.findViewById(R.id.name);
                  gun= view.findViewById(R.id.gun);
                  price=view.findViewById(R.id.price);
                  oillit=view.findViewById(R.id.oillit);
                  oilmoney=view.findViewById(R.id.oilmoney);
                  realpay=view.findViewById(R.id.realpay);
                  realpay.setText(String.valueOf(pay_money)+"元(共优惠"+String.valueOf(coupon_money)+"元)");
                  oilmoney.setText(String.valueOf(oil_money)+"元");
                  oillit.setText(String.valueOf(oil_lit)+"升");
                  price.setText("挂牌"+String.valueOf(use_oil_price)+"元/升 (实际"+String.valueOf(merchant_oil_price)+"元/升)");
                  gun.setText(String.valueOf(gun_id)+"号汽油 "+String.valueOf(oil_name));
                  paytime.setText(String.valueOf(pay_time));
                  ordertime.setText(String.valueOf(order_time));
                  name.setText(String.valueOf(name_));
                  orderid.setText(String.valueOf(order_id));
              }
            }
        });
        return view;
    }
    /**
     * 内部类，用于辅助适配
     *
     * @author qiangzi
     *
     */
    class Util {
        ImageView pay_state;
        TextView gun_number, pay_money, gun_kind,pay_time;
    }
}
