package com.example.app_food_staff.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_food_staff.DTO.MonAnDTO;
import com.example.app_food_staff.R;

import java.util.ArrayList;

public class MonAnAdapter extends ArrayAdapter<MonAnDTO> {
    private Context context;
    private ArrayList<MonAnDTO> list;
    TextView tvMonAn, tvGia;
    ImageButton btnEdit,btnDel;
    ImageView imgMonAn;
    Bitmap img;
    byte[] hinhAnh;
    public MonAnAdapter(@NonNull Context context, ArrayList<MonAnDTO> list) {
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
            v = inflater.inflate(R.layout.item_mon_an,null);
        }
        final MonAnDTO item = list.get(position);
        if (item != null){
            tvMonAn = v.findViewById(R.id.tvMonAn);
            tvGia = v.findViewById(R.id.tvGia);
            imgMonAn = v.findViewById(R.id.imgMonAn);

            tvMonAn.setText("" + item.getTenMonAn());
            tvGia.setText("" + item.getGia() +"VNĐ");
            hinhAnh = item.getHinhAnh();
            img = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
            imgMonAn.setImageBitmap(img);
        }

        return v;
    }
}