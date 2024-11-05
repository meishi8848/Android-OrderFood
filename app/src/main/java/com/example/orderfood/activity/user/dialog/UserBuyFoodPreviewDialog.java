package com.example.orderfood.activity.user.dialog;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.orderfood.Bean.AddressBean;
import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.ManageUserBuyActivity;
import com.example.orderfood.activity.user.adapter.ReceiveAddressListAdapter;
import com.example.orderfood.activity.user.adapter.UserBuyFoodOrderDetailAdapter;
import com.example.orderfood.dao.AddressDao;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.until.Tools;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserBuyFoodPreviewDialog {

    private Context context;

    private ManageUserBuyActivity man;

    private String bossId;

    public UserBuyFoodPreviewDialog(Context context, String bossId){
        man = (ManageUserBuyActivity) context;
        this.context=context;
        this.bossId=bossId;
        init();
    }

    private void init(){
        View bottomSheetLayout=man.getLayoutInflater().inflate(R.layout.user_buy_food_preview_dialog,null);
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetLayout);
        bottomSheetDialog.show();

        RecyclerView listView=bottomSheetLayout.findViewById(R.id.user_buy_food_preview_foodList);
        //获取所有商品信息
        TextView foodListJsonT=man.findViewById(R.id.user_buy_foodDetail);
        String foodListJson=foodListJsonT.getText().toString();
        JSONArray jsonArray=JSONArray.parseArray(foodListJson);

        List<OrderDetailBean> list=new ArrayList<>();
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
                list.add(orderDetailBean);
            }
        }

        UserBuyFoodOrderDetailAdapter userBuyFoodOrderDetailAdapter=new UserBuyFoodOrderDetailAdapter(list);

        listView.setLayoutManager(new LinearLayoutManager(context));
        if(list==null||list.size()==0){
            listView.setAdapter(null);
        }else{
            listView.setAdapter(userBuyFoodOrderDetailAdapter);
        }


    }

}
