package com.example.app_food_manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_food_manager.Adapter.BanAdapter;
import com.example.app_food_manager.DAO.BanDAO;
import com.example.app_food_manager.DTO.BanDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BanFragment extends Fragment {

    ListView lvBan;
    ArrayList<BanDTO> list;
    static BanDAO dao;
    BanAdapter adapter;
    BanDTO item;
    FloatingActionButton fabAdd;
    Dialog dialog;
    EditText edSoBan;
    TextView tvId;
    Button btnThem,btnHuy,btnLuu;


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
        fabAdd = view.findViewById(R.id.fabAdd);

        dao = new BanDAO();
        list = new ArrayList<>();
        capNhatLv();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity());
            }
        });

        lvBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void capNhatLv() {
        list = (ArrayList<BanDTO>) dao.getAll();
        adapter = new BanAdapter(getActivity(), this, list);
        lvBan.setAdapter(adapter);
    }
    public void xoa(int Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int kq = dao.delete(Id);
                if(kq > -1){
                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Xóa không thành công", Toast.LENGTH_SHORT).show();
                }
                capNhatLv();
                dialog.cancel();
                Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    protected void openDialog(final Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_ban);
        edSoBan = dialog.findViewById(R.id.edSoBan);
        btnThem = dialog.findViewById(R.id.btnThem);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate() > 0) {
                    item = new BanDTO();
                    item.setSoBan(edSoBan.getText().toString());
                    if (dao.insert(item) > -1) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
    public void openDialogUpdate(final Context context, BanDTO objBan) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_ban_update);
        edSoBan = dialog.findViewById(R.id.edSoBan);
        btnLuu = dialog.findViewById(R.id.btnLuu);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        tvId = dialog.findViewById(R.id.tvId);

        edSoBan.setText(objBan.getSoBan());
        tvId.setText("Id: " + objBan.getId());
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate() > 0){
                    objBan.setSoBan(edSoBan.getText().toString());
                    if(dao.update(objBan) > -1){
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    public int validate() {
        int check = 1;
        if (edSoBan.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập số bàn", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;

    }
}