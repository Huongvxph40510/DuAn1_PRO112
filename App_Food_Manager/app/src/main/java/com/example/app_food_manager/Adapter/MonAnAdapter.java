package com.example.app_food_manager.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_food_manager.DTO.MonAnDTO;
import com.example.app_food_manager.MonAnFragment;
import com.example.app_food_manager.R;


import java.util.ArrayList;

public class MonAnAdapter extends ArrayAdapter<MonAnDTO> {
    private Context context;
    MonAnFragment fragment;
    private ArrayList<MonAnDTO> list;
    TextView tvMonAn, tvGia;
    ImageButton btnEdit,btnDel;
    ImageView imgMonAn;
    Bitmap img;
    byte[] hinhAnh;
    public MonAnAdapter(@NonNull Context context, MonAnFragment fragment, ArrayList<MonAnDTO> list) {
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
            v = inflater.inflate(R.layout.item_mon_an,null);
        }
        final MonAnDTO item = list.get(position);
        if (item != null){
            tvMonAn = v.findViewById(R.id.tvMonAn);
            tvGia = v.findViewById(R.id.tvGia);
            imgMonAn = v.findViewById(R.id.imgMonAn);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnDel  = v.findViewById(R.id.btnDel);

            tvMonAn.setText("" + item.getTenMonAn());
            tvGia.setText("Giá: " + item.getGia() +"VNĐ");
            hinhAnh = item.getHinhAnh();
            img = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
            imgMonAn.setImageBitmap(img);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.openDialogUpdate(context,list.get(position));
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.xoa(list.get(position).getId());
            }
        });
        return v;
    }
}