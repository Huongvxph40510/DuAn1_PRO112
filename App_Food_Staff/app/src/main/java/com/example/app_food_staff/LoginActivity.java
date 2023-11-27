package com.example.app_food_staff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_food_staff.DAO.NhanVienDAO;
import com.example.app_food_staff.DTO.NhanVienDTO;

public class LoginActivity extends AppCompatActivity {
    String userName, passWord;
    EditText edUsername, edPassWord;
    NhanVienDAO dao;
    NhanVienDTO nhanVienDTO;
    Button btnLogin;
    CheckBox chkRememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsername = findViewById(R.id.edUserName);
        edPassWord = findViewById(R.id.edPassWord);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        btnLogin = findViewById(R.id.btnLogin);
        dao = new NhanVienDAO();

        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        int id = pref.getInt("ID", 0);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        Boolean rem = pref.getBoolean("REMEMBER", false);

        edUsername.setText(user);
        edPassWord.setText(pass);
        chkRememberPass.setChecked(rem);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = edUsername.getText().toString();
                passWord = edPassWord.getText().toString();
                if (userName.trim().isEmpty() || passWord.trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    nhanVienDTO = dao.checkLogin(userName,passWord);
                    if (nhanVienDTO != null) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        rememberUser(nhanVienDTO.getId(),userName, passWord, chkRememberPass.isChecked());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tài khoản mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void rememberUser(int id, String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            // xoa trang thai luu truoc do
            edit.clear();
        } else {
            edit.putInt("ID",id);
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
        }
        // luu lai toan bo du lieu
        edit.commit();
    }
}