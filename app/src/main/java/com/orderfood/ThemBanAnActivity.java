package com.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.orderfood.DAO.BanAnDAO;

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edThemTenBanAn;
    private Button btnDongYThemBanAn;
    private BanAnDAO banAnDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thembanan);
        edThemTenBanAn=findViewById(R.id.edThemTenBanAn);
        btnDongYThemBanAn=findViewById(R.id.btnDongYThemBanAn);
        banAnDAO = new BanAnDAO(this);
        btnDongYThemBanAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String stringTenBanAn = edThemTenBanAn.getText().toString();
        if(stringTenBanAn!=null||!stringTenBanAn.equals("")){
            boolean kiemtra = banAnDAO.ThemBanAn(stringTenBanAn);
            Intent intent = new Intent();
            intent.putExtra("ketquathem",kiemtra);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }
}
