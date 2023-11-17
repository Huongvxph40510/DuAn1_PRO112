package com.example.app_food_manager.DTO;

public class MonAnDTO {
    int id;
    String tenMonAn;
    int gia;
    byte[] hinhAnh;

    public MonAnDTO() {
    }

    public MonAnDTO(String tenMonAn, int gia, byte[] hinhAnh) {
        this.tenMonAn = tenMonAn;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
