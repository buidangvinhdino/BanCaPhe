package com.example.bancaphe.Adapter_Package;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bancaphe.DAOModel.DAOLuuHD;
import com.example.bancaphe.DAOModel.DAOUser;
import com.example.bancaphe.FragmentManager.ViewUserInforFrgm;
import com.example.bancaphe.Model.LuuHoaDon;
import com.example.bancaphe.Model.User;
import com.example.bancaphe.R;

import java.util.ArrayList;

public class AdapterTKNV extends RecyclerView.Adapter<AdapterTKNV.ViewHolder>{

    ArrayList<LuuHoaDon> list;
    Context context;
    DAOLuuHD daoLuuHD;
    DAOUser daoUser;

    public AdapterTKNV(Context context, ArrayList<LuuHoaDon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_thongkenhanvien, parent, false);
        daoLuuHD = new DAOLuuHD(view.getContext());
        daoUser = new DAOUser(view.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        Settext

        LuuHoaDon luuHoaDon = list.get(position);
        String index = "";
        if (position < 9){
            index = "0" + (position + 1);
        }
        else{
            index = String.valueOf(position + 1);
        }
        holder.txtTknvSTT.setText(index);
        holder.txtTknvTenNv.setText(luuHoaDon.getTenUser());

        if (String.valueOf(luuHoaDon.getTt()) != null){
            double doanhThu = luuHoaDon.getTt();
            String outTongTien = String.format("%,.0f", doanhThu);
            if (outTongTien.length() > 4){

                holder.txtTknvDoanhThu.setText(outTongTien + "VNĐ");
            }
            else {
                holder.txtTknvDoanhThu.setText(outTongTien + "VNĐ");
            }

        }
        else {
            holder.txtTknvDoanhThu.setText("0 VNĐ");
        }

        User user = daoUser.getUser(luuHoaDon.getMaUser());

//        Item Click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Load Fragment hiển thị thông tin nhân viên
                loadFragment(new ViewUserInforFrgm(user));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTknvSTT, txtTknvTenNv, txtTknvDoanhThu;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTknvSTT = itemView.findViewById(R.id.txtTknvSTT);
            txtTknvTenNv = itemView.findViewById(R.id.txtTknvTenNv);
            txtTknvDoanhThu = itemView.findViewById(R.id.txtTknvDoanhThu);

        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
