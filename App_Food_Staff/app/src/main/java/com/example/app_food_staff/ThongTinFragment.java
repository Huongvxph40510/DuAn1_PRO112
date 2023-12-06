package com.example.app_food_staff;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_food_staff.DAO.NhanVienDAO;
import com.example.app_food_staff.DTO.NhanVienDTO;

public class ThongTinFragment extends Fragment {
    EditText edTen, edTaiKhoan, edMatKhau, edSoDienThoai, edCCCD;
    Button  btnLuu;
    NhanVienDTO item;
    NhanVienDAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thong_tin,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edTen = view.findViewById(R.id.edTen);
        edTaiKhoan = view.findViewById(R.id.edTaiKhoan);
        edMatKhau = view.findViewById(R.id.edMatKhau);
        edSoDienThoai = view.findViewById(R.id.edSoDienThoai);
        edCCCD = view.findViewById(R.id.edCCCD);
        btnLuu = view.findViewById(R.id.btnLuu);
        dao = new NhanVienDAO();

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        int id = pref.getInt("ID", 0);
        item = dao.getID(id);

        capnhat();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Sửa thông tin cá nhân");
                    builder.setMessage("Bạn có muốn sửa không?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            item.setTenNhanVien(edTen.getText().toString());
                            item.setTaiKhoan(edTaiKhoan.getText().toString());
                            item.setMatKhau(edMatKhau.getText().toString());
                            item.setSoDienThoai(edSoDienThoai.getText().toString());
                            item.setCCCD(edCCCD.getText().toString());
                            if (dao.update(item) > -1) {
                                Toast.makeText(getContext(), "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Sửa thông tin thất bại", Toast.LENGTH_SHORT).show();
                            }
                            capnhat();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            capnhat();
                        }
                    });
                    AlertDialog alert = builder.create();
                    builder.show();
                }
            }
        });
    }
    public int validate() {
        int check = 1;
        if (edTen.getText().length() == 0 || edTaiKhoan.getText().length() == 0 || edMatKhau.getText().length() == 0 || edSoDienThoai.getText().length() == 0 || edCCCD.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;

    }
    public void capnhat(){
        edTen.setText(item.getTenNhanVien());
        edTaiKhoan.setText(item.getTaiKhoan());
        edMatKhau.setText(item.getMatKhau());
        edSoDienThoai.setText(item.getSoDienThoai());
        edCCCD.setText(item.getCCCD());
    }
}
