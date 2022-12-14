package com.orderfood.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.orderfood.DAL.Database;
import com.orderfood.DTO.MonAnDTO;

import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {
    private SQLiteDatabase sqLiteDatabase;
    public MonAnDAO(Context context){
        Database database = new Database(context);
        sqLiteDatabase = database.open();
    }
    public boolean ThemMonAn(MonAnDTO monAnDTO){
        ContentValues values =  new ContentValues();
        values.put(Database.TB_MONAN_TENMONAN,monAnDTO.getTenMonAn());
        values.put(Database.TB_MONAN_GIATIEN,monAnDTO.getGiaTien());
        values.put(Database.TB_MONAN_MALOAI,monAnDTO.getMaLoai());
        values.put(Database.TB_MONAN_HINHANH,monAnDTO.getHinhAnh());
        long kiemtra = sqLiteDatabase.insert(Database.TB_MONAN,null,values);
        if(kiemtra!=0){
            return true;
        }else{
            return false;
        }
    }
    @SuppressLint("Range")
    public List<MonAnDTO> LayDSMonAnTheoLoai(int maloai){
        List<MonAnDTO> monAnDTOs = new ArrayList<MonAnDTO>();
        String truyvan = " SELECT * FROM " + Database.TB_MONAN
                + " WHERE " + Database.TB_MONAN_MALOAI + " = '" + maloai + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            MonAnDTO monAnDTO = new MonAnDTO();
            monAnDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(Database.TB_MONAN_HINHANH))+"");
            monAnDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(Database.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(Database.TB_MONAN_GIATIEN)));
            monAnDTO.setMaMonAn(cursor.getInt(cursor.getColumnIndex(Database.TB_MONAN_MAMON)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(Database.TB_MONAN_MALOAI)));
            monAnDTOs.add(monAnDTO);
            cursor.moveToNext();
        }
        return monAnDTOs;
    }
}
