package com.orderfood.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.orderfood.DAL.Database;
import com.orderfood.DTO.QuyenDTO;

import java.util.ArrayList;
import java.util.List;

public class QuyenDAO {
    private SQLiteDatabase sqLiteDatabase;
    public QuyenDAO(Context context){
        Database database = new Database(context);
        sqLiteDatabase = database.open();
    }
    public void ThemQuyen(String tenquyen){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.TB_QUYEN_TENQUYEN,tenquyen);
        sqLiteDatabase.insert(Database.TB_QUYEN,null,contentValues);
    }
    @SuppressLint("Range")
    public List<QuyenDTO> LayDSQuyen(){
        List<QuyenDTO> quyenDTOs = new ArrayList<QuyenDTO>();
        String truyvan = " SELECT * FROM " + Database.TB_QUYEN;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            QuyenDTO quyenDTO = new QuyenDTO();
            quyenDTO.setMaQuyen(cursor.getInt(cursor.getColumnIndex(Database.TB_QUYEN_MAQUYEN)));
            quyenDTO.setTenQuyen(cursor.getString(cursor.getColumnIndex(Database.TB_QUYEN_TENQUYEN)));
            quyenDTOs.add(quyenDTO);
            cursor.moveToNext();
        }
        return quyenDTOs;
    }
}
