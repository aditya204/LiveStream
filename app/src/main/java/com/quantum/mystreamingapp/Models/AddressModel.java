package com.quantum.mystreamingapp.Models;

public class AddressModel {
    private String nmae,phone,type,details,altrtnatePhn;
    private int size;

    public AddressModel(String nmae, String phone, String type, String details, String alternatePhn, int size) {
        this.nmae = nmae;
        this.phone = phone;
        this.type = type;
        this.details = details;


        this.altrtnatePhn=alternatePhn;
        this.size=size;}

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAltrtnatePhn() {
        return altrtnatePhn;
    }

    public void setAltrtnatePhn(String altrtnatePhn) {
        this.altrtnatePhn = altrtnatePhn;
    }



    public String getNmae() {
        return nmae;
    }

    public void setNmae(String nmae) {
        this.nmae = nmae;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
