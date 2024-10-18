package com.example.orderfood.dao;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.db.DBUntil;
import com.example.orderfood.until.Tools;

import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    public static SQLiteDatabase db= DBUntil.con;

    //查询所有未完成的订单详情
    public static List<OrderDetailBean> queryAllOrderDetail(String id){
        String sql="SELECT * FROM d_order_detail WHERE s_order_detail_id=?";
        Cursor cursor=db.rawQuery(sql,new String[]{id});
        List<OrderDetailBean> orderDetailBeanList=new ArrayList<>();
        while(cursor.moveToNext()){

            String detail_idT=Tools.getResultString(cursor,"s_order_detail_id");
            String food_idT=Tools.getResultString(cursor,"s_food_id");
            String food_nameT=Tools.getResultString(cursor,"s_food_name");
            String food_desT=Tools.getResultString(cursor,"s_food_describe");
            String food_priceT=Tools.getResultString(cursor,"s_food_price");
            String food_numT=Tools.getResultString(cursor,"s_food_num");
            String food_imgT=Tools.getResultString(cursor,"s_food_img");

            OrderDetailBean orderDetailBean=new OrderDetailBean(detail_idT,food_idT,food_nameT,food_desT,food_priceT,food_numT,food_imgT);

            orderDetailBeanList.add(orderDetailBean);
        }
        return orderDetailBeanList;
    }

    //查询订单
    public static List<OrderBean> queryAllOrders(){
        String sql="SELECT * FROM d_order";
        Cursor cursor=db.rawQuery(sql,null);
        List<OrderBean> orderBeanList=new ArrayList<>();
        while(cursor.moveToNext()){

            String order_idT=Tools.getResultString(cursor,"s_order_id");
            String order_timeT=Tools.getResultString(cursor,"s_order_time");
            String boss_idT=Tools.getResultString(cursor,"s_boss_id");
            String customer_idT=Tools.getResultString(cursor,"s_customer_id");
            String detail_idT=Tools.getResultString(cursor,"s_order_detail_id");
            String order_stateT=Tools.getResultString(cursor,"s_order_state");
            String order_addressT=Tools.getResultString(cursor,"s_order_address");

            OrderBean orderBean=new OrderBean(order_idT,order_timeT,boss_idT,customer_idT,order_stateT,order_addressT,detail_idT);

            orderBeanList.add(orderBean);
        }
        return orderBeanList;
    }

    //根据订单状态查询
    public static List<OrderBean> queryAllOrdersBySta(String account,String sta){
        String sql="SELECT * FROM d_order WHERE s_boss_id=? AND s_order_state=? ORDER BY strftime('%Y-%m-%d %H:%M:%S',s_order_time) DESC";
        String data[]={account,sta};
        Cursor cursor=db.rawQuery(sql,data);
        List<OrderBean> orderBeanList=new ArrayList<>();
        while(cursor.moveToNext()){

            String order_idT=Tools.getResultString(cursor,"s_order_id");
            String order_timeT=Tools.getResultString(cursor,"s_order_time");
            String boss_idT=Tools.getResultString(cursor,"s_boss_id");
            String customer_idT=Tools.getResultString(cursor,"s_customer_id");
            String detail_idT=Tools.getResultString(cursor,"s_order_detail_id");
            String order_stateT=Tools.getResultString(cursor,"s_order_state");
            String order_addressT=Tools.getResultString(cursor,"s_order_address");

            OrderBean orderBean=new OrderBean(order_idT,order_timeT,boss_idT,customer_idT,order_stateT,order_addressT,detail_idT);

            orderBeanList.add(orderBean);
        }
        return orderBeanList;
    }

    public static boolean updateOrderStatus(String orderId, String newStatus){

        String sql="UPDATE d_order set s_order_state=? WHERE s_order_id=?";

        try{
            db.execSQL(sql,new String[]{newStatus,orderId});
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public static List<OrderBean> queryAllOrdersFinished(String account){
        String sql="SELECT * FROM d_order WHERE s_boss_id=? AND s_order_state!=? ORDER BY strftime('%Y-%m-%d %H:%M:%S',s_order_time) DESC";
        String data[]={account,"1"};
        Cursor cursor=db.rawQuery(sql,data);
        List<OrderBean> orderBeanList=new ArrayList<>();
        while(cursor.moveToNext()){

            String order_idT=Tools.getResultString(cursor,"s_order_id");
            String order_timeT=Tools.getResultString(cursor,"s_order_time");
            String boss_idT=Tools.getResultString(cursor,"s_boss_id");
            String customer_idT=Tools.getResultString(cursor,"s_customer_id");
            String detail_idT=Tools.getResultString(cursor,"s_order_detail_id");
            String order_stateT=Tools.getResultString(cursor,"s_order_state");
            String order_addressT=Tools.getResultString(cursor,"s_order_address");

            OrderBean orderBean=new OrderBean(order_idT,order_timeT,boss_idT,customer_idT,order_stateT,order_addressT,detail_idT);

            orderBeanList.add(orderBean);
        }
        return orderBeanList;
    }

}
