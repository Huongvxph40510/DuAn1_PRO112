package com.example.app_food_manager.DAO;

import android.util.Log;

import com.example.app_food_manager.DTO.BanDTO;
import com.example.app_food_manager.DataBase.DbSqlServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietDAO {
    Connection objConn;
    public  HoaDonChiTietDAO(){
        DbSqlServer db = new DbSqlServer();
        objConn = db.openConnect();
    }

    public int tongTien(int id){
        int tongTien = 0;
        try{
            String sql = "select SUM(HDCT.soLuong * MA.gia) from HoaDonChiTiet HDCT\n" +
                    "join MonAn MA on HDCT.idMonAn = MA.id\n" +
                    "where idHoaDon = " + id;
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                tongTien = resultSet.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tongTien;
    }
    public int getDoanhThu(String tuNgay, String denNgay){
        int tongTien = 0;
        try{
            String sql = "select SUM(HDCT.soLuong * MA.gia) from HoaDonChiTiet HDCT\n" +
                    "join MonAn MA on HDCT.idMonAn = MA.id\n" +
                    "join HoaDon HD on HDCT.idHoaDon = HD.id\n" +
                    "where hd.ngayTao between '"+tuNgay+"' and  '"+denNgay+"'";
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                tongTien = resultSet.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tongTien;
    }

}