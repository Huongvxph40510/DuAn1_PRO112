package com.example.app_food_manager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_food_manager.Adapter.HDCTAdapter;
import com.example.app_food_manager.Adapter.HoaDonAdapter;
import com.example.app_food_manager.Adapter.TKHDAdapter;
import com.example.app_food_manager.DAO.BanDAO;
import com.example.app_food_manager.DAO.HoaDonChiTietDAO;
import com.example.app_food_manager.DAO.HoaDonDAO;
import com.example.app_food_manager.DAO.NhanVienDAO;
import com.example.app_food_manager.DTO.HoaDonChiTietDTO;
import com.example.app_food_manager.DTO.HoaDonDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TKHDFragment extends Fragment {
    ListView lvHoaDon;
    ArrayList<HoaDonDTO> list;
    static HoaDonDAO dao;
    TKHDAdapter adapter;
    ImageView btnNgay;
    EditText edNgay;
    TextView tvHoaDon;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int mYear,mMonth,mDay;
    Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tkhd,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edNgay = view.findViewById(R.id.edNgay);
        tvHoaDon= view.findViewById(R.id.tvHoaDon);
        btnNgay = view.findViewById(R.id.btnNgay);
        lvHoaDon = view.findViewById(R.id.lvHoaDon);

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
                tvHoaDon.setText("Tổng số hóa đơn: " + dao.getSoHoaDon(edNgay.getText().toString()));
                capNhatLv();
            }
        });
        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialogThanhToan(getContext(), list.get(i));
            }
        });
    }
    void capNhatLv() {
        list = (ArrayList<HoaDonDTO>) dao.getByDate(edNgay.getText().toString());
        adapter = new TKHDAdapter(getActivity(), this, list);
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
    protected void openDialogThanhToan(final Context context, HoaDonDTO objHoaDonTT) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_hoa_don);
        TextView tvId = dialog.findViewById(R.id.tvId);
        TextView tvNhanVienTao = dialog.findViewById(R.id.tvNhanVienTao);
        TextView tvSoBan = dialog.findViewById(R.id.tvSoBan);
        TextView tvNgay = dialog.findViewById(R.id.tvNgay);
        TextView tvNhanVienThanhToan = dialog.findViewById(R.id.tvNhanVienThanhToan);
        TextView tvTrangThai = dialog.findViewById(R.id.tvTrangThai);
        TextView tvTongTien = dialog.findViewById(R.id.tvTongTien);
        ListView lvMonAn = dialog.findViewById(R.id.lvMonAn);
        BanDAO banDAO = new BanDAO();
        HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO();
        ArrayList<HoaDonChiTietDTO> listHDCT = (ArrayList<HoaDonChiTietDTO>) hoaDonChiTietDAO.getTheoIdHoaDon(objHoaDonTT.getId());
        HDCTAdapter hdctAdapter = new HDCTAdapter(getActivity(), listHDCT);
        lvMonAn.setAdapter(hdctAdapter);
        NhanVienDAO nhanVienDAO = new NhanVienDAO();

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
        tvTongTien.setText("Tổng tiền: " + tongTien);
        dialog.show();
    }
}
