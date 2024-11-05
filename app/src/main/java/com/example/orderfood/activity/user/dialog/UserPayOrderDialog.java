package com.example.orderfood.activity.user.dialog;

import static com.google.android.filament.Filament.init;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.orderfood.Bean.AddressBean;
import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.adapter.OrderWaitingListDetailAdapter;
import com.example.orderfood.activity.user.ManageUserBuyActivity;
import com.example.orderfood.activity.user.adapter.ReceiveAddressListAdapter;
import com.example.orderfood.activity.user.adapter.UserBuyFoodOrderDetailAdapter;
import com.example.orderfood.dao.AddressDao;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.dao.OrderDao;
import com.example.orderfood.until.Tools;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserPayOrderDialog {

    private Context context;

    private ManageUserBuyActivity man;

    private String bossId;

    private List<OrderDetailBean> list=new ArrayList<>();
    public UserPayOrderDialog(Context context,String bossId){
        man = (ManageUserBuyActivity) context;
        this.context=context;
        this.bossId=bossId;
        init();
    }

    private void init(){
        View bottomSheetLayout=man.getLayoutInflater().inflate(R.layout.user_buy_food_pay_dialog,null);
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetLayout);
        bottomSheetDialog.show();

        UserBean userBean= AdminDao.getCustomerInformation(Tools.getOnAccount(context));
        //加载头像
        ImageView userHead=bottomSheetLayout.findViewById(R.id.user_buy_food_pay_head);
        userHead.setImageBitmap(BitmapFactory.decodeFile(userBean.getU_Img()));

        TextView userName=bottomSheetLayout.findViewById(R.id.user_buy_food_pay_name);
        userName.setText(userBean.getU_Name());

        TextView userOrderTime=bottomSheetLayout.findViewById(R.id.user_buy_food_pay_time);
        Date date1=new Date();
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=sdf1.format(date1);
        userOrderTime.setText(time);

        //加载收货信息
        TextView receiver=bottomSheetLayout.findViewById(R.id.user_buy_food_pay_receiver);
        receiver.setText("");

        TextView address=bottomSheetLayout.findViewById(R.id.user_buy_food_pay_address);
        address.setText("");

        TextView phone=bottomSheetLayout.findViewById(R.id.user_buy_food_pay_phone);
        phone.setText("");

        TextView orderState=bottomSheetLayout.findViewById(R.id.user_buy_food_pay_state);
        orderState.setText("");

        TextView totalPrice=bottomSheetLayout.findViewById(R.id.user_buy_food_pay_total);
        totalPrice.setText("");

        RecyclerView addressRecycle=bottomSheetLayout.findViewById(R.id.user_buy_food_pay_addressList);
        //查询当前用户所有的地址
        List<AddressBean> addressList=AddressDao.getAllAddressByUserId(Tools.getOnAccount(context));
        ReceiveAddressListAdapter receiveAddressListAdapter=new ReceiveAddressListAdapter(bottomSheetLayout,addressList);
        addressRecycle.setLayoutManager(new LinearLayoutManager(context));
        if(addressList==null||addressList.size()==0){
            addressRecycle.setAdapter(null);
        }else{
            addressRecycle.setAdapter(receiveAddressListAdapter);
        }

        RecyclerView listView=bottomSheetLayout.findViewById(R.id.user_buy_food_pay_foodList);
        //获取所有商品信息
        TextView foodListJsonT=man.findViewById(R.id.user_buy_foodDetail);
        String foodListJson=foodListJsonT.getText().toString();
        JSONArray jsonArray=JSONArray.parseArray(foodListJson);

        list=new ArrayList<>();
        for(Object o:jsonArray){
            JSONObject temp=JSONObject.parseObject(o.toString());
            if(!temp.getString("num").equals("0")){
                OrderDetailBean orderDetailBean=new OrderDetailBean();
                orderDetailBean.setFood_id(temp.getString("foodId"));
                orderDetailBean.setFood_num(temp.getString("num"));

                FoodBean food=FoodDao.queryFoodById(temp.getString("foodId"));
                orderDetailBean.setFood_price(food.getFoodPrice());
                orderDetailBean.setFood_img(food.getFoodImg());
                orderDetailBean.setFood_name(food.getFoodName());
                orderDetailBean.setFood_des(food.getFoodDes());
                list.add(orderDetailBean);
            }
        }
        totalPrice.setText("￥"+getSumPrice());

        UserBuyFoodOrderDetailAdapter userBuyFoodOrderDetailAdapter=new UserBuyFoodOrderDetailAdapter(list);

        listView.setLayoutManager(new LinearLayoutManager(context));
        if(list==null||list.size()==0){
            listView.setAdapter(null);
        }else{
            listView.setAdapter(userBuyFoodOrderDetailAdapter);
            userBuyFoodOrderDetailAdapter.notifyDataSetChanged();
        }

        Button buttonCancel=bottomSheetLayout.findViewById(R.id.user_buy_food_pay_cancelOrder);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
            }
        });

        Button buttonPay=bottomSheetLayout.findViewById(R.id.user_buy_food_payOrder);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建订单，向详情表和订单表传输数据
                if(receiver.getText()==null||receiver.getText().toString().equals("")){
                    Toast.makeText(bottomSheetLayout.getContext(),"请选择收货人",Toast.LENGTH_SHORT).show();
                }else if(address.getText()==null||address.getText().toString().equals("")){
                    Toast.makeText(bottomSheetLayout.getContext(),"请选择收货地址",Toast.LENGTH_SHORT).show();
                }else if(phone.getText()==null||phone.getText().toString().equals("")){
                    Toast.makeText(bottomSheetLayout.getContext(),"请选择联系方式",Toast.LENGTH_SHORT).show();
                }else{
                    String receiveAddress=receiver.getText().toString()+"-"+address.getText().toString()+"-"+phone.getText().toString();
                    String orderId= UUID.randomUUID().toString().replace("-","");
                    String orderDetailId= UUID.randomUUID().toString().replace("-","");
                    if(OrderDao.InsertOrder(orderId,time,userBean.getU_Id(),bossId,"1",receiveAddress,orderDetailId)){
                        for (OrderDetailBean orderDetailBean : list) {//所有的订单详情信息
                            orderDetailBean.setOrder_detail_id(orderDetailId);
                            OrderDao.saveOrderDetail(orderDetailBean);
                        }
                        Toast.makeText(bottomSheetLayout.getContext(),"订单创建成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(bottomSheetLayout.getContext(),"订单创建失败",Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

    }

    //获取总价
    public String getSumPrice(){
        //所有商品数量*价格的总和
        BigDecimal total=new BigDecimal(0);

        for(OrderDetailBean orderDetailBean:list){
            BigDecimal priceZ=new BigDecimal(orderDetailBean.getFood_price());
            BigDecimal numZ=new BigDecimal(orderDetailBean.getFood_num());

            total=total.add(priceZ.multiply(numZ));
        }
        return total.toString();
    }

}
