package com.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orderfood.DAO.NhanVienDAO;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnDangNhapDN,btnDangKyDN;
    private EditText edTenDangNhapDN,edMatKhauDN;
    private NhanVienDAO nhanVienDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);
        btnDangKyDN=findViewById(R.id.btnDangKyDN);
        btnDangNhapDN=findViewById(R.id.btnDangNhapDN);
        edTenDangNhapDN=findViewById(R.id.edTenDangNhapDN);
        edMatKhauDN=findViewById(R.id.edMatKhauDN);
//        btnDangKyDN.setVisibility(View.GONE);
        nhanVienDAO = new NhanVienDAO(this);
        btnDangNhapDN.setOnClickListener(this);
        btnDangKyDN.setOnClickListener(this);
//        HienThiButton();
    }
//    private void HienThiButton(){
//        boolean kiemtra = nhanVienDAO.KiemTraNhanVien();
//        if(kiemtra){
//            btnDangKyDN.setVisibility(View.GONE);
//            btnDangNhapDN.setVisibility(View.VISIBLE);
//        }else{
//            btnDangKyDN.setVisibility(View.VISIBLE);
//            btnDangNhapDN.setVisibility(View.GONE);
//        }
//    }
    private void btnDangNhap(){
        String stringTenDN = edTenDangNhapDN.getText().toString();
        String stringMatKhau = edMatKhauDN.getText().toString();
        int kiemtra = nhanVienDAO.KiemTraDangNhap(stringTenDN,stringMatKhau);
//        int maquyen = nhanVienDAO.LayQuyenNhanVien(kiemtra);
        if(kiemtra!=0){
//            SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putInt("maquyen",maquyen);
//            editor.commit();
            Intent iTrangchu = new Intent(DangNhapActivity.this,TrangChuActivity.class);
            iTrangchu.putExtra("tendn",edTenDangNhapDN.getText().toString());
            iTrangchu.putExtra("manhanvien",kiemtra);//neu co manhanvien thi dangkyact chuyen thanh suaact
            startActivity(iTrangchu);
            overridePendingTransition(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
            finish();
        }else{
            Toast.makeText(DangNhapActivity.this,R.string.thatbai,Toast.LENGTH_SHORT).show();
        }
    }
    private void btnDangKy(){
        Intent iDangKy = new Intent(DangNhapActivity.this,DangKyActivity.class);
//        iDangKy.putExtra("landautien",1);
        startActivity(iDangKy);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        HienThiButton();
//    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnDangNhapDN:
                btnDangNhap();
                break;
            case R.id.btnDangKyDN:
                btnDangKy();
                break;
        }
    }
}