package com.example.donationapp.COMMON;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FillData implements Parcelable {
    private String name;
    private String dntid;
    private String fid;
    private String uid;

    protected FillData(Parcel in) {
        name = in.readString();
        dntid = in.readString();
        fid = in.readString();
        uid = in.readString();
        detail = in.readString();
        qty = in.readString();
        address = in.readString();
        lat = in.readString();
        lot = in.readString();
        email = in.readString();
        img = in.readString();
        phone = in.readString();
        pass = in.readString();
        msg = in.readString();
        description = in.readString();
        image = in.readString();
        sid = in.readString();
        id = in.readString();
        duration = in.readString();
        type = in.readString();
        status = in.readString();
    }

    public static final Creator<FillData> CREATOR = new Creator<FillData>() {
        @Override
        public FillData createFromParcel(Parcel in) {
            return new FillData(in);
        }

        @Override
        public FillData[] newArray(int size) {
            return new FillData[size];
        }
    };

    public String getDntid() {
        return dntid;
    }

    public void setDntid(String dntid) {
        this.dntid = dntid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    private String detail;
    private String qty;
    private String address;
    private String lat;
    private String lot;
    private String email;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;
    private String phone;
    private String pass;
    private String msg;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String image;

    private String sid;
    private String id;
    private String duration;
    private String type;
    private String status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(dntid);
        dest.writeString(fid);
        dest.writeString(uid);
        dest.writeString(detail);
        dest.writeString(qty);
        dest.writeString(address);
        dest.writeString(lat);
        dest.writeString(lot);
        dest.writeString(email);
        dest.writeString(img);
        dest.writeString(phone);
        dest.writeString(pass);
        dest.writeString(msg);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(sid);
        dest.writeString(id);
        dest.writeString(duration);
        dest.writeString(type);
        dest.writeString(status);
    }
}

