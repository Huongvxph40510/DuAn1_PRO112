package com.example.app_food_manager.DAO;

import android.util.Log;

import com.example.app_food_manager.DTO.MonAnDTO;
import com.example.app_food_manager.DataBase.DbSqlServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {
    Connection objConn;
    public  MonAnDAO(){
        DbSqlServer db = new DbSqlServer();
        objConn = db.openConnect();
    }
    public int delete(int id){
        int kq = -1;
        try {
            String sql = "DELETE FROM MonAn WHERE id='" +id +"'";
            Statement statement = this.objConn.createStatement();
            kq = statement.executeUpdate(sql);
        }catch (Exception e){
            kq = -1;
        }
        return kq;
    }
    public List<MonAnDTO> getAll(){
        List<MonAnDTO> list = new ArrayList<>();
        try{
            String sql = "Select * from MonAn";
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                MonAnDTO objMonAn = new MonAnDTO();
                objMonAn.setId(resultSet.getInt("id"));
                objMonAn.setTenMonAn(resultSet.getString("tenMonAn"));
                objMonAn.setGia(resultSet.getInt("gia"));
                objMonAn.setHinhAnh(resultSet.getBytes("hinhAnh"));
                list.add(objMonAn);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public int insert(MonAnDTO objMonAn){
        int kq = -1;
        String tenMonAn = objMonAn.getTenMonAn();
        int gia = objMonAn.getGia();
        byte[] hinhAnh = objMonAn.getHinhAnh();
        try {
            String sql = "INSERT INTO MonAn(tenMonAn,gia,hinhAnh)\n" +
                    "VALUES (N'"+tenMonAn+"'," +gia+",?);";
            try (PreparedStatement statement = objConn.prepareStatement(sql)) {
                statement.setBytes(1, hinhAnh);
                statement.executeUpdate();
                kq = 1;
            }

        }catch (Exception e){
            kq = -1;
        }
        return kq;
    }
    public int update(MonAnDTO objMonAn){
        int kq = -1;
        String tenMonAn = objMonAn.getTenMonAn();
        int gia = objMonAn.getGia();
        byte[] hinhAnh = objMonAn.getHinhAnh();
        int id = objMonAn.getId();
        try {
            String sql = "UPDATE MonAn\n" +
                    "SET tenMonAn = N'"+ tenMonAn +"',gia= '"+gia+"',hinhAnh= ?\n" +
                    "WHERE id = '"+ id + "';";
            try (PreparedStatement statement = objConn.prepareStatement(sql)) {
                statement.setBytes(1, hinhAnh);
                statement.executeUpdate();
                kq = 1;
            }
        }catch (Exception e){
            kq = -1;
        }
        return kq;
    }
    public MonAnDTO getId(int id){
        MonAnDTO objMonAn = new MonAnDTO();
        try{
            String sql = "Select * from MonAn where id =" + id;
            Statement statement = this.objConn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                objMonAn.setId(resultSet.getInt("id"));
                objMonAn.setTenMonAn(resultSet.getString("tenMonAn"));
                objMonAn.setGia(resultSet.getInt("gia"));
                objMonAn.setHinhAnh(resultSet.getBytes("hinhAnh"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return objMonAn;
    }
}
