package com.example.app_food_staff;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_food_staff.Adapter.TKHDAdapter;
import com.example.app_food_staff.DAO.HoaDonDAO;
import com.example.app_food_staff.DTO.HoaDonDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TKHDDTTFragment extends Fragment {
    ListView lvHoaDon;
    ArrayList<HoaDonDTO> list;
    static HoaDonDAO dao;
    TKHDAdapter adapter;
    ImageView btnNgay;
    EditText edNgay;
    TextView tvHoaDon;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int mYear,mMonth,mDay;
    int id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tkhddt,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edNgay = view.findViewById(R.id.edNgay);
        tvHoaDon= view.findViewById(R.id.tvHoaDon);
        btnNgay = view.findViewById(R.id.btnNgay);
        lvHoaDon = view.findViewById(R.id.lvHoaDon);
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        id = pref.getInt("ID", 0);

        dao = new HoaDonDAO();

        edNgay.setEnabled(false);
        btnNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,mDateNgay,mYear,mMonth,mDay);
                d.show();

            }
        });
        edNgay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvHoaDon.setText("Tổng số hóa đơn: " + dao.getSoHoaDonThanhToan(edNgay.getText().toString(), id));
                capNhatLv();
            }
        });
    }
    void capNhatLv() {
        list = (ArrayList<HoaDonDTO>) dao.getTheoNgayThanhToan(edNgay.getText().toString(), id);
        adapter = new TKHDAdapter(getActivity(), list);
        lvHoaDon.setAdapter(adapter);
    }
    DatePickerDialog.OnDateSetListener mDateNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
            edNgay.setText(sdf.format(c.getTime()));
        }
    };
}