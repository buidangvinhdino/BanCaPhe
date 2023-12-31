package com.example.bancaphe.FragmentManager;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bancaphe.Adapter_Package.AdapterSanPham;
import com.example.bancaphe.DAOModel.DAOSanPham;
import com.example.bancaphe.Model.SanPham;
import com.example.bancaphe.Model.TheLoai;
import com.example.bancaphe.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class SuaSPFrgm extends Fragment {

    EditText edUpdateAnhSP,edUpdateTenSP, edUpdateGiaBan, edUpdateMoTa, btnUpdate;
    AutoCompleteTextView edtLoaiSP;
    SanPham sanPham;
    ImageView imgUpdate;
    DAOSanPham daoSanPham;
    String strAnhSP,strTenSP, strMota, strLoaiSP;
    double strGiaban;
    ArrayList<SanPham> arrayList;
    AdapterSanPham adapter = null;
    int maLoai, index;
    boolean checkTL;

    public SuaSPFrgm(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sua_s_p_frgm, container, false);
        // ánh xạ
        ImageView btnBackSuaSP = view.findViewById(R.id.btnBackSuaSP);
        EditText btnSuaSPHuy = view.findViewById(R.id.btnSuaSPHuy);
        EditText btnSuaSPXN = view.findViewById(R.id.btnUpdateSp);
        edUpdateAnhSP =  view.findViewById(R.id.update_anhSP);
        edUpdateTenSP = view.findViewById(R.id.update_tenSP);
        edUpdateGiaBan = view.findViewById(R.id.update_giaBan);
        edUpdateMoTa = view.findViewById(R.id.update_moTa);
        edtLoaiSP = view.findViewById(R.id.edUpdateLSP);
        btnUpdate = view.findViewById(R.id.btnUpdateSp);
        daoSanPham = new DAOSanPham(getContext());
        arrayList = new ArrayList<>();
        adapter = new AdapterSanPham(getActivity(), arrayList);

//        Settext cho Edittext
        edUpdateAnhSP.setText(sanPham.getImage());
        edUpdateGiaBan.setText(sanPham.getPrice() + "");
        edUpdateTenSP.setText(sanPham.getTenSanPham());
        maLoai = sanPham.getMaLoai();
        String tenLoai = "";
        ArrayList<TheLoai> listTL = daoSanPham.getDSLSP();
        for (int i = 0; i < listTL.size(); i++) {
            if (listTL.get(i).getMaLoai() == maLoai){
                tenLoai = listTL.get(i).getTenLoai();
            }
        }
        edtLoaiSP.setText(tenLoai);
        edUpdateMoTa.setText(sanPham.getMota());

        // xử lý sự kiện thêm ảnh


        // set sự kiện
        btnBackSuaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ChiTietSPSuaFrgm(sanPham));
            }
        });

        btnSuaSPHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUpdateAnhSP.setText(sanPham.getImage());
                edUpdateGiaBan.setText(sanPham.getPrice() + "");
                edUpdateTenSP.setText(sanPham.getTenSanPham());
                maLoai = sanPham.getMaLoai();
                String tenLoai = "";
                ArrayList<TheLoai> listTL = daoSanPham.getDSLSP();
                for (int i = 0; i < listTL.size(); i++) {
                    if (listTL.get(i).getMaLoai() == maLoai){
                        tenLoai = listTL.get(i).getTenLoai();
                    }
                }
                edtLoaiSP.setText(tenLoai);
                edUpdateMoTa.setText(sanPham.getMota());

                Toast.makeText(getContext(), "Hủy!", Toast.LENGTH_SHORT).show();
            }
        });
//        Set Data cho spnLoaiSP - AnhNQ
        ArrayList<TheLoai> listTheLoai = daoSanPham.getDSLSP();
        ArrayList<String> listTenTL = new ArrayList<>();
        ArrayList<Integer> listMaTL = new ArrayList<>();
        int listTheLoaiSize = listTheLoai.size();
        if (listTheLoaiSize != 0) {
            for (int i = 0; i < listTheLoaiSize; i++) {
                TheLoai theLoaiModel = listTheLoai.get(i);
                listTenTL.add(theLoaiModel.getTenLoai());
                listMaTL.add(theLoaiModel.getMaLoai());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, listTenTL);

        edtLoaiSP.setThreshold(1);
        edtLoaiSP.setAdapter(adapter);

        btnSuaSPXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAnhSP = edUpdateAnhSP.getText().toString();

                strTenSP = edUpdateTenSP.getText().toString();
                strGiaban = Double.parseDouble(edUpdateGiaBan.getText().toString());
                strMota = edUpdateMoTa.getText().toString();
                strLoaiSP = edtLoaiSP.getText().toString();

                checkTL = false;
                index = 0;
                for (int i = 0; i < listTheLoaiSize; i++) {
                    String mTenLoai = listTenTL.get(i);
                    if (mTenLoai.equals(strLoaiSP)){
                        index = i;
                        checkTL = true;
                        break;
                    }
                }

                if (checkTL){
                    Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dialog_confirm);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    TextView dialog_confirm_content = dialog.findViewById(R.id.dialog_confirm_content);
                    EditText btnDialogHuy = dialog.findViewById(R.id.btnDialogHuy);
                    EditText btnUpdate = dialog.findViewById(R.id.btnDialogXN);

                    dialog_confirm_content.setText("Bạn chắc chắn muốn sửa thông tin sản phẩm đã chọn!");

                    btnDialogHuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Hủy!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                    //Update
                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkEdt()) {
                            maLoai = listMaTL.get(index);
                                daoSanPham.updateSanPham(strAnhSP, strTenSP, strGiaban, maLoai, strMota, sanPham.getId());
                                Toast.makeText(getActivity(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                                loadFragment(new ProductFrgm());
                                resetEdt();
                                dialog.dismiss();
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    dialog.show();
                }   else {
                    edtLoaiSP.setError("Loại sản phẩm không tồn tại!");
                    edtLoaiSP.setText("");
                }
            }
        });
        return view;
    }

    //Cấp quyền lấy ảnh

    Bitmap imgChose = null;


    //image to byte

    //replaceFragment
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //    Check Form
    private boolean checkEdt() {

        boolean checkAdd = true;
        if (strAnhSP.isEmpty()) {
            edUpdateAnhSP.setError("Vui lòng nhập!");
            edUpdateAnhSP.setHintTextColor(Color.RED);
            checkAdd = false;
        }
        if (strTenSP.isEmpty()) {
            edUpdateTenSP.setError("Vui lòng nhập!");
            edUpdateTenSP.setHintTextColor(Color.RED);
            checkAdd = false;
        }

        if (edUpdateGiaBan.getText().toString().isEmpty()) {
            edUpdateGiaBan.setError("Vui lòng nhập!");
            edUpdateGiaBan.setHintTextColor(Color.RED);
            checkAdd = false;
        }
        if (strLoaiSP.isEmpty()) {
            edtLoaiSP.setError("Vui lòng nhập!");
            edtLoaiSP.setHintTextColor(Color.RED);
            checkAdd = false;
        }

        if (strMota.isEmpty()) {
            edUpdateMoTa.setError("Vui lòng nhập!");
            edUpdateMoTa.setHintTextColor(Color.RED);
            checkAdd = false;
        }

        return checkAdd;
    }

    //    Reset Edittext
    private void resetEdt() {
        edUpdateAnhSP.setText("");
        edUpdateAnhSP.setHintTextColor(Color.BLACK);
        edUpdateTenSP.setText("");
        edUpdateTenSP.setHintTextColor(Color.BLACK);
        edUpdateGiaBan.setText("");
        edUpdateGiaBan.setHintTextColor(Color.BLACK);
        edUpdateMoTa.setText("");
        edUpdateMoTa.setHintTextColor(Color.BLACK);
    }
}