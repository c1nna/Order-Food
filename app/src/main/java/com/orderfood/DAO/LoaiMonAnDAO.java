package com.orderfood.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.orderfood.DAL.Database;
import com.orderfood.DTO.LoaiMonAnDTO;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDAO {
    private SQLiteDatabase sqLiteDatabase;
    public LoaiMonAnDAO(Context context){
        Database database = new Database(context);
        sqLiteDatabase = database.open();
    }
    public boolean ThemLoaiMonAn(String tenloai){
        ContentValues values = new ContentValues();
        values.put(Database.TB_LOAIMONAN_TENLOAI,tenloai);
        long kiemtra = sqLiteDatabase.insert(Database.TB_LOAIMONAN,null,values);
        if(kiemtra!=0){
            return true;
        }else{
            return false;
        }
    }
    @SuppressLint("Range")
    public List<LoaiMonAnDTO> LayDanhSachLoaiMonAn(){
        List<LoaiMonAnDTO> loaiMonAnDTOs = new ArrayList<LoaiMonAnDTO>();
        String truyvan = " SELECT * FROM " + Database.TB_LOAIMONAN;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiMonAnDTO loaiMonAnDTO = new LoaiMonAnDTO();
            loaiMonAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(Database.TB_LOAIMONAN_MALOAI)));
            loaiMonAnDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(Database.TB_LOAIMONAN_TENLOAI)));
            loaiMonAnDTOs.add(loaiMonAnDTO);
            cursor.moveToNext();
        }
        return loaiMonAnDTOs;
    }
    @SuppressLint("Range")
    public String LayHinhLoaiMonAn(int maloai){
        String hinhanh = "";
        String truyvan = " SELECT * FROM " + Database.TB_MONAN
                + " WHERE " + Database.TB_MONAN_MALOAI + " = '" + maloai + "' "
                + " AND " + Database.TB_MONAN_HINHANH + " != '' "
                + " ORDER BY " + Database.TB_MONAN_MAMON + " LIMIT 1 ";
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            hinhanh = cursor.getString(cursor.getColumnIndex(Database.TB_MONAN_HINHANH));
            cursor.moveToNext();
        }
        return hinhanh;
    }
}
