package com.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.orderfood.DAO.GoiMonDAO;
import com.orderfood.DTO.ChiTietGoiMonDTO;

public class SoLuongActivity extends AppCompatActivity implements View.OnClickListener {
    private int maban, mamonan;
    private Button btnDongYThemSL;
    private EditText edThemSL;
    private GoiMonDAO goiMonDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themsoluong);
        btnDongYThemSL = findViewById(R.id.btnDongYThemSLMonAn);
        edThemSL = findViewById(R.id.edSLMonAn);
        btnDongYThemSL.setOnClickListener(this);
        goiMonDAO = new GoiMonDAO(this);
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        mamonan = intent.getIntExtra("mamonan",0);
    }

    @Override
    public void onClick(View view) {
        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban,"false");
        boolean kiemtra = goiMonDAO.KiemTraMonAnDaTonTai(magoimon,mamonan);
        if(kiemtra){
            int soluongcu = goiMonDAO.LaySLMonAnTheoMaGoiMon(magoimon,mamonan);
            int soluongmoi = Integer.parseInt(edThemSL.getText().toString());
            int tongsoluong = soluongcu + soluongmoi;
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(tongsoluong);
            boolean ktcapnhat = goiMonDAO.CapNhatSoLuong(chiTietGoiMonDTO);
            if(ktcapnhat){
                Toast.makeText(this,R.string.thanhcong,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,R.string.thatbai,Toast.LENGTH_SHORT).show();
            }
        }else{
            int soluonggoi = Integer.parseInt(edThemSL.getText().toString());
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(soluonggoi);
            boolean ktcapnhat = goiMonDAO.ThemChiTietGoiMon(chiTietGoiMonDTO);
            if(ktcapnhat){
                Toast.makeText(this,R.string.thanhcong,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,R.string.thatbai,Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}
