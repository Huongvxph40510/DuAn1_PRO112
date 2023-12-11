package com.example.app_food_staff.DAO;

import android.util.Log;

import com.example.app_food_staff.DTO.MonAnDTO;
import com.example.app_food_staff.DataBase.DbSqlServer;

import java.sql.Connection;
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
