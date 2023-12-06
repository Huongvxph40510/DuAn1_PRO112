package com.example.app_food_staff.DTO;

public class NhanVienDTO {
    int id;
    String tenNhanVien;
    String taiKhoan;
    String matKhau;
    String soDienThoai;
    String CCCD;

    public NhanVienDTO() {
    }

    public NhanVienDTO(int id, String tenNhanVien, String taiKhoan, String matKhau, String soDienThoai, String CCCD) {
        this.id = id;
        this.tenNhanVien = tenNhanVien;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.soDienThoai = soDienThoai;
        this.CCCD = CCCD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }
}
