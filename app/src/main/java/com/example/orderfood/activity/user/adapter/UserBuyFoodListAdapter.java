package com.example.orderfood.activity.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.orderfood.Bean.BossBean;
import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.ManageUserBuyActivity;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.dao.CommentDao;
import com.example.orderfood.dao.FoodDao;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 用来显示商家商品的一个适配器
 */
public class UserBuyFoodListAdapter extends ArrayAdapter<FoodBean> {

    private List<FoodBean> list;

    private Context context;

    private Context contextFather;

    JSONArray jsonArray;

    public UserBuyFoodListAdapter(@NonNull Context context, List<FoodBean> list,Context contextFather) {
        super(context, R.layout.list_user_buy_food_list, list);
        this.context=context;
        this.list=list;
        this.contextFather=contextFather;
        jsonArray=new JSONArray();
        for(FoodBean foodBean:list){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("foodId",foodBean.getFoodId());
            jsonObject.put("num","0");
            jsonArray.add(jsonObject);
        }
    }

    //显示列表数据的关键方法
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup){
        if(convertView==null){//如果convertView为空，说明这是个新建视图或者没有可重用的视图
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_user_buy_food_list,viewGroup,false);
        }

        FoodBean temp=list.get(position);

        ImageView img=convertView.findViewById(R.id.user_buy_food_list_foodImg);
        TextView name=convertView.findViewById(R.id.user_buy_food_list_name);
        TextView sale=convertView.findViewById(R.id.user_buy_food_list_sale);
        TextView price=convertView.findViewById(R.id.user_buy_food_list_price);
        TextView des=convertView.findViewById(R.id.user_buy_food_list_des);

        ImageView minus=convertView.findViewById(R.id.user_buy_food_minus);
        TextView num=convertView.findViewById(R.id.user_buy_food_num);
        ImageView add=convertView.findViewById(R.id.user_buy_food_add);

        ManageUserBuyActivity fatherView=(ManageUserBuyActivity) contextFather;
        TextView sumPrice=fatherView.findViewById(R.id.user_buy_sumPrice);
        TextView foodDetail=fatherView.findViewById(R.id.user_buy_foodDetail);//存放购买了哪些物品
        String foodJson=foodDetail.getText().toString();

        if(foodJson.isEmpty()) {
            foodDetail.setText(jsonArray.toJSONString());
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numT=num.getText().toString();
                int numD=Integer.valueOf(numT)+1;
                num.setText(String.valueOf(numD));

                BigDecimal priceB=new BigDecimal(temp.getFoodPrice());

                BigDecimal priceSum=new BigDecimal(sumPrice.getText().toString());
                BigDecimal result=priceSum.add(priceB);
                sumPrice.setText(result.toString());

                String foodJson= foodDetail.getText().toString();
                JSONArray jsonArray1=JSONArray.parseArray(foodJson);
                JSONArray newJson=new JSONArray();
                for(Object o:jsonArray1){
                    JSONObject tempJSON=JSONObject.parseObject(o.toString());
                    if(tempJSON.get("foodId").equals(temp.getFoodId())){
                        tempJSON.put("num",num.getText());
                    }
                    newJson.add(tempJSON);
                }
                foodDetail.setText(newJson.toString());
                //Log.d("AAAA",foodDetail.getText().toString());
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numT=num.getText().toString();
                int numD=Integer.valueOf(numT);
                if(numD>0) {
                    numD-=1;

                    BigDecimal priceB=new BigDecimal(temp.getFoodPrice());

                    BigDecimal priceSum=new BigDecimal(sumPrice.getText().toString());
                    BigDecimal result=priceSum.subtract(priceB);
                    sumPrice.setText(result.toString());
                }
                num.setText(String.valueOf(numD));

                String foodJson= foodDetail.getText().toString();
                JSONArray jsonArray1=JSONArray.parseArray(foodJson);
                JSONArray newJson=new JSONArray();
                for(Object o:jsonArray1){
                    JSONObject tempJSON=JSONObject.parseObject(o.toString());
                    if(tempJSON.get("foodId").equals(temp.getFoodId())){
                        tempJSON.put("num",num.getText());
                    }
                    newJson.add(tempJSON);
                }
                foodDetail.setText(newJson.toString());
                //Log.d("AAAA",foodDetail.getText().toString());
            }
        });

        String bossId=temp.getBossId();
        BossBean bossBean=AdminDao.getBossInformation(bossId);

        //店铺评分需要计算

        Bitmap bitmap= BitmapFactory.decodeFile(temp.getFoodImg());
        img.setImageBitmap(bitmap);
        name.setText(temp.getFoodName());
        price.setText("价格："+temp.getFoodPrice());
        des.setText("描述："+temp.getFoodDes());
        String scoreZ=CommentDao.getAvgScore(bossId);

        int saleNum=FoodDao.getMouthSaleNum(temp.getFoodId());
        sale.setText("月销："+String.valueOf(saleNum));

        convertView.setOnClickListener(null);

        return convertView;
    }

}
