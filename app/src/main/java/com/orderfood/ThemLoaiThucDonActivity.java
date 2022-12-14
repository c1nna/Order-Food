package com.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.orderfood.DAO.LoaiMonAnDAO;

public class ThemLoaiThucDonActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnDongYThemLoaiThucDon;
    private EditText edTenLoai;
    private LoaiMonAnDAO loaiMonAnDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themloaithucdon);
        loaiMonAnDAO = new LoaiMonAnDAO(this);
        btnDongYThemLoaiThucDon=findViewById(R.id.btnDongYThemLoaiThucDon);
        edTenLoai=findViewById(R.id.edThemLoaiThucDon);
        btnDongYThemLoaiThucDon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String stringTenLoaiThucDon = edTenLoai.getText().toString();
        if(stringTenLoaiThucDon!=null||stringTenLoaiThucDon.equals("")){
            boolean kiemtra = loaiMonAnDAO.ThemLoaiMonAn(stringTenLoaiThucDon);
            Intent iDuLieu = new Intent();
            iDuLieu.putExtra("kiemtraloaithucdon",kiemtra);
            setResult(Activity.RESULT_OK,iDuLieu);
            finish();
        }else{
            Toast.makeText(this,R.string.nhapthongtin,Toast.LENGTH_SHORT).show();
        }
    }
}
