package com.example.app_food_staff.DAO;

import android.util.Log;

import com.example.app_food_staff.DTO.NhanVienDTO;
import com.example.app_food_staff.DataBase.DbSqlServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    Connection objConn;
    public  NhanVienDAO(){
        DbSqlServer db = new DbSqlServer();
        objConn = db.openConnect();
    }

    public NhanVienDTO checkLogin(String taiKhoan, String matKhau){
        NhanVienDTO objNhanVien = null;
        try{
            String sql = "Select * from NhanVien where taiKhoan = '" + taiKhoan + "' AND matKhau = '" + matKhau + "'";
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                objNhanVien = new NhanVienDTO();
                objNhanVien.setId(resultSet.getInt("id"));
                objNhanVien.setTenNhanVien(resultSet.getString("tenNhanVien"));
                objNhanVien.setTaiKhoan(resultSet.getString("taiKhoan"));
                objNhanVien.setMatKhau(resultSet.getString("matKhau"));
                objNhanVien.setSoDienThoai(resultSet.getString("soDienThoai"));
                objNhanVien.setCCCD(resultSet.getString("CCCD"));
            }
            Log.e("zzzzzzzzzz", "getAll: đọc oke " );
        }catch (Exception e){
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu " );
            e.printStackTrace();
        }
        return objNhanVien;
    }

    public NhanVienDTO getID(int id){
        NhanVienDTO objNhanVien = new NhanVienDTO();
        try{
            String sql = "Select * from NhanVien where id =" + id;
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                objNhanVien.setId(resultSet.getInt("id"));
                objNhanVien.setTenNhanVien(resultSet.getString("tenNhanVien"));
                objNhanVien.setTaiKhoan(resultSet.getString("taiKhoan"));
                objNhanVien.setMatKhau(resultSet.getString("matKhau"));
                objNhanVien.setSoDienThoai(resultSet.getString("soDienThoai"));
                objNhanVien.setCCCD(resultSet.getString("CCCD"));
            }
            Log.e("zzzzzzzzzz", "getAll: đọc oke " );
        }catch (Exception e){
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu " );
            e.printStackTrace();
        }
        return objNhanVien;
    }
    public int update(NhanVienDTO objNhanVien){
        int kq = -1;
        String tenNhanVien = objNhanVien.getTenNhanVien();
        String taiKhoan = objNhanVien.getTaiKhoan();
        String matKhau = objNhanVien.getMatKhau();
        String soDienThoai = objNhanVien.getSoDienThoai();
        String CCCD = objNhanVien.getCCCD();
        int id = objNhanVien.getId();
        try {
            String sql = "UPDATE NhanVien\n" +
                    "SET tenNhanVien = N'"+ tenNhanVien +"',taiKhoan= '"+taiKhoan+"',matKhau= '"+matKhau+"',soDienThoai= '"+soDienThoai+"',CCCD= '"+CCCD+ "'\n" +
                    "WHERE id = '"+ id + "';";
            Statement statement = this.objConn.createStatement();
            kq = statement.executeUpdate(sql);
            Log.e("zzzzzzzzzz", "getAll: xóa oke" );
        }catch (Exception e){
            kq = -1;
            Log.e("zzzzzzzzzz", "getAll: xóa khong thành công" );
        }
        return kq;
    }
}
