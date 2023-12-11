package com.example.app_food_manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_food_manager.Adapter.MonAnAdapter;
import com.example.app_food_manager.DAO.MonAnDAO;
import com.example.app_food_manager.DTO.MonAnDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MonAnFragment extends Fragment {
    ListView lvMonAn;
    ArrayList<MonAnDTO> list;
    static MonAnDAO dao;
    MonAnAdapter adapter;
    MonAnDTO item;
    FloatingActionButton fabAdd;
    EditText edMonAn, edGia;
    Button btnThem, btnHuy, btnLuu, btnThemAnh;
    TextView tvId;
    ImageView imgViewMonAn;
    Dialog dialog;
    Bitmap bitmap;
    byte[] hinhAnh;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mon_an, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvMonAn = view.findViewById(R.id.lvMonAn);
        fabAdd = view.findViewById(R.id.fabAdd);

        dao = new MonAnDAO();
        list = new ArrayList<>();
        capNhatLv();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getContext());
            }
        });

    }

    void capNhatLv() {
        list = (ArrayList<MonAnDTO>) dao.getAll();
        adapter = new MonAnAdapter(getActivity(), this, list);
        lvMonAn.setAdapter(adapter);
    }

    public void xoa(int Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int kq = dao.delete(Id);
                if (kq > -1) {
                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
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
        dialog.setContentView(R.layout.dialog_mon_an);
        edMonAn = dialog.findViewById(R.id.edMonAn);
        edGia = dialog.findViewById(R.id.edGia);
        btnThemAnh = dialog.findViewById(R.id.btnThemAnh);
        btnThem = dialog.findViewById(R.id.btnThem);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        imgViewMonAn = dialog.findViewById(R.id.imgViewMonAn);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }

        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edMonAn.getText().toString().trim().isEmpty() || edGia.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Bạn chưa nhập thông tin mớn ăn", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int gia = Integer.parseInt(edGia.getText().toString());
                    if(gia < 0){
                        Toast.makeText(context, "Giá phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (Exception e){
                    Toast.makeText(context, "Giá phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bitmap == null) {
                    Toast.makeText(context, "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                }
                MonAnDTO item = new MonAnDTO();
                item.setTenMonAn(edMonAn.getText().toString());
                String ma = edMonAn.getText().toString();
                item.setGia(Integer.parseInt(edGia.getText().toString()));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                item.setHinhAnh(byteArray);
                if (dao.insert(item) > -1) {
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                capNhatLv();
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    public void openDialogUpdate(final Context context, MonAnDTO objMonAn) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_mon_an_update);
        edMonAn = dialog.findViewById(R.id.edMonAn);
        edGia = dialog.findViewById(R.id.edGia);
        btnThemAnh = dialog.findViewById(R.id.btnThemAnh);
        tvId = dialog.findViewById(R.id.tvId);
        btnLuu = dialog.findViewById(R.id.btnLuu);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        imgViewMonAn = dialog.findViewById(R.id.imgViewMonAn);

        tvId.setText("Id: " + objMonAn.getId());
        edMonAn.setText(objMonAn.getTenMonAn());
        edGia.setText(objMonAn.getGia() + "");
        hinhAnh = objMonAn.getHinhAnh();
        bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
        imgViewMonAn.setImageBitmap(bitmap);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }

        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edMonAn.getText().toString().trim().isEmpty() || edGia.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Bạn chưa nhập thông tin mớn ăn", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int gia = Integer.parseInt(edGia.getText().toString());
                    if(gia < 0){
                        Toast.makeText(context, "Giá phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (Exception e){
                    Toast.makeText(context, "Giá phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bitmap == null) {
                    Toast.makeText(context, "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }
                objMonAn.setTenMonAn(edMonAn.getText().toString());
                objMonAn.setGia(Integer.parseInt(edGia.getText().toString()));
                if (bitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    objMonAn.setHinhAnh(byteArray);
                }
                if (dao.update(objMonAn) > -1) {
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                capNhatLv();
            }
        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && data != null) {
            Uri selectedImageUri = data.getData();

            try {
                // Chuyển đổi URI thành Bitmap
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                // Hiển thị ảnh đã chọn lên ImageView
                imgViewMonAn.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
