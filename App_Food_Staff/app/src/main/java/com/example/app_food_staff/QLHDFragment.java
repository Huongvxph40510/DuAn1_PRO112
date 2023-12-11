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
import com.example.app_food_staff.DAO.NhanVienDAO;
import com.example.app_food_staff.DTO.BanDTO;
import com.example.app_food_staff.DTO.HoaDonChiTietDTO;
import com.example.app_food_staff.DTO.HoaDonDTO;
import com.example.app_food_staff.DTO.MonAnDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
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
    Button btnThem, btnHuy, btnThemMon,btnThanhToan,btnLuu;
    HoaDonAdapter hoaDonAdapter;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    BanDAO banDAO;
    ArrayList<BanDTO> listBan;
    BanSpinnerAdapter banSpinnerAdapter;
    int maBan;
    Dialog dialog, dialogMonAn, dialogSL;
    HoaDonChiTietDTO objHoaDonChiTiet;
    HoaDonDTO objHoaDon;
    Spinner spBan;
    int idMonAn;
    BanDTO objBan;
    TextView tvId, tvNhanVienTao, tvSoBan, tvNgay, tvNhanVienThanhToan, tvTrangThai, tvTongTien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qlhd, container, false);
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

        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialogThanhToan(getContext(), listHoaDon.get(i));
            }
        });
    }

    protected void openDialog(final Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_hoa_don);
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
        lvMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HoaDonChiTietDTO objHoaDonChiTiet = listHDCT.get(i);
                openDialogUpdateSL(context,objHoaDonChiTiet);
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
                if (objBan.getTrangThai() == 0) {
                    if (listHDCT.size() != 0) {
                        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                        int id = pref.getInt("ID", 0);
                        maBan = objBan.getId();
                        objHoaDon = new HoaDonDTO();
                        objHoaDon.setIdBan(maBan);
                        objHoaDon.setIdNhanVienTao(id);
                        objHoaDon.setNgayTao(new Date());
                        if (hoaDonDAO.insert(objHoaDon) > 0) {
                            int idNew = hoaDonDAO.idNew();
                            for (HoaDonChiTietDTO item : listHDCT) {
                                item.setIdHoaDon(idNew);
                                hoaDonChiTietDAO.insert(item);
                            }
                        }
                        banDAO.setTrangthai(objBan);
                        dialog.dismiss();
                        capNhatLv();
                    } else {
                        Toast.makeText(context, "Bạn chưa chọn món", Toast.LENGTH_SHORT).show();
                    }
                } else {
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
                for (HoaDonChiTietDTO item : listHDCT) {
                    if (item.getIdMonAn() == idMonAn){
                        Toast.makeText(context, "Trong danh sách đã có món ăn này", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
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
                if(edSoLuong.getText().toString().isEmpty()){
                    Toast.makeText(context, "Bạn chưa nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int gia = Integer.parseInt(edSoLuong.getText().toString());
                    if(gia < 0){
                        Toast.makeText(context, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (Exception e){
                    Toast.makeText(context, "Số lượng phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }
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
    protected void openDialogUpdateSL(final Context context,HoaDonChiTietDTO objHoaDonChiTiet) {
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
                if(edSoLuong.getText().toString().isEmpty()){
                    Toast.makeText(context, "Bạn chưa nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int gia = Integer.parseInt(edSoLuong.getText().toString());
                    if(gia < 0){
                        Toast.makeText(context, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (Exception e){
                    Toast.makeText(context, "Số lượng phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }
                objHoaDonChiTiet.setSoLuong(Integer.parseInt(edSoLuong.getText().toString()));
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

    protected void openDialogThanhToan(final Context context, HoaDonDTO objHoaDonTT) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_thanh_toan);
        tvId = dialog.findViewById(R.id.tvId);
        tvNhanVienTao = dialog.findViewById(R.id.tvNhanVienTao);
        tvSoBan = dialog.findViewById(R.id.tvSoBan);
        tvNgay = dialog.findViewById(R.id.tvNgay);
        tvNhanVienThanhToan = dialog.findViewById(R.id.tvNhanVienThanhToan);
        tvTrangThai = dialog.findViewById(R.id.tvTrangThai);
        tvTongTien = dialog.findViewById(R.id.tvTongTien);
        lvMonAn = dialog.findViewById(R.id.lvMonAn);
        btnThanhToan = dialog.findViewById(R.id.btnThanhToan);
        btnThemMon = dialog.findViewById(R.id.btnThemMon);
        btnLuu = dialog.findViewById(R.id.btnLuu);
        banDAO = new BanDAO();
        hoaDonChiTietDAO = new HoaDonChiTietDAO();
        hoaDonDAO = new HoaDonDAO();
        listHDCT = (ArrayList<HoaDonChiTietDTO>) hoaDonChiTietDAO.getTheoIdHoaDon(objHoaDonTT.getId());
        hdctAdapter = new HDCTAdapter(getActivity(), listHDCT);
        lvMonAn.setAdapter(hdctAdapter);
        NhanVienDAO nhanVienDAO = new NhanVienDAO();
        BanDAO banDAO = new BanDAO();
        HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO();

        String nhanVienTao = nhanVienDAO.getID(objHoaDonTT.getIdNhanVienTao()).getTenNhanVien();
        String nhanVienThanhToan = nhanVienDAO.getID(objHoaDonTT.getIdNhanVienThanToan()).getTenNhanVien();
        String soBan = banDAO.getID(objHoaDonTT.getIdBan()).getSoBan();
        int tongTien = hoaDonChiTietDAO.tongTien(objHoaDonTT.getId());

        tvId.setText("Mã hóa đơn: " + objHoaDonTT.getId());
        tvNhanVienTao.setText("Nhân viên tạo: " + nhanVienTao);
        tvSoBan.setText("Số bàn: " + soBan);
        tvNgay.setText("Ngày Tạo: " + objHoaDonTT.getNgayTao().toString());
        if (objHoaDonTT.getTrangThai() == 0) {
            tvNhanVienThanhToan.setText("Nhân viên thanh toán: Chưa thanh toán");
            tvTrangThai.setText("Trạng thái: Chưa thanh toán");
        } else {
            tvNhanVienThanhToan.setText("Nhân viên thanh toán: " + nhanVienThanhToan);
            tvTrangThai.setText("Trạng thái: Đã thanh toán");
        }
        lvMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HoaDonChiTietDTO objHoaDonChiTiet = listHDCT.get(i);
                openDialogUpdateSL(context,objHoaDonChiTiet);
            }
        });
        tvTongTien.setText("Tổng tiền: " + tongTien);
        btnThemMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogListMonAn(context);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (HoaDonChiTietDTO item : listHDCT) {
                    if(item.getIdHoaDon() == 0){
                        int idhoadon = objHoaDonTT.getId();
                        item.setIdHoaDon(idhoadon);
                        hoaDonChiTietDAO.insert(item);
                    }else{
                        hoaDonChiTietDAO.update(item);
                    }
                }
                dialog.dismiss();
                capNhatLv();
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                int id = pref.getInt("ID", 0);
                banDAO.setTrangthai(banDAO.getID(objHoaDonTT.getIdBan()));
                hoaDonDAO.thanhToan(objHoaDonTT.getId(), id);
                dialog.dismiss();
                capNhatLv();
            }
        });
        dialog.show();
    }
}
