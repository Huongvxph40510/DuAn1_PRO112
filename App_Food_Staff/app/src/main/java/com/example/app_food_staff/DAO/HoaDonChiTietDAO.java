package com.example.app_food_staff.DAO;

import android.util.Log;

import com.example.app_food_staff.DTO.HoaDonChiTietDTO;
import com.example.app_food_staff.DTO.HoaDonDTO;
import com.example.app_food_staff.DTO.MonAnDTO;
import com.example.app_food_staff.DataBase.DbSqlServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietDAO {
    Connection objConn;
    public HoaDonChiTietDAO(){
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
    public int insert(HoaDonChiTietDTO objHoaDonChiTiet){
        int kq = -1;
        int idMonAn = objHoaDonChiTiet.getIdMonAn();
        int idHoaDon = objHoaDonChiTiet.getIdHoaDon();
        int soLuong = objHoaDonChiTiet.getSoLuong();
        try {
            String sql = "INSERT INTO HoaDonChiTiet(idHoaDon,idMonAn,soLuong)\n" +
                    "VALUES ("+idHoaDon+"," +idMonAn+"," +soLuong+");";
            Statement statement = this.objConn.createStatement();
            kq = statement.executeUpdate(sql);
        }catch (Exception e){
            kq = -1;
        }
        return kq;
    }
    public List<HoaDonChiTietDTO> getTheoIdHoaDon(int idHoaDon){
        List<HoaDonChiTietDTO> list = new ArrayList<>();
        try{
            String sql = "Select * from HoaDonChiTiet where idHoaDon = " + idHoaDon ;
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                HoaDonChiTietDTO item = new HoaDonChiTietDTO();
                item.setIdHoaDon(resultSet.getInt("idHoaDon"));
                item.setIdMonAn(resultSet.getInt("idMonAn"));
                item.setSoLuong(resultSet.getInt("soLuong"));
                list.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public int update(HoaDonChiTietDTO objHoaDonChiTiet){
        int kq = -1;
        int idMonAn = objHoaDonChiTiet.getIdMonAn();
        int idHoaDon = objHoaDonChiTiet.getIdHoaDon();
        int soLuong = objHoaDonChiTiet.getSoLuong();
        try {
            String sql = "UPDATE HoaDonChiTiet\n" +
                    "SET soLuong = "+ soLuong +"\n" +
                    "WHERE idMonAn = '"+ idMonAn + "' and idHoaDon = '" +idHoaDon+ "';";
            Statement statement = this.objConn.createStatement();
            kq = statement.executeUpdate(sql);
        }catch (Exception e){
            kq = -1;
        }
        return kq;
    }
}