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

import com.orderfood.DAO.BanAnDAO;

public class SuaBanAnActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSua;
    private EditText edSua;
    private BanAnDAO banAnDAO;
    private int maban;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suabanan);
        btnSua = findViewById(R.id.btnDongYSuaBanAn);
        edSua = findViewById(R.id.edSuaTenBanAn);
        btnSua.setOnClickListener(this);
        banAnDAO = new BanAnDAO(this);
        maban = getIntent().getIntExtra("maban",0);
    }

    @Override
    public void onClick(View view) {
        String tenban = edSua.getText().toString();
        if(!tenban.trim().equals("")||tenban.trim()!=null){
            boolean kiemtra = banAnDAO.CapNhatTenBan(maban,tenban);
            Intent intent = new Intent();
            intent.putExtra("kiemtra",kiemtra);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else{
            Toast.makeText(SuaBanAnActivity.this,R.string.nhapthongtin,Toast.LENGTH_SHORT).show();
        }
    }
}
