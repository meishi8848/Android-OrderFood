package com.example.orderfood.Bean;

import java.io.Serializable;

public class BossBean implements Serializable {

    @Override
    public String toString() {
        return "UserBean{" +
                "b_Id='" + b_Id + '\'' +
                ", b_Pwd='" + b_Pwd + '\'' +
                ", b_Name='" + b_Name + '\'' +
                ", b_Des='" + b_Des + '\'' +
                ", b_Type='" + b_Type + '\'' +
                ", b_Img='" + b_Img + '\'' +
                '}';
    }

    public String getB_Id() {
        return b_Id;
    }

    public void setB_Id(String b_Id) {
        this.b_Id = b_Id;
    }

    public String getB_Pwd() {
        return b_Pwd;
    }

    public void setB_Pwd(String b_Pwd) {
        this.b_Pwd = b_Pwd;
    }

    public String getB_Name() {
        return b_Name;
    }

    public void setB_Name(String b_Name) {
        this.b_Name = b_Name;
    }

    public String getB_Des() {
        return b_Des;
    }

    public void setB_Des(String b_Des) {
        this.b_Des = b_Des;
    }

    public String getB_Type() {
        return b_Type;
    }

    public void setB_Type(String b_Type) {
        this.b_Type = b_Type;
    }

    public String getB_Img() {
        return b_Img;
    }

    public void setB_Img(String b_Img) {
        this.b_Img = b_Img;
    }

    public BossBean() {
    }

    public BossBean(String b_Id, String b_Pwd, String b_Name, String b_Des, String b_Type, String b_Img) {
        this.b_Id = b_Id;
        this.b_Pwd = b_Pwd;
        this.b_Name = b_Name;
        this.b_Des = b_Des;
        this.b_Type = b_Type;
        this.b_Img = b_Img;
    }

    private String b_Id;
    private String b_Pwd;
    private String b_Name;
    private String b_Des;
    private String b_Type;
    private String b_Img;

}
