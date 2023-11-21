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

import java.util.ArrayList;

public class HoaDonFragment extends Fragment {
    ListView lvHoaDon;
    ArrayList<HoaDonDTO> list;
    static HoaDonDAO dao;
    HoaDonAdapter adapter;
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
        list = (ArrayList<HoaDonDTO>) dao.getAll();
        adapter = new HoaDonAdapter(getActivity(), this, list);
        lvHoaDon.setAdapter(adapter);
    }
}
