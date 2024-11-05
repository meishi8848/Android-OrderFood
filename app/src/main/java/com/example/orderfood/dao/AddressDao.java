package com.example.orderfood.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.Bean.AddressBean;
import com.example.orderfood.db.DBUntil;
import com.example.orderfood.until.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
         rs.close();
         return list;
    }

    /**
     * 修改收货地址
     * @param id
     * @param name
     * @param address
     * @param phone
     * @return
     */
    public static boolean updateAddress(String id,String name,String address,String phone){
        try{
            db.execSQL("UPDATE d_receiveAddress set s_customer_name=?,s_customer_address=?,s_customer_phone=? WHERE s_address_id=?",new String[]{name,address,phone,id});
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 添加收货地址
     * @param id
     * @param name
     * @param address
     * @param phone
     * @return
     */
    public static boolean addAddress(String id,String name,String address,String phone){
        try{
            String uuid= UUID.randomUUID().toString().replace("-","");
            db.execSQL("INSERT INTO d_receiveAddress(s_address_id,s_customer_id,s_customer_name,s_customer_address,s_customer_phone)"+"VALUES(?,?,?,?,?)",
                    new String[]{uuid,id,name,address,phone});
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    public static boolean deleteAddressById(String id){
        try{
            db.execSQL("DELETE FROM d_receiveAddress WHERE s_address_id=?",new String[]{id});
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
