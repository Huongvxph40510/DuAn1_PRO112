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
            Log.e("zzzzzzzzzz", "getAll: đọc oke ");
        } catch (Exception e) {
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu ");
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
            Log.e("zzzzzzzzzz", "getAll: xóa oke" );
        }catch (Exception e){
            kq = -1;
            Log.e("zzzzzzzzzz", "getAll: xóa khong thành công" );
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
            Log.e("zzzzzzzzzz", "getAll: xóa oke" );
        }catch (Exception e){
            id = -1;
            Log.e("zzzzzzzzzz", "getAll: xóa khong thành công" );
        }
        return id;
    }

    public int setTrangThai(int idHoaDon){
        int kq = -1;
        try {
            String sql = "UPDATE HoaDon\n" +
                    "SET trangThai = "+ 1 +" \n" +
                    "WHERE id = '"+ idHoaDon + "';";
            Statement statement = this.objConn.createStatement();
            kq = statement.executeUpdate(sql);
            Log.e("zzzzzzzzzz", "getAll: xóa oke" );
        }catch (Exception e){
            kq = -1;
            Log.e("zzzzzzzzzz", "getAll: xóa khong thành công" );
        }
        return kq;
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
            Log.e("zzzzzzzzzz", "getAll: đọc oke " );
        }catch (Exception e){
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu " );
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
            Log.e("zzzzzzzzzz", "getAll: đọc oke " );
        }catch (Exception e){
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu " );
            e.printStackTrace();
        }
        return tongHoaDon;
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
            Log.e("zzzzzzzzzz", "getAll: đọc oke " );
        }catch (Exception e){
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu " );
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
            Log.e("zzzzzzzzzz", "getAll: đọc oke " );
        }catch (Exception e){
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu " );
            e.printStackTrace();
        }
        return tongHoaDon;
    }
}