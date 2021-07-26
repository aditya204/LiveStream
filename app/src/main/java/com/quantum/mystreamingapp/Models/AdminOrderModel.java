package com.quantum.mystreamingapp.Models;

public class AdminOrderModel {

    private String order_id,time,otp,grandTotal;
    private boolean is_paid,is_pickup;

    public AdminOrderModel(String order_id, String time, String otp, String grandTotal, boolean is_paid, boolean is_pickup) {
        this.order_id = order_id;
        this.time = time;
        this.otp = otp;
        this.grandTotal = grandTotal;
        this.is_paid = is_paid;
        this.is_pickup=is_pickup;
    }

    public boolean isIs_pickup() {
        return is_pickup;
    }

    public void setIs_pickup(boolean is_pickup) {
        this.is_pickup = is_pickup;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public boolean isIs_paid() {
        return is_paid;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }
}
