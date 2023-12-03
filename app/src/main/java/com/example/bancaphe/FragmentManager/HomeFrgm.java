package com.example.bancaphe.FragmentManager;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bancaphe.Adapter_Package.AdapterHome;
import com.example.bancaphe.DAOModel.DAOLuuHD;
import com.example.bancaphe.DAOModel.DAOSanPham;
import com.example.bancaphe.DAOModel.DAOUser;
import com.example.bancaphe.Model.SanPham;
import com.example.bancaphe.Model.TheLoai;
import com.example.bancaphe.Model.User;
import com.example.bancaphe.R;

import java.util.ArrayList;

public class HomeFrgm extends Fragment {

    RecyclerView recycler_SPBanChay;
    private AdapterHome adapterHome;
    private ArrayList<SanPham> listSpTopOut = new ArrayList<>();
    DAOLuuHD daoLuuHD;
    DAOSanPham daoSanPham;
    LinearLayout layoutParent;
    DAOUser daoUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_frgm, container, false);
        ImageView imgNotifi = view.findViewById(R.id.imgNotifi);
        layoutParent = view.findViewById(R.id.layoutParent);
        recycler_SPBanChay = view.findViewById(R.id.recycler_SPBanChay);
        TextView txtHello = view.findViewById(R.id.txtHello);
        daoLuuHD = new DAOLuuHD(getContext());
        daoSanPham = new DAOSanPham(getContext());
        daoUser = new DAOUser(getContext());

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", getActivity().MODE_PRIVATE);
        int maUserNow = pref.getInt("MA", 0);
        User user = daoUser.getUser(maUserNow);
        String fullName = user.getFullName();

        txtHello.setText("Xin chào, " + fullName + "!");

        ArrayList<SanPham> listSanPham = daoSanPham.getAllProduct(0);
        ArrayList<Integer> listMaSPTop = daoLuuHD.getTopSp();
        for (int i = 0; i < listMaSPTop.size(); i++) {
            for (int j = 0; j < listSanPham.size(); j++) {
                if (listMaSPTop.get(i) == listSanPham.get(j).getId()){
                    listSpTopOut.add(listSanPham.get(j));
                }
            }
        }


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        adapterHome = new AdapterHome(listSpTopOut ,getActivity());
        recycler_SPBanChay.setLayoutManager(layoutManager);
        recycler_SPBanChay.setAdapter(adapterHome);

//        Notifi
        imgNotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_notifi);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText btnDongNotifi = dialog.findViewById(R.id.btnDongNotifi);
                btnDongNotifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        return view;
    }

}