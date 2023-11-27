package com.example.app_food_staff.DTO;

public class HoaDonChiTietDTO {
    int idHoaDon;
    int idMonAn;
    int soLuong;

    public HoaDonChiTietDTO() {
    }

    public HoaDonChiTietDTO(int idHoaDon, int idMonAn, int soLuong) {
        this.idHoaDon = idHoaDon;
        this.idMonAn = idMonAn;
        this.soLuong = soLuong;
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public int getIdMonAn() {
        return idMonAn;
    }

    public void setIdMonAn(int idMonAn) {
        this.idMonAn = idMonAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
