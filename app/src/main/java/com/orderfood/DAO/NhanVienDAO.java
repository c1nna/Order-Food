package com.orderfood.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.orderfood.DAL.Database;
import com.orderfood.DTO.NhanVienDTO;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private SQLiteDatabase sqLiteDatabase;
    public NhanVienDAO(Context context){
        Database database = new Database(context);
        sqLiteDatabase = database.open();
    }
    public long ThemNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.TB_NHANVIEN_TENDN,nhanVienDTO.getTENDN());
        contentValues.put(Database.TB_NHANVIEN_CMND,nhanVienDTO.getCMND());
        contentValues.put(Database.TB_NHANVIEN_GIOITINH,nhanVienDTO.getGIOITINH());
        contentValues.put(Database.TB_NHANVIEN_MATKHAU,nhanVienDTO.getMATKHAU());
        contentValues.put(Database.TB_NHANVIEN_NGAYSINH,nhanVienDTO.getNGAYSINH());
        contentValues.put(Database.TB_NHANVIEN_MAQUYEN,nhanVienDTO.getMAQUYEN());
        long kiemtra = sqLiteDatabase.insert(Database.TB_NHANVIEN,null,contentValues);
        return kiemtra;
    }
    public boolean KiemTraNhanVien(){
        String truyvan = "SELECT * FROM " + Database.TB_NHANVIEN;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        if(cursor.getCount()!=0){
            return true;
        }else{
            return false;
        }
    }
    @SuppressLint("Range")
    public int KiemTraDangNhap(String tenDN, String matkhau){
        String truyvan = " SELECT * FROM " + Database.TB_NHANVIEN
                + " WHERE " + Database.TB_NHANVIEN_TENDN + " = '" + tenDN
                + "' AND " + Database.TB_NHANVIEN_MATKHAU + " = '" + matkhau + "'";
        int manhanvien = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            manhanvien = cursor.getInt(cursor.getColumnIndex(Database.TB_NHANVIEN_MANV));
            cursor.moveToNext();
        }
        return manhanvien;
    }
    @SuppressLint("Range")
    public List<NhanVienDTO> LayDSNhanVien(){
        List<NhanVienDTO> nhanVienDTOS = new ArrayList<NhanVienDTO>();
        String truyvan = " SELECT * FROM " + Database.TB_NHANVIEN;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndex(Database.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndex(Database.TB_NHANVIEN_NGAYSINH)));
            nhanVienDTO.setCMND(cursor.getLong(cursor.getColumnIndex(Database.TB_NHANVIEN_CMND)));
            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndex(Database.TB_NHANVIEN_TENDN)));
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndex(Database.TB_NHANVIEN_MANV)));
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndex(Database.TB_NHANVIEN_MATKHAU)));
            nhanVienDTOS.add(nhanVienDTO);
            cursor.moveToNext();
        }
        return nhanVienDTOS;
    }
    public boolean XoaNhanVien(int manhanvien){
        long kiemtra = sqLiteDatabase.delete(Database.TB_NHANVIEN,Database.TB_NHANVIEN_MANV + " = " + manhanvien,null );
        if(kiemtra!=0){
            return true;
        }else{
            return false;
        }
    }
    @SuppressLint("Range")
    public NhanVienDTO LayNhanVienTheoMa(int manhanvien){
        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        String truyvan = " SELECT * FROM " + Database.TB_NHANVIEN
                + " WHERE " + Database.TB_NHANVIEN_MANV + " = " + manhanvien;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndex(Database.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndex(Database.TB_NHANVIEN_NGAYSINH)));
            nhanVienDTO.setCMND(cursor.getLong(cursor.getColumnIndex(Database.TB_NHANVIEN_CMND)));
            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndex(Database.TB_NHANVIEN_TENDN)));
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndex(Database.TB_NHANVIEN_MANV)));
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndex(Database.TB_NHANVIEN_MATKHAU)));
            cursor.moveToNext();
        }
        return nhanVienDTO;
    }
    public boolean SuaNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.TB_NHANVIEN_TENDN,nhanVienDTO.getTENDN());
        contentValues.put(Database.TB_NHANVIEN_CMND,nhanVienDTO.getCMND());
        contentValues.put(Database.TB_NHANVIEN_GIOITINH,nhanVienDTO.getGIOITINH());
        contentValues.put(Database.TB_NHANVIEN_MATKHAU,nhanVienDTO.getMATKHAU());
        contentValues.put(Database.TB_NHANVIEN_NGAYSINH,nhanVienDTO.getNGAYSINH());
        long kiemtra = sqLiteDatabase.update(Database.TB_NHANVIEN,contentValues,Database.TB_NHANVIEN_MANV + " = " + nhanVienDTO.getMANV(),null);
        if(kiemtra!=0){
            return true;
        }else{
            return false;
        }
    }
//    @SuppressLint("Range")
//    public int LayQuyenNhanVien(int manhanvien){
//        int maquyen = 0;
//        String truyvan = " SELECT * FROM " + Database.TB_NHANVIEN
//                + " WHERE " + Database.TB_NHANVIEN_MANV + " = " + manhanvien;
//        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
//        cursor.moveToFirst();
//        while(!cursor.isAfterLast()){
//            maquyen = cursor.getInt(cursor.getColumnIndex(Database.TB_NHANVIEN_MAQUYEN));
//            cursor.moveToNext();
//        }
//        return maquyen;
//    }
}
