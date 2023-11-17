package com.example.app_food_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.app_food_manager.DAO.BanDAO;
import com.example.app_food_manager.DTO.BanDTO;
import com.example.app_food_manager.DataBase.DbSqlServer;
import com.google.android.material.navigation.NavigationView;


import android.os.Bundle;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    View mHeaderView;
    TextView tvUser;
    NavigationView nv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BanDAO banDAO= new BanDAO();
        List<BanDTO> list = new ArrayList<>();
        list = banDAO.getAll();

        for(int i = 0; i<list.size(); i++){
            BanDTO objBan = list.get(i);
            Log.d("zzzzzzzzzzz", "id :"+objBan.getId()+"số bàn :"+objBan.getSoBan()+"trạng thái :"+objBan.getTrangThai() );
        }

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        nv = findViewById(R.id.nvView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lí món ăn");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        MonAnFragment fragmentMonAn = new MonAnFragment();
        BanFragment fragmentBan = new BanFragment();
        NhanVienFragment fragmentNhanVien = new NhanVienFragment();
        HoaDonFragment fragmentHoaDon = new HoaDonFragment();
        TKDTFragment fragmentTKDT = new TKDTFragment();
        TKHDFragment fragmentTKHD = new TKHDFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.flContent,fragmentMonAn).commit();


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id == R.id.menu_mon_an){
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent,fragmentMonAn).commit();
                    getSupportActionBar().setTitle("Quản lí món ăn");
                } else if (id == R.id.menu_ban){
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent,fragmentBan).commit();
                    getSupportActionBar().setTitle("Quản lí bàn");
                }else if (id == R.id.menu_hoa_don){
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent,fragmentHoaDon).commit();
                    getSupportActionBar().setTitle("Quản lí hóa đơn");
                }else if (id == R.id.menu_nhan_vien){
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent,fragmentNhanVien).commit();
                    getSupportActionBar().setTitle("Quản lí nhân viên");
                }else if (id == R.id.menu_tkdt){
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent,fragmentTKDT).commit();
                    getSupportActionBar().setTitle("Thông kê doanh thu");
                }else if (id == R.id.menu_tkhd){
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent,fragmentTKHD).commit();
                    getSupportActionBar().setTitle("Thống kê hóa đơn");
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Singout");
                    builder.setMessage("Bạn có muốn đăng xuất không??");
                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
                    builder.setNegativeButton("No",null);
                    builder.show();
                }
                drawer.close();
                return  true;
            }
        });
    }
}