package com.example.app_food_manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_food_manager.Adapter.NhanVienAdapter;
import com.example.app_food_manager.DAO.BanDAO;
import com.example.app_food_manager.DAO.NhanVienDAO;
import com.example.app_food_manager.DTO.BanDTO;
import com.example.app_food_manager.DTO.NhanVienDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NhanVienFragment extends Fragment {
    ListView lvNhanVien;
    ArrayList<NhanVienDTO> list;
    static NhanVienDAO dao;
    NhanVienAdapter adapter;
    NhanVienDTO item;
    FloatingActionButton fabAdd;
    EditText edTen,edTaiKhoan,edMatKhau,edSoDienThoai,edCCCD;
    Button btnThem,btnHuy,btnLuu;
    TextView tvId;
    Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nhan_vien,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvNhanVien = view.findViewById(R.id.lvNhanVien);
        fabAdd = view.findViewById(R.id.fabAdd);

        dao = new NhanVienDAO();
        list = new ArrayList<>();
        capNhatLv();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getContext());
            }
        });

    }

    void capNhatLv() {
        list = (ArrayList<NhanVienDTO>) dao.getAll();
        adapter = new NhanVienAdapter(getActivity(), this, list);
        lvNhanVien.setAdapter(adapter);
    }
    public void xoa(int Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int kq = dao.delete(Id);
                if(kq > -1){
                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Xóa không thành công", Toast.LENGTH_SHORT).show();
                }
                capNhatLv();
                dialog.cancel();
                Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }
    protected void openDialog(final Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_nhan_vien);
        edTen = dialog.findViewById(R.id.edTen);
        edTaiKhoan = dialog.findViewById(R.id.edTaiKhoan);
        edMatKhau = dialog.findViewById(R.id.edMatKhau);
        edSoDienThoai = dialog.findViewById(R.id.edSoDienThoai);
        edCCCD = dialog.findViewById(R.id.edCCCD);
        btnThem = dialog.findViewById(R.id.btnThem);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate() > 0) {
                    item = new NhanVienDTO();
                    item.setTenNhanVien(edTen.getText().toString());
                    item.setTaiKhoan(edTaiKhoan.getText().toString());
                    item.setMatKhau(edMatKhau.getText().toString());
                    item.setSoDienThoai(edSoDienThoai.getText().toString());
                    item.setCCCD(edCCCD.getText().toString());
                    if (dao.insert(item) > -1) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
    public void openDialogUpdate(final Context context, NhanVienDTO objNhanVien) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_nhan_vien_update);
        edTen = dialog.findViewById(R.id.edTen);
        edTaiKhoan = dialog.findViewById(R.id.edTaiKhoan);
        edMatKhau = dialog.findViewById(R.id.edMatKhau);
        edSoDienThoai = dialog.findViewById(R.id.edSoDienThoai);
        edCCCD = dialog.findViewById(R.id.edCCCD);
        tvId = dialog.findViewById(R.id.tvId);
        btnLuu = dialog.findViewById(R.id.btnLuu);
        btnHuy = dialog.findViewById(R.id.btnHuy);

        tvId.setText("Mã nhân viên: " + objNhanVien.getId());
        edTen.setText(objNhanVien.getTenNhanVien());
        edTaiKhoan.setText( objNhanVien.getTaiKhoan());
        edMatKhau.setText( objNhanVien.getMatKhau());
        edSoDienThoai.setText(objNhanVien.getSoDienThoai());
        edCCCD.setText( objNhanVien.getCCCD());

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate() > 0) {
                    objNhanVien.setTenNhanVien(edTen.getText().toString());
                    objNhanVien.setTaiKhoan(edTaiKhoan.getText().toString());
                    objNhanVien.setMatKhau(edMatKhau.getText().toString());
                    objNhanVien.setSoDienThoai(edSoDienThoai.getText().toString());
                    objNhanVien.setCCCD(edCCCD.getText().toString());
                    if (dao.update(objNhanVien) > -1) {
                        Toast.makeText(context, "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Sửa thông tin thất bại", Toast.LENGTH_SHORT).show();
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
    public int validate() {
        int check = 1;
        if (edTen.getText().length() == 0 || edTaiKhoan.getText().length() == 0 ||edMatKhau.getText().length() == 0 ||edSoDienThoai.getText().length() == 0 ||edCCCD.getText().length() == 0 ) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        try {
            int gia = Integer.parseInt(edSoDienThoai.getText().toString());
        }catch (Exception e){
            Toast.makeText(getContext(), "Số điện thoại phải là số", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}
