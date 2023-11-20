package com.example.app_food_manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_food_manager.DTO.NhanVienDTO;
import com.example.app_food_manager.NhanVienFragment;
import com.example.app_food_manager.R;

import java.util.ArrayList;

public class NhanVienAdapter extends ArrayAdapter<NhanVienDTO> {
    private Context context;
    NhanVienFragment fragment;
    private ArrayList<NhanVienDTO> list;
    TextView tvId, tvTen, tvTaiKhoan,tvMatKhau,tvNamSinh,tvCCCD;
    ImageButton btnEdit,btnDel;
    public NhanVienAdapter(@NonNull Context context, NhanVienFragment fragment, ArrayList<NhanVienDTO> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_nhan_vien,null);
        }
        final NhanVienDTO item = list.get(position);

        if (item != null){
            tvId = v.findViewById(R.id.tvId);
            tvTen = v.findViewById(R.id.tvTen);
            tvTaiKhoan = v.findViewById(R.id.tvTaiKhoan);
            tvMatKhau = v.findViewById(R.id.tvMatKhau);
            tvNamSinh = v.findViewById(R.id.tvNamSinh);
            tvCCCD  = v.findViewById(R.id.tvCCCD);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnDel  = v.findViewById(R.id.btnDel);

            tvId.setText("Mã nhân viên: "+ item.getId());
            tvTen.setText("Tên nhân viên: "+ item.getTenNhanVien());
            tvTaiKhoan.setText("Tài Khoản: "+ item.getTaiKhoan());
            tvMatKhau.setText("Mật khẩu: "+ item.getMatKhau());
            tvNamSinh.setText("Năm sinh: "+ item.getNamSinh());
            tvCCCD.setText("CCCD: "+ item.getCCCD());

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.xoa(list.get(position).getId());
                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.openDialogUpdate(context,list.get(position));
                }
            });
        }
        return v;
    }
}