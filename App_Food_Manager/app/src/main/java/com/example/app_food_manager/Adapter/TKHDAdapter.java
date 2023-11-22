package com.example.app_food_manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_food_manager.DAO.BanDAO;
import com.example.app_food_manager.DAO.HoaDonChiTietDAO;
import com.example.app_food_manager.DAO.NhanVienDAO;
import com.example.app_food_manager.DTO.HoaDonDTO;
import com.example.app_food_manager.R;
import com.example.app_food_manager.TKHDFragment;

import java.util.ArrayList;

public class TKHDAdapter extends ArrayAdapter<HoaDonDTO> {
    private Context context;
    TKHDFragment fragment;
    private ArrayList<HoaDonDTO> list;
    TextView tvId, tvNhanVienTao, tvSoBan,tvNgay,tvNhanVienThanhToan,tvTrangThai,tvTongTien;

    public TKHDAdapter(@NonNull Context context, TKHDFragment fragment, ArrayList<HoaDonDTO>  list) {
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
            v = inflater.inflate(R.layout.item_hoa_don,null);
        }
        final HoaDonDTO item = list.get(position);
        if (item != null) {
            tvId = v.findViewById(R.id.tvId);
            tvNhanVienTao = v.findViewById(R.id.tvNhanVienTao);
            tvSoBan = v.findViewById(R.id.tvSoBan);
            tvNgay = v.findViewById(R.id.tvNgay);
            tvNhanVienThanhToan = v.findViewById(R.id.tvNhanVienThanhToan);
            tvTrangThai = v.findViewById(R.id.tvTrangThai);
            tvTongTien = v.findViewById(R.id.tvTongTien);

            NhanVienDAO nhanVienDAO= new NhanVienDAO();
            BanDAO banDAO = new BanDAO();
            HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO();

            String nhanVienTao = nhanVienDAO.getID(item.getIdNhanVienTao()).getTenNhanVien();
            String nhanVienThanhToan = nhanVienDAO.getID(item.getIdNhanVienThanToan()).getTenNhanVien();
            String soBan = banDAO.getID(item.getIdBan()).getSoBan();
            int tongTien = hoaDonChiTietDAO.tongTien(item.getId());

            tvId.setText("Mã hóa đơn: " + item.getId());
            tvNhanVienTao.setText("Nhân viên tạo: "+ nhanVienTao);
            tvSoBan.setText("Số bàn: "+ soBan);
            tvNgay.setText("Ngày Tạo: "+ item.getNgayTao().toString() );
            if(item.getTrangThai() == 0 ){
                tvNhanVienThanhToan.setText("Nhân viên thanh toán: Chưa thanh toán");
                tvTrangThai.setText("Trạng thái: Chưa thanh toán");
            }else{tvNhanVienThanhToan.setText("Nhân viên thanh toán: "+ nhanVienThanhToan);
                tvTrangThai.setText("Trạng thái: Đã thanh toán");
            }
            tvTongTien.setText("Tổng tiền: "+ tongTien);
        }

        return v;
    }
}