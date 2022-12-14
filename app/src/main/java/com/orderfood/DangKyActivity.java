package com.orderfood;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.orderfood.DAO.NhanVienDAO;
import com.orderfood.DAO.QuyenDAO;
import com.orderfood.DTO.NhanVienDTO;
import com.orderfood.DTO.QuyenDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edTenDangNhapDK,edMatKhauDK,edNgaySinhDK,edCMNDDK;
    private RadioGroup rgGioiTinh;
    private RadioButton rdNam,rdNu;
    private Button btnDongYDK,btnThoatDK;
    private String stringGioiTinh;
    private NhanVienDAO nhanVienDAO;
    private QuyenDAO quyenDAO;
    private int manhanvien = 0;
//    private int landautien = 0;
//    private Spinner spinner;
//    private List<QuyenDTO> quyenDTOList;
//    private List<String> dataAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);
        edTenDangNhapDK=findViewById(R.id.edTenDangNhapDK);
        edMatKhauDK=findViewById(R.id.edMatKhauDK);
        edNgaySinhDK=findViewById(R.id.edNgaySinhDK);
        edCMNDDK=findViewById(R.id.edCMNDDK);
        rgGioiTinh=findViewById(R.id.rgGioiTinh);
        rdNam=findViewById(R.id.rdNam);
        rdNu=findViewById(R.id.rdNu);
        btnDongYDK=findViewById(R.id.btnDongYDK);
        btnThoatDK=findViewById(R.id.btnThoatDK);
//        spinner=findViewById(R.id.spQuyen);
        btnDongYDK.setOnClickListener(this);
        btnThoatDK.setOnClickListener(this);
        edNgaySinhDK.setOnClickListener(this);
        nhanVienDAO = new NhanVienDAO(this);
//        quyenDAO = new QuyenDAO(this);
//        quyenDTOList = quyenDAO.LayDSQuyen();
//        dataAdapter = new ArrayList<String>();
//        for (int i = 0; i < quyenDTOList.size(); i++) {
//            String tenquyen = quyenDTOList.get(i).getTenQuyen();
//            dataAdapter.add(tenquyen);
//        }
//        landautien = getIntent().getIntExtra("landautien",0);
//        if(landautien==0) {
//            quyenDAO.ThemQuyen("quản lý");
//            quyenDAO.ThemQuyen("nhân viên");
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataAdapter);
//            spinner.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        }else{
//            spinner.setVisibility(View.GONE);
//        }
        manhanvien = getIntent().getIntExtra("manhanvien",0);
        if(manhanvien!=0){
            btnDongYDK.setText(R.string.capnhat);
            NhanVienDTO nhanVienDTO = nhanVienDAO.LayNhanVienTheoMa(manhanvien);
            edTenDangNhapDK.setText(nhanVienDTO.getTENDN());
            edMatKhauDK.setText(nhanVienDTO.getMATKHAU());
            edNgaySinhDK.setText(nhanVienDTO.getNGAYSINH());
            edCMNDDK.setText(String.valueOf(nhanVienDTO.getCMND()));
            String gioitinh = nhanVienDTO.getGIOITINH();
            if(gioitinh.equals(R.string.nam)){
                rdNam.setChecked(true);
            }else{
                rdNu.setChecked(true);
            }
        }
    }
    private void DongYThemNhanVien(){
        String stringTenDangNhap = edTenDangNhapDK.getText().toString();
        String stringMatKhau = edMatKhauDK.getText().toString();
        switch (rgGioiTinh.getCheckedRadioButtonId()) {
            case R.id.rdNam:
                stringGioiTinh = "Nam";
                break;
            case R.id.rdNu:
                stringGioiTinh = "Nữ";
                break;
        }
        String stringNgaySinh = edNgaySinhDK.getText().toString();
        long longCMND = Long.parseLong(edCMNDDK.getText().toString());
        if (stringTenDangNhap == null || stringTenDangNhap.equals("")) {
            Toast.makeText(DangKyActivity.this, R.string.nhapthongtin, Toast.LENGTH_SHORT).show();
        } else if (stringMatKhau == null || stringMatKhau.equals("")) {
            Toast.makeText(DangKyActivity.this, R.string.nhapthongtin, Toast.LENGTH_SHORT).show();
        } else {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setTENDN(stringTenDangNhap);
            nhanVienDTO.setMATKHAU(stringMatKhau);
            nhanVienDTO.setGIOITINH(stringGioiTinh);
            nhanVienDTO.setNGAYSINH(stringNgaySinh);
            nhanVienDTO.setCMND(longCMND);
//            if(landautien!=0){
//                //gan mac dinh quyen nhan vien admin landautien
//                nhanVienDTO.setMAQUYEN(1);
//            }else{
//                //gan quyen = quyen ma admin chon khi tao nhan vien
//                int vitri = spinner.getSelectedItemPosition();
//                int maquyen = quyenDTOList.get(vitri).getMaQuyen();
//                nhanVienDTO.setMAQUYEN(maquyen);
//            }
            long kiemtra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
            if (kiemtra != 0) {
                Toast.makeText(DangKyActivity.this, R.string.thanhcong, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DangKyActivity.this, R.string.thatbai, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void SuaNhanVien(){
        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        switch (rgGioiTinh.getCheckedRadioButtonId()) {
            case R.id.rdNam:
                stringGioiTinh = "Nam";
                break;
            case R.id.rdNu:
                stringGioiTinh = "Nữ";
                break;
        }
        nhanVienDTO.setMANV(manhanvien);
        nhanVienDTO.setTENDN(edTenDangNhapDK.getText().toString());
        nhanVienDTO.setMATKHAU(edMatKhauDK.getText().toString());
        nhanVienDTO.setCMND(Long.parseLong(edCMNDDK.getText().toString()));
        nhanVienDTO.setNGAYSINH(edNgaySinhDK.getText().toString());
        nhanVienDTO.setGIOITINH(stringGioiTinh);
        boolean kiemtra = nhanVienDAO.SuaNhanVien(nhanVienDTO);
        if(kiemtra){
            Toast.makeText(DangKyActivity.this,R.string.thanhcong,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(DangKyActivity.this,R.string.thatbai,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnDongYDK:
                if(manhanvien == 0) {
                    DongYThemNhanVien();
                }else{
                    SuaNhanVien();
                }
                finish();
                break;
            case R.id.btnThoatDK:
                finish();
                break;
            case R.id.edNgaySinhDK:
                final Calendar c = Calendar.getInstance();
                int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH);
                int day=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog= new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date="";
                        if(m>8){
                            date=d+"/"+(m+1)+"/"+y;
                        }else{
                            date=d+"/0"+(m+1)+"/"+y;
                        }
                        edNgaySinhDK.setText(date);
                    }
                },year,month,day);
                dialog.show();
        }
    }
}
