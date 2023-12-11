package com.example.app_food_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_food_manager.Adapter.HoaDonAdapter;
import com.example.app_food_manager.DAO.BanDAO;
import com.example.app_food_manager.DAO.HoaDonDAO;
import com.example.app_food_manager.DTO.HoaDonDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HoaDonFragment extends Fragment {
    ListView lvHoaDon;
    ArrayList<HoaDonDTO> list;
    static HoaDonDAO dao;
    HoaDonAdapter adapter;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hoa_don,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvHoaDon = view.findViewById(R.id.lvHoaDon);
        dao = new HoaDonDAO();
        list = new ArrayList<>();
        capNhatLv();
    }
    void capNhatLv() {
        Date d = new Date();
        String ngay = sdf.format(d);
        list = (ArrayList<HoaDonDTO>) dao.getByDate(ngay);
        adapter = new HoaDonAdapter(getActivity(), this, list);
        lvHoaDon.setAdapter(adapter);
    }
}
