package com.example.app_food_staff.DAO;

import android.util.Log;

import com.example.app_food_staff.DTO.HoaDonChiTietDTO;
import com.example.app_food_staff.DTO.HoaDonDTO;
import com.example.app_food_staff.DTO.NhanVienDTO;
import com.example.app_food_staff.DataBase.DbSqlServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonDAO {
    Connection objConn;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    public HoaDonDAO() {
        DbSqlServer db = new DbSqlServer();
        objConn = db.openConnect();
    }

    public List<HoaDonDTO> getAll() {
        List<HoaDonDTO> list = new ArrayList<>();
        Date d = new Date();
        String ngay = sdf.format(d);
        try {
            String sql = "select * from HoaDon where ngayTao = '" + ngay + "' and trangThai = 0" ;
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
    public int insert(HoaDonDTO objHoaDon){
        int kq = -1;
        int idNhanVienTao = objHoaDon.getIdNhanVienTao();
        int idBan = objHoaDon.getIdBan();
        String ngayTao = sdf.format(objHoaDon.getNgayTao());
        try {
            String sql = "INSERT INTO HoaDon(idNhanVienTao,idBan,ngayTao)\n" +
                    "VALUES ("+idNhanVienTao+"," +idBan+",'" +ngayTao+"');";
            Statement statement = this.objConn.createStatement();
            kq = statement.executeUpdate(sql);
        }catch (Exception e){
            kq = -1;
        }
        return kq;
    }

    public int idNew(){
        int id = -1;
        try {
            String sql = "Select top 1 * from HoaDon order by id desc ";
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        }catch (Exception e){
            id = -1;
        }
        return id;
    }
    public List<HoaDonDTO> getTheoNgayTao(String ngay, int idNhanVienTao){
        List<HoaDonDTO> list = new ArrayList<>();
        try{
            String sql = "select * from HoaDon where ngayTao ='"+ngay+"' and idNhanVienTao = " +idNhanVienTao;
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                HoaDonDTO objHoaDon = new HoaDonDTO();
                objHoaDon.setId(resultSet.getInt(1));
                objHoaDon.setNgayTao(resultSet.getDate(2));
                objHoaDon.setIdBan(resultSet.getInt(3));
                objHoaDon.setIdNhanVienTao(resultSet.getInt(4));
                objHoaDon.setIdNhanVienThanToan(resultSet.getInt(5));
                objHoaDon.setTrangThai(resultSet.getInt(6));
                list.add(objHoaDon);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public int getSoHoaDonTao(String ngay, int idNhanVienTao){
        int tongHoaDon = 0;
        try{
            String sql = "select COUNT(id) from HoaDon\n" +
                    "where ngayTao ='"+ngay+"' and idNhanVienTao = " +idNhanVienTao;
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                tongHoaDon = resultSet.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tongHoaDon;
    }
    public List<HoaDonDTO> getTheoNgayThanhToan(String ngay, int idNhanVienThanhToan){
        List<HoaDonDTO> list = new ArrayList<>();
        try{
            String sql = "select * from HoaDon where ngayTao ='"+ngay+"' and idNhanVienThanhToan = " +idNhanVienThanhToan;
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                HoaDonDTO objHoaDon = new HoaDonDTO();
                objHoaDon.setId(resultSet.getInt(1));
                objHoaDon.setNgayTao(resultSet.getDate(2));
                objHoaDon.setIdBan(resultSet.getInt(3));
                objHoaDon.setIdNhanVienTao(resultSet.getInt(4));
                objHoaDon.setIdNhanVienThanToan(resultSet.getInt(5));
                objHoaDon.setTrangThai(resultSet.getInt(6));
                list.add(objHoaDon);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public int getSoHoaDonThanhToan(String ngay, int idNhanVienThanhToan){
        int tongHoaDon = 0;
        try{
            String sql = "select COUNT(id) from HoaDon\n" +
                    "where ngayTao ='"+ngay+"' and idNhanVienThanhToan = " +idNhanVienThanhToan;
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                tongHoaDon = resultSet.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tongHoaDon;
    }
    public int thanhToan(int idHoaDon, int idNV){
        int kq = -1;
        try {
            String sql = "UPDATE HoaDon\n" +
                    "SET trangThai = "+ 1 +",idNhanVienThanhToan= "+idNV+" \n" +
                    "WHERE id = '"+ idHoaDon + "';";
            Statement statement = this.objConn.createStatement();
            kq = statement.executeUpdate(sql);
        }catch (Exception e){
            kq = -1;
        }
        return kq;
    }
    public HoaDonDTO getHoaDonTheoBan(int idban){
        HoaDonDTO objHoaDon = new HoaDonDTO();
        try {
            String sql = "Select * from HoaDon where idBan = "+idban+" and trangThai = 0";
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                objHoaDon.setId(resultSet.getInt(1));
                objHoaDon.setNgayTao(resultSet.getDate(2));
                objHoaDon.setIdBan(resultSet.getInt(3));
                objHoaDon.setIdNhanVienTao(resultSet.getInt(4));
                objHoaDon.setIdNhanVienThanToan(resultSet.getInt(5));
                objHoaDon.setTrangThai(resultSet.getInt(6));
            }
        }catch (Exception e){
        }
        return objHoaDon;
    }
}
