package com.example.orderfood.Bean;

import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.dao.OrderDao;

import java.util.List;

public class OrderBean {

    @Override
    public String toString() {
        return "OrderBean{" +
                "order_id='" + order_id + '\'' +
                ", order_time='" + order_time + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", boss_id='" + boss_id + '\'' +
                ", order_state='" + order_state + '\'' +
                ", order_address='" + order_address + '\'' +
                ", order_detail_id='" + order_detail_id + '\'' +
                '}';
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getBoss_id() {
        return boss_id;
    }

    public void setBoss_id(String boss_id) {
        this.boss_id = boss_id;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    public String getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(String order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public OrderBean() {
    }

    public OrderBean(String order_id, String order_time, String boss_id, String customer_id, String order_state, String order_address, String order_detail_id) {
        this.order_id = order_id;
        this.order_time = order_time;
        this.customer_id = customer_id;
        this.boss_id = boss_id;
        this.order_state = order_state;
        this.order_address = order_address;
        this.order_detail_id = order_detail_id;
        this.orderDetailBeanList = OrderDao.queryAllOrderDetail(order_detail_id);
        String uname=AdminDao.getCustomerInformation(customer_id).getU_Name();
        this.userName=uname;
    }

    private String order_id;
    private String order_time;
    private String customer_id;
    private String boss_id;
    private String order_state;
    private String order_address;
    private String order_detail_id;
    private List<OrderDetailBean> orderDetailBeanList;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<OrderDetailBean> getOrderDetailBeanList() {
        return orderDetailBeanList;
    }

    public void setOrderDetailBeanList(List<OrderDetailBean> orderDetailBeanList) {
        this.orderDetailBeanList = orderDetailBeanList;
    }
}
