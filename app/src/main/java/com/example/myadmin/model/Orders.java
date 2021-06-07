package com.example.myadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orders {
    @SerializedName("Id")
    @Expose
    private int Id;
    @SerializedName("UserId")
    @Expose
    private int UserId;
    @SerializedName("BuyingDay")
    @Expose
    private String BuyingDay;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Phone")
    @Expose
    private String Phone;
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("Note")
    @Expose
    private String Note;
    @SerializedName("StatusId")
    @Expose
    private int StatusId ;

    public Orders(int id, int userId, String buyingDay, String name, String phone, String email, String address, String note, int statusId) {
        Id = id;
        UserId = userId;
        BuyingDay = buyingDay;
        Name = name;
        Phone = phone;
        Email = email;
        Address = address;
        Note = note;
        StatusId = statusId;
    }
    public Orders(){}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getBuyingDay() {
        return BuyingDay;
    }

    public void setBuyingDay(String buyingDay) {
        BuyingDay = buyingDay;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }
}
