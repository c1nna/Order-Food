package com.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.orderfood.Adapter.HienThiThanhToanAdapter;
import com.orderfood.DAO.BanAnDAO;
import com.orderfood.DAO.GoiMonDAO;
import com.orderfood.DTO.ThanhToanDTO;
import com.orderfood.Fragment.HienThiBanAnFragment;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {
    private GridView gridView;
    private Button btnThanhToan,btnThoat;
    private GoiMonDAO goiMonDAO;
    private List<ThanhToanDTO> thanhToanDTOList;
    private HienThiThanhToanAdapter adapter;
    private TextView txtTongTien;
    private long tongtien = 0;
    private BanAnDAO banAnDAO;
    private int maban = 0;
    private FragmentManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);
        gridView = findViewById(R.id.gvThanhToan);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnThoat = findViewById(R.id.btnThoatThanhToan);
        txtTongTien = findViewById(R.id.txtTongTien);
        btnThanhToan.setOnClickListener(this);
        btnThoat.setOnClickListener(this);
        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);
        manager = getSupportFragmentManager();
        maban = getIntent().getIntExtra("maban",0);
        if(maban!=0){
            HienThiThanhToan();
            for (int i = 0; i < thanhToanDTOList.size(); i++) {
                int soluong = thanhToanDTOList.get(i).getSoLuong();
                int giatien = thanhToanDTOList.get(i).getGiaTien();
                tongtien+=soluong*giatien;
            }
            txtTongTien.setText("Total: " + tongtien + " đồng" );

        }
    }
    private void HienThiThanhToan(){
        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban,"false");
        thanhToanDTOList = goiMonDAO.LayDSMonAnTheoMaGoiMon(magoimon);
        adapter = new HienThiThanhToanAdapter(this,R.layout.custom_layout_hienthithanhtoan,thanhToanDTOList);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnThanhToan:
                boolean kiemtrabanan = banAnDAO.CapNhatTinhTrangBan(maban,"false");
                boolean kiemtragoimon = goiMonDAO.CapNhatTrangThaiGoiMonTheoMaBan(maban,"true");
                if(kiemtrabanan && kiemtragoimon){
                    Toast.makeText(ThanhToanActivity.this,R.string.thanhcong,Toast.LENGTH_SHORT).show();
                    HienThiThanhToan();
                }else{
                    Toast.makeText(ThanhToanActivity.this,R.string.thatbai,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnThoatThanhToan:
                Intent intent = new Intent(ThanhToanActivity.this,TrangChuActivity.class);
                startActivity(intent);
//                FragmentTransaction transaction = manager.beginTransaction();
//                HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
//                transaction.replace(R.id.frame,hienThiBanAnFragment);
//                transaction.commit();
                break;
        }
    }
}
