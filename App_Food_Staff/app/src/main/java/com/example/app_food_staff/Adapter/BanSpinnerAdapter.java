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

import com.example.app_food_staff.DTO.BanDTO;
import com.example.app_food_staff.R;

import java.util.ArrayList;

public class BanSpinnerAdapter extends ArrayAdapter<BanDTO> {
    private Context context;
    private ArrayList<BanDTO> list;
    TextView tvSoBan, tvTrangThai;
    public BanSpinnerAdapter(@NonNull  Context context, ArrayList<BanDTO> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_ban_spinner, null);
        }
        final BanDTO item = list.get(position);
        if (item != null) {
            tvSoBan = v.findViewById(R.id.tvSoBan);
            tvSoBan.setText("Bàn số: " + item.getSoBan());
            tvTrangThai = v.findViewById(R.id.tvTrangThai);
            if(item.getTrangThai() == 0){
                tvTrangThai.setText("Còn trống");
                tvTrangThai.setTextColor(Color.GREEN);
            }else{
                tvTrangThai.setText("Có khách");
                tvTrangThai.setTextColor(Color.RED);
            }
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_ban_spinner, null);
        }
        final BanDTO item = list.get(position);
        if (item != null) {
            tvSoBan = v.findViewById(R.id.tvSoBan);
            tvSoBan.setText("Bàn số: " + item.getSoBan());
            tvTrangThai = v.findViewById(R.id.tvTrangThai);
            if(item.getTrangThai() == 0){
                tvTrangThai.setText("Còn trống");
                tvTrangThai.setTextColor(Color.GREEN);
            }else{
                tvTrangThai.setText("Có khách");
                tvTrangThai.setTextColor(Color.RED);
            }
        }
        return v;
    }
}