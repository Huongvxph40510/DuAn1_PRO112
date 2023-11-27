package com.example.app_food_staff.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_food_staff.DAO.MonAnDAO;
import com.example.app_food_staff.DTO.HoaDonChiTietDTO;
import com.example.app_food_staff.DTO.MonAnDTO;
import com.example.app_food_staff.QLHDFragment;
import com.example.app_food_staff.R;

import java.util.ArrayList;

public class HDCTAdapter extends ArrayAdapter<HoaDonChiTietDTO> {
    private Context context;
    private ArrayList<HoaDonChiTietDTO> list;
    TextView tvMonAn,tvSoLuong, tvGia;
    MonAnDTO objMonAn;
    MonAnDAO dao;
    public HDCTAdapter(@NonNull Context context,  ArrayList<HoaDonChiTietDTO> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_hoa_don_chi_tiet,null);
        }
        final HoaDonChiTietDTO item = list.get(position);
        if (item != null){
            tvMonAn = v.findViewById(R.id.tvMonAn);
            tvSoLuong = v.findViewById(R.id.tvSoLuong);
            tvGia = v.findViewById(R.id.tvGia);
            dao = new MonAnDAO();
            objMonAn = dao.getId(item.getIdMonAn());

            tvMonAn.setText(objMonAn.getTenMonAn());
            tvSoLuong.setText("Số lượng: " +item.getSoLuong());
            tvGia.setText(objMonAn.getGia()+ "");
        }
        return v;
    }

}