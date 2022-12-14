package com.orderfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.orderfood.DAO.BanAnDAO;
import com.orderfood.DAO.GoiMonDAO;
import com.orderfood.DTO.GoiMonDTO;
import com.orderfood.Fragment.HienThiThucDonFragment;
import com.orderfood.R;
import com.orderfood.DTO.BanAnDTO;
import com.orderfood.ThanhToanActivity;
import com.orderfood.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HienThiBanAnAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private int layout;
    private List<BanAnDTO> banAnDTOList;
    private ViewHolderBanAn viewHolderBanAn;
    private BanAnDAO banAnDAO;
    private GoiMonDAO goiMonDAO;
    private FragmentManager fragmentManager;
    public HienThiBanAnAdapter(Context context, int layout, List<BanAnDTO> banAnDTOList){
        this.context=context;
        this.layout=layout;
        this.banAnDTOList=banAnDTOList;
        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();
    }
    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return banAnDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return banAnDTOList.get(i).getMaBan();
    }

    public class ViewHolderBanAn{
        ImageView imgBanAn,imgGoiMon,imgThanhToan,imgAnButton;
        TextView txtTenBanAn;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn = new ViewHolderBanAn();
            view = inflater.inflate(R.layout.custom_layout_hienthibanan,viewGroup,false);
            viewHolderBanAn.imgBanAn=view.findViewById(R.id.imgBanAn);
            viewHolderBanAn.imgGoiMon=view.findViewById(R.id.imgGoiMon);
            viewHolderBanAn.imgThanhToan=view.findViewById(R.id.imgThanhToan);
            viewHolderBanAn.imgAnButton=view.findViewById(R.id.imgAnButton);
            viewHolderBanAn.txtTenBanAn=view.findViewById(R.id.txtTenBanAn);
            view.setTag(viewHolderBanAn);
        }else{
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }
        if(banAnDTOList.get(i).isDuocChon()){
            HienThiBTN();
        }else {
            AnBTN(false);
        }
        BanAnDTO banAnDTO = banAnDTOList.get(i);
        String kttinhtrang = banAnDAO.LayTinhTrangBanTheoMa(banAnDTO.getMaBan());
        if(kttinhtrang.equals("true")){
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banantrue);
        }else{
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banan);
        }
        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());
        viewHolderBanAn.imgBanAn.setTag(i);
        viewHolderBanAn.imgBanAn.setOnClickListener(this);
        viewHolderBanAn.imgGoiMon.setOnClickListener(this);
        viewHolderBanAn.imgThanhToan.setOnClickListener(this);
        viewHolderBanAn.imgAnButton.setOnClickListener(this);
        return view;
    }
    private void HienThiBTN(){
        viewHolderBanAn.imgGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imgAnButton.setVisibility(View.VISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_hienthi_btn_banan);
        viewHolderBanAn.imgGoiMon.startAnimation(animation);
        viewHolderBanAn.imgThanhToan.startAnimation(animation);
        viewHolderBanAn.imgAnButton.startAnimation(animation);
    }
    private void AnBTN(boolean hieuung){
        if(hieuung){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_an_btn_banan);
            viewHolderBanAn.imgGoiMon.startAnimation(animation);
            viewHolderBanAn.imgThanhToan.startAnimation(animation);
            viewHolderBanAn.imgAnButton.startAnimation(animation);
        }
        viewHolderBanAn.imgGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imgAnButton.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        int vitri1 = (int) viewHolderBanAn.imgBanAn.getTag();
        int maban = banAnDTOList.get(vitri1).getMaBan();
        viewHolderBanAn = (ViewHolderBanAn) ((View) view.getParent()).getTag();
        switch (id){
            case R.id.imgBanAn:
                String tenban = viewHolderBanAn.txtTenBanAn.getText().toString();
                int vitri = (int) view.getTag();
                banAnDTOList.get(vitri).setDuocChon(true); //luu vi tri ban an duoc chon
                HienThiBTN();
                break;
            case R.id.imgGoiMon:
                Intent layiTrangChu = ((TrangChuActivity)context).getIntent();
                int manhanvien = layiTrangChu.getIntExtra("manhanvien",0);
                String tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(maban);
                if(tinhtrang.equals("false")){
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                    String ngaygoi = dateFormat.format(calendar.getTime());
                    GoiMonDTO goiMonDTO = new GoiMonDTO();
                    goiMonDTO.setMaBan(maban);
                    goiMonDTO.setMaNV(manhanvien);
                    goiMonDTO.setNgayGoi(ngaygoi);
                    goiMonDTO.setTinhTrang("false");
                    long kiemtra = goiMonDAO.ThemGoiMon(goiMonDTO);
                    banAnDAO.CapNhatTinhTrangBan(maban,"true");
                    if(kiemtra==0){
                        Toast.makeText(context,R.string.thatbai,Toast.LENGTH_SHORT).show();
                    }
                }
                FragmentTransaction transThucDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                Bundle bDLThucDon = new Bundle();
                bDLThucDon.putInt("maban",maban);
                hienThiThucDonFragment.setArguments(bDLThucDon);
                transThucDon.replace(R.id.frame,hienThiThucDonFragment).addToBackStack("hienthibanan");
                transThucDon.commit();
                break;
            case R.id.imgThanhToan:
                Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
                iThanhToan.putExtra("maban",maban);
                context.startActivity(iThanhToan);
                break;
            case R.id.imgAnButton:
                AnBTN(true);
                break;
        }
    }
}
