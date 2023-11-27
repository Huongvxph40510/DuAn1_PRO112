package com.example.app_food_staff;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_food_staff.Adapter.BanSpinnerAdapter;
import com.example.app_food_staff.Adapter.HDCTAdapter;
import com.example.app_food_staff.Adapter.HoaDonAdapter;
import com.example.app_food_staff.Adapter.MonAnAdapter;
import com.example.app_food_staff.DAO.BanDAO;
import com.example.app_food_staff.DAO.HoaDonChiTietDAO;
import com.example.app_food_staff.DAO.HoaDonDAO;
import com.example.app_food_staff.DAO.MonAnDAO;
import com.example.app_food_staff.DTO.BanDTO;
import com.example.app_food_staff.DTO.HoaDonChiTietDTO;
import com.example.app_food_staff.DTO.HoaDonDTO;
import com.example.app_food_staff.DTO.MonAnDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class QLHDFragment extends Fragment {
    ListView lvHoaDon;
    ListView lvMonAn, lvListMonAn;
    ArrayList<MonAnDTO> listMonAn;
    ArrayList<HoaDonChiTietDTO> listHDCT;
    HoaDonDAO hoaDonDAO;
    ArrayList<HoaDonDTO> listHoaDon;
    MonAnDAO monAnDAO;
    MonAnAdapter monAnAdapter;
    FloatingActionButton fabAdd;
    HDCTAdapter hdctAdapter;
    Button btnThem,btnHuy,btnThemMon;
    HoaDonAdapter hoaDonAdapter;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    BanDAO banDAO;
    ArrayList<BanDTO> listBan;
    BanSpinnerAdapter banSpinnerAdapter;
    int maBan;
    Dialog dialog,dialogMonAn,dialogSL;
    HoaDonChiTietDTO objHoaDonChiTiet;
    HoaDonDTO objHoaDon;
    Spinner spBan;
    int idMonAn;
    BanDTO objBan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qlhd,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabAdd = view.findViewById(R.id.fabAdd);
        lvHoaDon = view.findViewById(R.id.lvHoaDon);
        hoaDonDAO = new HoaDonDAO();
        listHoaDon = new ArrayList<>();
        capNhatLv();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getContext());
            }
        });

    }

    protected void openDialog(final Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_hoa_don );
        spBan = dialog.findViewById(R.id.spBan);
        lvMonAn = dialog.findViewById(R.id.lvMonAn);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        btnThemMon = dialog.findViewById(R.id.btnThemMon);
        btnThem = dialog.findViewById(R.id.btnThem);
        hoaDonChiTietDAO = new HoaDonChiTietDAO();

        listHDCT = new ArrayList<>();
        banDAO = new BanDAO();
        listBan = (ArrayList<BanDTO>) banDAO.getAll();
        banSpinnerAdapter = new BanSpinnerAdapter(context, listBan);
        spBan.setAdapter(banSpinnerAdapter);

        spBan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                objBan = listBan.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnThemMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogListMonAn(context);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(objBan.getTrangThai() == 0){
                    if (listHDCT != null){
                        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                        int id = pref.getInt("ID", 0);
                        maBan = objBan.getId();
                        objHoaDon = new HoaDonDTO();
                        objHoaDon.setIdBan(maBan);
                        objHoaDon.setIdNhanVienTao(id);
                        objHoaDon.setNgayTao(new Date());
                        if (hoaDonDAO.insert(objHoaDon) > 0){
                            int idNew = hoaDonDAO.idNew();
                            for(HoaDonChiTietDTO item : listHDCT){
                                item.setIdHoaDon(idNew);
                                hoaDonChiTietDAO.insert(item);
                            }
                        }
                        banDAO.setTrangthai(objBan);
                        dialog.dismiss();
                        capNhatLv();
                    }else{
                        Toast.makeText(context, "Bạn chưa chọn món", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "Bàn đã có khách", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }
    protected void openDialogListMonAn(final Context context) {
        dialogMonAn = new Dialog(context);
        dialogMonAn.setContentView(R.layout.dialog_list_mon_an);
        lvListMonAn = dialogMonAn.findViewById(R.id.lvListMonAn);
        monAnDAO = new MonAnDAO();
        capNhatLvListMonAn();

        lvListMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idMonAn = listMonAn.get(i).getId();
                openDialogSL(context);
            }
        });
        dialogMonAn.show();
    }
    protected void openDialogSL(final Context context) {
        dialogSL = new Dialog(context);
        dialogSL.setContentView(R.layout.dialog_so_luong);
        EditText edSoLuong = dialogSL.findViewById(R.id.edSoLuong);
        Button btnThemMonAn = dialogSL.findViewById(R.id.btnThem);
        Button btnHuyMonAn = dialogSL.findViewById(R.id.btnHuy);
        btnHuyMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSL.dismiss();
                dialogMonAn.dismiss();
            }
        });
        btnThemMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoaDonChiTietDTO objHoaDonChiTiet = new HoaDonChiTietDTO();
                objHoaDonChiTiet.setIdMonAn(idMonAn);
                objHoaDonChiTiet.setSoLuong(Integer.parseInt(edSoLuong.getText().toString()));
                listHDCT.add(objHoaDonChiTiet);
                dialogMonAn.dismiss();
                dialogSL.dismiss();
                capNhatLvMonAn();
            }
        });
        dialogSL.show();
    }
    void capNhatLv() {
        listHoaDon = (ArrayList<HoaDonDTO>) hoaDonDAO.getAll();
        hoaDonAdapter = new HoaDonAdapter(getActivity(), this, listHoaDon);
        lvHoaDon.setAdapter(hoaDonAdapter);
    }
    void capNhatLvMonAn() {
        hdctAdapter = new HDCTAdapter(getActivity(), listHDCT);
        lvMonAn.setAdapter(hdctAdapter);
    }

    void capNhatLvListMonAn() {
        listMonAn = (ArrayList<MonAnDTO>) monAnDAO.getAll();
        monAnAdapter = new MonAnAdapter(getActivity(), listMonAn);
        lvListMonAn.setAdapter(monAnAdapter);
    }
}