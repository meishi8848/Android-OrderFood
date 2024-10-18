package com.example.orderfood.Bean;

import java.io.Serializable;

public class UserBean implements Serializable {

    @Override
    public String toString() {
        return "UserBean{" +
                "u_Id='" + u_Id + '\'' +
                ", u_Pwd='" + u_Pwd + '\'' +
                ", u_Name='" + u_Name + '\'' +
                ", u_sex='" + u_sex + '\'' +
                ", u_Address='" + u_Address + '\'' +
                ", u_Phone='" + u_Phone + '\'' +
                ", u_Img='" + u_Img + '\'' +
                '}';
    }

    public String getU_Id() {
        return u_Id;
    }

    public void setU_Id(String u_Id) {
        this.u_Id = u_Id;
    }

    public String getU_Pwd() {
        return u_Pwd;
    }

    public void setU_Pwd(String u_Pwd) {
        this.u_Pwd = u_Pwd;
    }

    public String getU_Name() {
        return u_Name;
    }

    public void setU_Name(String u_Name) {
        this.u_Name = u_Name;
    }

    public String getU_sex() {
        return u_sex;
    }

    public void setU_sex(String u_sex) {
        this.u_sex = u_sex;
    }

    public String getU_Address() {
        return u_Address;
    }

    public void setU_Address(String u_Address) {
        this.u_Address = u_Address;
    }

    public String getU_Phone() {
        return u_Phone;
    }

    public void setU_Phone(String u_Phone) {
        this.u_Phone = u_Phone;
    }

    public String getU_Img() {
        return u_Img;
    }

    public void setU_Img(String u_Img) {
        this.u_Img = u_Img;
    }

    public UserBean() {
    }

    public UserBean(String u_Id, String u_Pwd, String u_Name, String u_sex, String u_Address, String u_Phone, String u_Img) {
        this.u_Id = u_Id;
        this.u_Pwd = u_Pwd;
        this.u_Name = u_Name;
        this.u_sex = u_sex;
        this.u_Address = u_Address;
        this.u_Phone = u_Phone;
        this.u_Img = u_Img;
    }

    private String u_Id;
    private String u_Pwd;
    private String u_Name;
    private String u_sex;
    private String u_Address;
    private String u_Phone;
    private String u_Img;

}
