package com.example.app_food_manager.DAO;

import android.util.Log;


import com.example.app_food_manager.DTO.NhanVienDTO;
import com.example.app_food_manager.DataBase.DbSqlServer;

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
    public List<NhanVienDTO> getAll(){
        List<NhanVienDTO> list = new ArrayList<>();
        try{
            String sql = "Select * from NhanVien";
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                NhanVienDTO objNhanVien = new NhanVienDTO();
                objNhanVien.setId(resultSet.getInt("id"));
                objNhanVien.setTenNhanVien(resultSet.getString("tenNhanVien"));
                objNhanVien.setTaiKhoan(resultSet.getString("taiKhoan"));
                objNhanVien.setMatKhau(resultSet.getString("matKhau"));
                objNhanVien.setSoDienThoai(resultSet.getString("soDienThoai"));
                objNhanVien.setCCCD(resultSet.getString("CCCD"));
                list.add(objNhanVien);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public int delete(int id){
        int kq = -1;
        try {
            String sql = "DELETE FROM NhanVien WHERE id='" +id +"'";
            Statement statement = this.objConn.createStatement();
            kq = statement.executeUpdate(sql);
        }catch (Exception e){
            kq = -1;
        }
        return kq;
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return objNhanVien;
    }
    public int insert(NhanVienDTO objNhanVien){
        int kq = -1;
        String tenNhanVien = objNhanVien.getTenNhanVien();
        String taiKhoan = objNhanVien.getTaiKhoan();
        String matKhau = objNhanVien.getMatKhau();
        String soDienThoai = objNhanVien.getSoDienThoai();
        String CCCD = objNhanVien.getCCCD();
        try {
            String sql = "INSERT INTO NhanVien(tenNhanVien,taiKhoan,matKhau,soDienThoai,CCCD)\n" +
                    "VALUES (N'"+tenNhanVien+"','" +taiKhoan+"','" +matKhau+"','" +soDienThoai+"','" +CCCD+"');";
            Statement statement = this.objConn.createStatement();
            kq = statement.executeUpdate(sql);
        }catch (Exception e){
            kq = -1;
        }
        return kq;
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
        }catch (Exception e){
            kq = -1;
        }
        return kq;
    }
}
