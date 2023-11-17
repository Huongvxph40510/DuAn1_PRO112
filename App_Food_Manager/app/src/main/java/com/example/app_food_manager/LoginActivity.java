package com.example.app_food_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    String userNameAdmin = "admin";
    String passWordAdmin = "admin";
    String userName, passWord;
    EditText edUsername, edPassWord;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.edUserName);
        edPassWord = findViewById(R.id.edPassWord);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = edUsername.getText().toString();
                passWord = edPassWord.getText().toString();
                if (userName.trim().isEmpty() || passWord.trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (userName.equals(userNameAdmin)  && passWord.equals(passWordAdmin)) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tài khoản mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}