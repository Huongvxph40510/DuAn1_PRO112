package com.example.app_food_staff;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_food_staff.Adapter.BanAdapter;
import com.example.app_food_staff.DAO.BanDAO;
import com.example.app_food_staff.DTO.BanDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BanFragment extends Fragment {
    ListView lvBan;
    ArrayList<BanDTO> list;
    static BanDAO dao;
    BanAdapter adapter;
    BanDTO item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ban,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvBan = view.findViewById(R.id.lvBan);

        dao = new BanDAO();
        list = new ArrayList<>();
        capNhatLv();
    }

    void capNhatLv() {
        list = (ArrayList<BanDTO>) dao.getAll();
        adapter = new BanAdapter(getActivity(), this, list);
        lvBan.setAdapter(adapter);
    }
}