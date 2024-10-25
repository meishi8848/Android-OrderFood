package com.example.orderfood.Bean;

public class AddressBean {

    private String addressId;
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;

    public AddressBean(String addressId, String customerId, String customerName, String customerAddress, String customerPhone) {
        this.addressId = addressId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
    }

    public AddressBean() {
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "addressId='" + addressId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                '}';
    }
}
