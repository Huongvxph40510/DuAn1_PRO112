package com.example.app_food_staff.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_food_staff.BanFragment;
import com.example.app_food_staff.DTO.BanDTO;
import com.example.app_food_staff.R;
import com.example.app_food_staff.BanFragment;

import java.util.ArrayList;

public class BanAdapter extends ArrayAdapter<BanDTO> {
    private Context context;
    BanFragment fragment;
    private ArrayList<BanDTO> list;
    TextView tvBan, tvTrangThai;


    public BanAdapter(@NonNull Context context, BanFragment fragment, ArrayList<BanDTO> list) {
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
            v = inflater.inflate(R.layout.item_ban,null);
        }
        final BanDTO item = list.get(position);
        if (item != null){
            tvBan = v.findViewById(R.id.tvBan);
            tvTrangThai = v.findViewById(R.id.tvTrangThai);
            tvBan.setText("Số bàn: " + item.getSoBan());
            if(item.getTrangThai() == 0){
                tvTrangThai.setText("Trạng thái: Còn trống");
                tvTrangThai.setTextColor(Color.GREEN);
            }else{
                tvTrangThai.setText("Trạng thái: Có khách");
                tvTrangThai.setTextColor(Color.RED);
            }
        }
        return v;
    }
}