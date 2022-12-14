package com.orderfood.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.orderfood.DAL.Database;
import com.orderfood.DTO.BanAnDTO;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO {
    private SQLiteDatabase sqLiteDatabase;
    public BanAnDAO(Context context){
        Database database = new Database(context);
        sqLiteDatabase = database.open();
    }
    public boolean ThemBanAn(String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.TB_BANAN_TENBAN,tenban);
        contentValues.put(Database.TB_BANAN_TINHTRANG,"false");
        long kiemtra = sqLiteDatabase.insert(Database.TB_BANAN,null,contentValues);
        if(kiemtra!=0 ){
            return true;
        }else{
            return false;
        }
    }
    @SuppressLint("Range")
    public List<BanAnDTO> LayTatCaBanAn(){
        List<BanAnDTO> banAnDTOList = new ArrayList<BanAnDTO>();
        String truyvan = " SELECT * FROM " + Database.TB_BANAN;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(Database.TB_BANAN_MABAN)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndex(Database.TB_BANAN_TENBAN)));
            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }
        return banAnDTOList;
    }
    @SuppressLint("Range")
    public String LayTinhTrangBanTheoMa(int maban){
        String tinhtrang = "";
        String truyvan = " SELECT * FROM " + Database.TB_BANAN
                + " WHERE " + Database.TB_BANAN_MABAN + " = '" + maban + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(Database.TB_BANAN_TINHTRANG));
            cursor.moveToNext();
        }
        return tinhtrang;
    }
    public boolean CapNhatTinhTrangBan(int maban,String tinhtrang){
        ContentValues values = new ContentValues();
        values.put(Database.TB_BANAN_TINHTRANG,tinhtrang);
        long kiemtra = sqLiteDatabase.update(Database.TB_BANAN,values,Database.TB_BANAN_MABAN + " = '" + maban + "' ",null);
        if(kiemtra!=0){
            return true;
        }else {
            return false;
        }
    }
    public boolean XoaBanAnTheoMa(int maban){
        long kiemtra = sqLiteDatabase.delete(Database.TB_BANAN,Database.TB_BANAN_MABAN + " = " + maban,null);
        if(kiemtra!=0){
            return true;
        }else{
            return false;
        }
    }
    public boolean CapNhatTenBan(int maban,String tenban){
        ContentValues values = new ContentValues();
        values.put(Database.TB_BANAN_TENBAN,tenban);
        long kiemtra = sqLiteDatabase.update(Database.TB_BANAN,values,Database.TB_BANAN_MABAN + " = '" + maban + "' ",null);
        if(kiemtra!=0){
            return true;
        }else {
            return false;
        }
    }
}
