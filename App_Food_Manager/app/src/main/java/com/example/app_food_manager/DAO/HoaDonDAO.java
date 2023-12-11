package com.example.app_food_manager.DAO;

import android.util.Log;

import com.example.app_food_manager.DTO.HoaDonDTO;
import com.example.app_food_manager.DTO.MonAnDTO;
import com.example.app_food_manager.DataBase.DbSqlServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    Connection objConn;


    public HoaDonDAO() {
        DbSqlServer db = new DbSqlServer();
        objConn = db.openConnect();
    }

    public List<HoaDonDTO> getAll() {
        List<HoaDonDTO> list = new ArrayList<>();
        try {
            String sql = "select * from HoaDon";
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                HoaDonDTO objHoaDon = new HoaDonDTO();
                objHoaDon.setId(resultSet.getInt(1));
                objHoaDon.setNgayTao(resultSet.getDate(2));
                objHoaDon.setIdBan(resultSet.getInt(3));
                objHoaDon.setIdNhanVienTao(resultSet.getInt(4));
                objHoaDon.setIdNhanVienThanToan(resultSet.getInt(5));
                objHoaDon.setTrangThai(resultSet.getInt(6));
                list.add(objHoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonDTO> getByDate(String ngay) {
        List<HoaDonDTO> list = new ArrayList<>();
        try {
            String sql = "select * from HoaDon where ngayTao ='" + ngay + "'";
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                HoaDonDTO objHoaDon = new HoaDonDTO();
                objHoaDon.setId(resultSet.getInt(1));
                objHoaDon.setNgayTao(resultSet.getDate(2));
                objHoaDon.setIdBan(resultSet.getInt(3));
                objHoaDon.setIdNhanVienTao(resultSet.getInt(4));
                objHoaDon.setIdNhanVienThanToan(resultSet.getInt(5));
                objHoaDon.setTrangThai(resultSet.getInt(6));
                list.add(objHoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getSoHoaDon(String ngay) {
        int tongHoaDon = 0;
        try {
            String sql = "select COUNT(id) from HoaDon\n" +
                    "where ngayTao ='" + ngay + "'";
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                tongHoaDon = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongHoaDon;
    }

}