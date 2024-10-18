package com.example.orderfood.until;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.Bean.OrderDetailBean;

import java.util.ArrayList;
import java.util.List;

public class Tools {
    /**
     * 获取当前账号
     * @param context
     * @return
     */
    public static String getOnAccount(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String boss_id=sharedPreferences.getString("account","root");//如果这个值没有就使用该默认值
        return boss_id;
    }

    /**
     * 获取结果集当中指定行的内容
     * @param rs
     * @param column_name
     * @return
     */
    @SuppressLint("Range")
    public static String getResultString(Cursor rs, String column_name){
       return rs.getString(rs.getColumnIndex(column_name));
    }

    /**
     * 判断用户名和商品名是否有关键词与搜索词一致
     * @param list
     * @param query
     * @return
     */
    public static List<OrderBean> filterOrder(List<OrderBean> list,String query){
        List<OrderBean> list1=new ArrayList<>();
        for (OrderBean orderBean:list){//判断用户名和商品名是否有关键词与搜索词一致
            if(orderBean.getUserName().contains(query)){
                list1.add(orderBean);
            }else{
                List<OrderDetailBean> list2=orderBean.getOrderDetailBeanList();
                for(OrderDetailBean orderDetailBean:list2){
                    if(orderDetailBean.getFood_name().contains(query)){
                        list1.add(orderBean);
                    }
                }
            }
        }
        return list1;
    }

}
