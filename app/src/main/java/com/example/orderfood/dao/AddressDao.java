package com.example.orderfood.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.Bean.AddressBean;
import com.example.orderfood.db.DBUntil;
import com.example.orderfood.until.Tools;

import java.util.ArrayList;
import java.util.List;

public class AddressDao {

    public static SQLiteDatabase db= DBUntil.con;

    /**
     * 查询地址簿
     * @param userId
     * @return
     */
    public static List<AddressBean> getAllAddressByUserId(String userId){
         Cursor rs=db.rawQuery("SELECT * FROM d_receiveAddress WHERE s_customer_id=?",new String[]{userId});
         List<AddressBean> list=new ArrayList<>();
         while (rs.moveToNext()){
             AddressBean addressBean=new AddressBean();
             addressBean.setAddressId(Tools.getResultString(rs,"s_address_id"));
             addressBean.setCustomerId(Tools.getResultString(rs,"s_customer_id"));
             addressBean.setCustomerName(Tools.getResultString(rs,"s_customer_name"));
             addressBean.setCustomerAddress(Tools.getResultString(rs,"s_customer_address"));
             addressBean.setCustomerPhone(Tools.getResultString(rs,"s_customer_phone"));

             list.add(addressBean);
         }
         return list;
    }

}
