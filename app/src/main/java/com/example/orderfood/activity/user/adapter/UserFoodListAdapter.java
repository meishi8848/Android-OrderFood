package com.example.orderfood.activity.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.Bean.BossBean;
import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.ManageBossUpdateFoodActivity;
import com.example.orderfood.activity.user.ManageUserBuyActivity;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.dao.CommentDao;
import com.example.orderfood.dao.FoodDao;

import java.util.List;

/**
 * 用来显示商家商品的一个适配器
 */
public class UserFoodListAdapter extends ArrayAdapter<FoodBean> {

    private List<FoodBean> list;

    private Context context;

    public UserFoodListAdapter(@NonNull Context context, List<FoodBean> list) {
        super(context, R.layout.list_user_food_list, list);
        this.context=context;
        this.list=list;
    }

    //显示列表数据的关键方法
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup){
        if(convertView==null){//如果convertView为空，说明这是个新建视图或者没有可重用的视图
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_user_food_list,viewGroup,false);
        }

        FoodBean temp=list.get(position);

        ImageView img=convertView.findViewById(R.id.user_food_list_foodImg);
        TextView name=convertView.findViewById(R.id.user_food_list_name);
        TextView sale=convertView.findViewById(R.id.user_food_list_sale);
        TextView price=convertView.findViewById(R.id.user_food_list_price);
        TextView des=convertView.findViewById(R.id.user_food_list_des);

        ImageView bimg=convertView.findViewById(R.id.user_food_list_bossHead);
        TextView bname=convertView.findViewById(R.id.user_food_list_bossName);
        TextView bscore=convertView.findViewById(R.id.user_food_list_bossScore);

        String bossId=temp.getBossId();
        BossBean bossBean=AdminDao.getBossInformation(bossId);
        bimg.setImageBitmap(BitmapFactory.decodeFile(bossBean.getB_Img()));
        bname.setText(bossBean.getB_Name());

        //店铺评分需要计算


        Bitmap bitmap= BitmapFactory.decodeFile(temp.getFoodImg());
        img.setImageBitmap(bitmap);
        name.setText(temp.getFoodName());
        price.setText("价格："+temp.getFoodPrice());
        des.setText("描述："+temp.getFoodDes());
        String scoreZ=CommentDao.getAvgScore(bossId);
        bscore.setText(scoreZ+"分");

        int saleNum=FoodDao.getMouthSaleNum(temp.getFoodId());
        sale.setText("月销："+String.valueOf(saleNum));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ManageUserBuyActivity.class);
                intent.putExtra("bossId",bossBean);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

}
