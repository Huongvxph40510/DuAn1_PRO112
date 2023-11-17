package com.example.app_food_manager.DTO;

import java.util.Date;

public class HoaDonDTO {
    int id;
    Date ngayTao;
    int idBan;
    int idNhanVienTao;
    int idNhanVienThanToan;
    int trangThai;

    public HoaDonDTO() {
    }

    public HoaDonDTO(Date ngayTao, int idBan, int idNhanVienTao, int idNhanVienThanToan, int trangThai) {
        this.ngayTao = ngayTao;
        this.idBan = idBan;
        this.idNhanVienTao = idNhanVienTao;
        this.idNhanVienThanToan = idNhanVienThanToan;
        this.trangThai = trangThai;
    }

    public HoaDonDTO(Date ngayTao, int idBan, int idNhanVienTao, int trangThai) {
        this.ngayTao = ngayTao;
        this.idBan = idBan;
        this.idNhanVienTao = idNhanVienTao;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getIdBan() {
        return idBan;
    }

    public void setIdBan(int idBan) {
        this.idBan = idBan;
    }

    public int getIdNhanVienTao() {
        return idNhanVienTao;
    }

    public void setIdNhanVienTao(int idNhanVienTao) {
        this.idNhanVienTao = idNhanVienTao;
    }

    public int getIdNhanVienThanToan() {
        return idNhanVienThanToan;
    }

    public void setIdNhanVienThanToan(int idNhanVienThanToan) {
        this.idNhanVienThanToan = idNhanVienThanToan;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
