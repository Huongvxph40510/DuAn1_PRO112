package com.example.app_food_manager.DAO;

import android.util.Log;

import com.example.app_food_manager.DTO.BanDTO;
import com.example.app_food_manager.DataBase.DbSqlServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BanDAO {
    Connection objConn;
    public  BanDAO(){
        DbSqlServer db = new DbSqlServer();
        objConn = db.openConnect();
    }
    public List<BanDTO> getAll(){
        List<BanDTO> list = new ArrayList<>();
        try{
            String sql = "Select * from Ban";
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                BanDTO objBan = new BanDTO();
                objBan.setId(resultSet.getInt("id"));
                objBan.setSoBan(resultSet.getString("soBan"));
                objBan.setTrangThai(resultSet.getInt("trangThai"));
                list.add(objBan);
            }
            Log.e("zzzzzzzzzz", "getAll: đọc oke " );
        }catch (Exception e){
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu " );
            e.printStackTrace();
        }
        return list;
    }
    public int delete(int id){
        int kq = -1;
        try {
            String sql = "DELETE FROM Ban WHERE id='" +id +"'";
            Statement statement = this.objConn.createStatement();
            kq = statement.executeUpdate(sql);
            Log.e("zzzzzzzzzz", "getAll: xóa oke" );
        }catch (Exception e){
            kq = -1;
            Log.e("zzzzzzzzzz", "getAll: xóa khong thành công" );
        }
        return kq;
    }
    public int insert(BanDTO objBan){
        int kq = -1;
        String soBan = objBan.getSoBan();
        try {
            String sql = "INSERT INTO Ban(soBan)\n" +
                    "VALUES ("+soBan+");";
            Statement statement = this.objConn.createStatement();
            kq = statement.executeUpdate(sql);
            Log.e("zzzzzzzzzz", "getAll: xóa oke" );
        }catch (Exception e){
            kq = -1;
            Log.e("zzzzzzzzzz", "getAll: xóa khong thành công" );
        }
        return kq;
    }
    public int update(BanDTO objBan){
        int kq = -1;
        String soBan = objBan.getSoBan();
        int id = objBan.getId();
        try {
            String sql = "UPDATE Ban\n" +
                    "SET soBan ='"+ soBan +"' \n" +
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