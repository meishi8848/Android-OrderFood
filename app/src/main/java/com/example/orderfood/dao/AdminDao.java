package com.example.orderfood.dao;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.Bean.BossBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.db.DBUntil;

/**
 * 操作数据库
 */
public class AdminDao {

    public static SQLiteDatabase db=DBUntil.con;

    /**
     * 实现保存商家数据
     * @param id
     * @param pwd
     * @param name
     * @param des
     * @param type
     * @param head
     * @return
     */
    public static int saveBossUser(String id,String pwd,String name,String des,String type,String head){
        String data[]={id,pwd,name,des,type,head};

        try{
            db.execSQL("insert into d_business(s_id,s_pwd,s_name,s_describe,s_type,s_img)"+" values(?,?,?,?,?,?)",
                    data);
            return 1;
        }catch (Exception e){
            return 0;
        }

    }

    /**
     * 实现保存顾客用户数据
     * @param id
     * @param pwd
     * @param name
     * @param sex
     * @param address
     * @param phone
     * @param head
     * @return
     */
    public static int saveCustomerUser(String id,String pwd,String name,String sex,String address,String phone,String head){
        String data[]={id,pwd,name,sex,address,phone,head};

        try{
            db.execSQL("insert into d_user(s_id,s_pwd,s_name,s_sex,s_address,s_phone,s_img)"+" values(?,?,?,?,?,?,?)",
                    data);
            return 1;
        }catch (Exception e){
            return 0;
        }

    }

    /**
     * 更新普通用户个人信息
     * @param id
     * @param name
     * @param sex
     * @param address
     * @param phone
     * @param head
     * @return
     */
    public static int UpdateCustomerUser(String id,String name,String sex,String address,String phone,String head){
        String data[]={name,sex,address,phone,head,id};

        try{
            db.execSQL("UPDATE d_user set s_name=?,s_sex=?,s_address=?,s_phone=?,s_img=? WHERE s_id=? ",
                    data);
            return 1;
        }catch (Exception e){
            return 0;
        }

    }

    /**
     * 登录顾客用户
     * @param id
     * @param pwd
     * @return
     */
    public static boolean loginUser(String id,String pwd){
        String data[]={id,pwd};
        String sql="select * from d_user where s_id=? and s_pwd=?";
        Cursor result=db.rawQuery(sql,data);
        while(result.moveToNext()){
            return true;
        }
        result.close();
        return false;
    }

    /**
     * 登录店铺账户
     * @param id
     * @param pwd
     * @return
     */
    public static boolean loginBoss(String id,String pwd){
        String data[]={id,pwd};
        String sql="select * from d_business where s_id=? and s_pwd=?";
        Cursor result=db.rawQuery(sql,data);
        while(result.moveToNext()){
            return true;
        }
        result.close();
        return false;
    }

    /**
     * 获取店铺用户的相关信息
     * @param account
     * @return
     */
    @SuppressLint("Range")
    public static BossBean getBossInformation(String account){
        String data[]={account};
        String sql="select * from d_business where s_id=?";
        Cursor result=db.rawQuery(sql,data);
        while(result.moveToNext()){
            BossBean bossBean=new BossBean(result.getString(result.getColumnIndex("s_id")),
                    result.getString(result.getColumnIndex("s_pwd")) ,
                    result.getString(result.getColumnIndex("s_name")) ,
                    result.getString(result.getColumnIndex("s_describe")) ,
                    result.getString(result.getColumnIndex("s_type")) ,
                    result.getString(result.getColumnIndex("s_img"))
            );

            return bossBean;
        }
        result.close();
        return null;
    }

    /**
     * 更改商家信息
     * @param id
     * @param name
     * @param des
     * @param type
     * @param head
     * @return
     */
    public static int UpdateBossUser(String id,String name,String des,String type,String head){
        String data[]={name,des,type,head,id};

        try{
            db.execSQL("update d_business set s_name=?, s_describe=?, s_type=?, s_img=? WHERE s_id=?",
                    data);
            return 1;
        }catch (Exception e){
            return 0;
        }

    }

    public static int UpdateBossUserPwd(String id,String pwd){
        String data[]={pwd,id};

        try{
            db.execSQL("update d_business set s_pwd=? WHERE s_id=?",
                    data);
            return 1;
        }catch (Exception e){
            return 0;
        }

    }

    /**
     * 修改顾客用户密码
     * @param id
     * @param pwd
     * @return
     */
    public static int UpdateCustomerUserPwd(String id,String pwd){
        String data[]={pwd,id};

        try{
            db.execSQL("update d_user set s_pwd=? WHERE s_id=?",
                    data);
            return 1;
        }catch (Exception e){
            return 0;
        }

    }

    /**
     * 获取顾客用户的相关信息
     * @param account
     * @return
     */
    @SuppressLint("Range")
    public static UserBean getCustomerInformation(String account){
        String data[]={account};
        String sql="select * from d_user where s_id=?";
        Cursor result=db.rawQuery(sql,data);
        while(result.moveToNext()){
            UserBean userBean=new UserBean(result.getString(result.getColumnIndex("s_id")),
                    result.getString(result.getColumnIndex("s_pwd")) ,
                    result.getString(result.getColumnIndex("s_name")) ,
                    result.getString(result.getColumnIndex("s_sex")) ,
                    result.getString(result.getColumnIndex("s_address")) ,
                    result.getString(result.getColumnIndex("s_phone")) ,
                    result.getString(result.getColumnIndex("s_img"))
            );

            return userBean;
        }
        result.close();
        return null;
    }

}
