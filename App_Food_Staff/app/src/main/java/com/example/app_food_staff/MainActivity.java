package com.example.app_food_staff;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.app_food_staff.DAO.NhanVienDAO;
import com.example.app_food_staff.DTO.NhanVienDTO;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    View mHeaderView;
    TextView tvUser;
    NavigationView nv;
    NhanVienDTO item;
    NhanVienDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layouts);
        toolbar = findViewById(R.id.toolbars);
        nv = findViewById(R.id.nvViews);
        dao = new NhanVienDAO();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lí hóa đơn");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mHeaderView = nv.getHeaderView(0);
        tvUser = mHeaderView.findViewById(R.id.tvUser);
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        int id = pref.getInt("ID", 0);
        item = dao.getID(id);
        tvUser.setText("Welcome " + item.getTenNhanVien());

        QLHDFragment qlhdFragment = new QLHDFragment();
        BanFragment banFragment = new BanFragment();
        ThongTinFragment thongTinFragment = new ThongTinFragment();
        TKHDDTFragment tkdhdtFragment = new TKHDDTFragment();
        TKHDDTTFragment tkdhdttFragment = new TKHDDTTFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.flContents,qlhdFragment).commit();

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id == R.id.menu_hoa_don){
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContents,qlhdFragment).commit();
                    getSupportActionBar().setTitle("Quản lí hóa đơn");
                } else if (id == R.id.menu_ban){
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContents,banFragment).commit();
                    getSupportActionBar().setTitle("Quản lí bàn");}else if (id == R.id.menu_tai_khoan){
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContents,thongTinFragment).commit();
                    getSupportActionBar().setTitle("Quản lí tài khoản");
                }else if (id == R.id.menu_don_hang_tao){
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContents,tkdhdtFragment).commit();
                    getSupportActionBar().setTitle("Thống kê hóa đơn đã tạo");
                }else if (id == R.id.menu_don_hang_thanh_toan) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContents, tkdhdttFragment).commit();
                    getSupportActionBar().setTitle("Thống kê hóa đơn đã thanh toán");
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