package com.example.app_food_staff.DTO;

public class BanDTO {
    int id;
    String soBan;
    int trangThai;

    public BanDTO() {
    }

    public BanDTO(String soBan) {
        this.soBan = soBan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSoBan() {
        return soBan;
    }

    public void setSoBan(String soBan) {
        this.soBan = soBan;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
