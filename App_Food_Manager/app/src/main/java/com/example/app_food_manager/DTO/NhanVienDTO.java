package com.example.app_food_manager.DTO;

public class NhanVienDTO {
    int id;
    String tenNhanVien;
    String taiKhoan;
    String matKhau;
    String namSinh;
    String CCCD;

    public NhanVienDTO() {
    }

    public NhanVienDTO(String tenNhanVien, String taiKhoan, String matKhau, String namSinh, String CCCD) {
        this.tenNhanVien = tenNhanVien;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.namSinh = namSinh;
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

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }
}
