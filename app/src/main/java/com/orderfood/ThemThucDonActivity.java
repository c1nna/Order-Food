package com.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.orderfood.Adapter.HienThiLoaiMonAnAdapter;
import com.orderfood.DAO.LoaiMonAnDAO;
import com.orderfood.DAO.MonAnDAO;
import com.orderfood.DTO.LoaiMonAnDTO;
import com.orderfood.DTO.MonAnDTO;
import java.util.List;


public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {
    public static int REQ_CODE_THEM_LOAI = 113;
    public static int REQ_CODE_MO_HINH = 123;
    private ImageButton imgThemLoaiThucDon;
    private Spinner spinner;
    private LoaiMonAnDAO loaiMonAnDAO;
    private List<LoaiMonAnDTO> loaiMonAnDTOs;
    private HienThiLoaiMonAnAdapter hienThiLoaiMonAnAdapter;
    private ImageView imgHinhThucDon;
    private Button btnDongYThemMonAn,btnThoatThemMonAn;
    private String stringDuongDanHinh;
    private EditText edThemTenMonAn,edThemGiaTien;
    private MonAnDAO monAnDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthucdon);
        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);
        imgThemLoaiThucDon=findViewById(R.id.imgThemLoaiThucDon);
        imgHinhThucDon=findViewById(R.id.imgHinhThucDon);
        spinner=findViewById(R.id.spLoaiMonAn);
        btnDongYThemMonAn=findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn=findViewById(R.id.btnThoatThemMonAn);
        edThemTenMonAn=findViewById(R.id.edThemTenMonAn);
        edThemGiaTien=findViewById(R.id.edThemGiaTien);
        HienThiSpinnerLoaiMonAn();
        imgThemLoaiThucDon.setOnClickListener(this);
        imgHinhThucDon.setOnClickListener(this);
        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);
    }
    private void HienThiSpinnerLoaiMonAn (){
        loaiMonAnDTOs = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        hienThiLoaiMonAnAdapter = new HienThiLoaiMonAnAdapter(ThemThucDonActivity.this,R.layout.custom_layout_spinloaithucdon,loaiMonAnDTOs);
        spinner.setAdapter(hienThiLoaiMonAnAdapter);
        hienThiLoaiMonAnAdapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.imgThemLoaiThucDon:
                Intent iThemLoaiMonAn = new Intent(ThemThucDonActivity.this,ThemLoaiThucDonActivity.class);
                startActivityForResult(iThemLoaiMonAn,REQ_CODE_THEM_LOAI);
                break;
            case R.id.imgHinhThucDon:
                Intent iMoHinh;
//                iMoHinh.setType("image/*");
//                iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(iMoHinh,"Chọn hình thực đơn"),REQ_CODE_MO_HINH);
                if(Build.VERSION.SDK_INT<19){
                    iMoHinh = new Intent();
                    iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                    iMoHinh.setType("image/*");
                    startActivityForResult(Intent.createChooser(iMoHinh,"Chọn hình thực đơn"),REQ_CODE_MO_HINH);
                }else{
                    iMoHinh = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    iMoHinh.addCategory(Intent.CATEGORY_OPENABLE);
                    iMoHinh.setType("image/*");
                    startActivityForResult(Intent.createChooser(iMoHinh,"Chọn hình thực đơn"),REQ_CODE_MO_HINH);
                }
                break;
            case R.id.btnDongYThemMonAn:
                int vitri = spinner.getSelectedItemPosition();
                int maloai = loaiMonAnDTOs.get(vitri).getMaLoai();
                String tenmonan = edThemTenMonAn.getText().toString();
                String giatien = edThemGiaTien.getText().toString();
                if(tenmonan!=null && giatien!=null){
                    MonAnDTO monAnDTO = new MonAnDTO();
                    monAnDTO.setGiaTien(giatien);
                    monAnDTO.setHinhAnh(stringDuongDanHinh);
                    monAnDTO.setTenMonAn(tenmonan);
                    monAnDTO.setMaLoai(maloai);
                    boolean kiemtra = monAnDAO.ThemMonAn(monAnDTO);
                    if(kiemtra){
                        Toast.makeText(this,R.string.thanhcong,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,R.string.thatbai,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,R.string.nhapthongtin,Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            case R.id.btnThoatThemMonAn:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_THEM_LOAI){
            if(resultCode == Activity.RESULT_OK){
                Intent dulieu = data;
                boolean kiemtra = dulieu.getBooleanExtra("kiemtraloaithucdon",false);
                if(kiemtra){
                    HienThiSpinnerLoaiMonAn();
                    Toast.makeText(this,R.string.thanhcong,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,R.string.thatbai,Toast.LENGTH_SHORT).show();
                }
            }
        }else if(requestCode == REQ_CODE_MO_HINH){
            if(resultCode == Activity.RESULT_OK && data!=null){
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
//                    imgHinhThucDon.setImageBitmap(bitmap);
//                    stringDuongDanHinh = data.getData().toString();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                imgHinhThucDon.setImageURI(data.getData());
                stringDuongDanHinh = data.getData().toString();
            }
        }
    }
}
