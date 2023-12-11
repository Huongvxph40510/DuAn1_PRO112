package com.example.app_food_manager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_food_manager.Adapter.HDCTAdapter;
import com.example.app_food_manager.Adapter.HoaDonAdapter;
import com.example.app_food_manager.DAO.BanDAO;
import com.example.app_food_manager.DAO.HoaDonChiTietDAO;
import com.example.app_food_manager.DAO.HoaDonDAO;
import com.example.app_food_manager.DAO.NhanVienDAO;
import com.example.app_food_manager.DTO.HoaDonChiTietDTO;
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
    Dialog dialog;
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
        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialogThanhToan(getContext(), list.get(i));
            }
        });
        capNhatLv();
    }
    void capNhatLv() {
        Date d = new Date();
        String ngay = sdf.format(d);
        list = (ArrayList<HoaDonDTO>) dao.getByDate(ngay);
        adapter = new HoaDonAdapter(getActivity(), this, list);
        lvHoaDon.setAdapter(adapter);
    }
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
