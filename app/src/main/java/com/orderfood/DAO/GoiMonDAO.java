package com.orderfood.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.orderfood.DAL.Database;
import com.orderfood.DTO.ChiTietGoiMonDTO;
import com.orderfood.DTO.GoiMonDTO;
import com.orderfood.DTO.ThanhToanDTO;

import java.util.ArrayList;
import java.util.List;

public class GoiMonDAO {
    private SQLiteDatabase sqLiteDatabase;
    public GoiMonDAO(Context context){
        Database database = new Database(context);
        sqLiteDatabase = database.open();
    }
    public long ThemGoiMon(GoiMonDTO goiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.TB_GOIMON_MABAN,goiMonDTO.getMaBan());
        contentValues.put(Database.TB_GOIMON_MANV,goiMonDTO.getMaNV());
        contentValues.put(Database.TB_GOIMON_NGAYGOI,goiMonDTO.getNgayGoi());
        contentValues.put(Database.TB_GOIMON_TINHTRANG,goiMonDTO.getTinhTrang());
        long magoimon = sqLiteDatabase.insert(Database.TB_GOIMON,null,contentValues);
        return magoimon;
    }
    @SuppressLint("Range")
    public long LayMaGoiMonTheoMaBan(int maban, String tinhtrang){
        String truyvan = " SELECT * FROM " + Database.TB_GOIMON
                + " WHERE " + Database.TB_GOIMON_MABAN + " = '" + maban + "' "
                + " AND " + Database.TB_GOIMON_TINHTRANG + " = '" + tinhtrang + "' ";
        long magoimon = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            magoimon = cursor.getInt(cursor.getColumnIndex(Database.TB_GOIMON_MAGOIMON));
            cursor.moveToNext();
        }
        return magoimon;
    }
    public boolean KiemTraMonAnDaTonTai(int magoimon, int mamonan){
        String truyvan = " SELECT * FROM " + Database.TB_CHITIETGOIMON
                + " WHERE " + Database.TB_CHITIETGOIMON_MAMONAN + " = '" + mamonan
                + "' AND " + Database.TB_CHITIETGOIMON_MAGOIMON + " = '" + magoimon +"' ";
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        if(cursor.getCount()!=0){
            return true;
        }else{
            return false;
        }
    }
    @SuppressLint("Range")
    public int LaySLMonAnTheoMaGoiMon(int magoimon,int mamonan) {
        int soluong = 0;
        String truyvan = " SELECT * FROM " + Database.TB_CHITIETGOIMON
                + " WHERE " + Database.TB_CHITIETGOIMON_MAMONAN + " = '" + mamonan
                + "' AND " + Database.TB_CHITIETGOIMON_MAGOIMON + " = '" + magoimon + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(Database.TB_CHITIETGOIMON_SOLUONG));
            cursor.moveToNext();
        }
        return soluong;
    }
    public boolean CapNhatSoLuong(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues values = new ContentValues();
        values.put(Database.TB_CHITIETGOIMON_SOLUONG,chiTietGoiMonDTO.getSoLuong());
        long kiemtra = sqLiteDatabase.update(Database.TB_CHITIETGOIMON,values,Database.TB_CHITIETGOIMON_MAGOIMON + " = " + chiTietGoiMonDTO.getMaGoiMon()
                + " AND " + Database.TB_CHITIETGOIMON_MAMONAN + " = " + chiTietGoiMonDTO.getMaMonAn(),null);
        if(kiemtra != 0){
            return true;
        }else{
            return false;
        }
    }
    public boolean ThemChiTietGoiMon(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues values = new ContentValues();
        values.put(Database.TB_CHITIETGOIMON_SOLUONG,chiTietGoiMonDTO.getSoLuong());
        values.put(Database.TB_CHITIETGOIMON_MAGOIMON,chiTietGoiMonDTO.getMaGoiMon());
        values.put(Database.TB_CHITIETGOIMON_MAMONAN,chiTietGoiMonDTO.getMaMonAn());
        long kiemtra = sqLiteDatabase.insert(Database.TB_CHITIETGOIMON,null,values);
        if(kiemtra!=0){
            return true;
        }else{
            return false;
        }
    }
    @SuppressLint("Range")
    public List<ThanhToanDTO> LayDSMonAnTheoMaGoiMon(int magoimon){
        String truyvan = " SELECT * FROM " + Database.TB_CHITIETGOIMON + " ct, " + Database.TB_MONAN
                        + " ma WHERE " + " ct. " + Database.TB_CHITIETGOIMON_MAMONAN + " = ma. " + Database.TB_MONAN_MAMON
                        + " AND " + Database.TB_CHITIETGOIMON_MAGOIMON + " = '" + magoimon + "' ";
        List<ThanhToanDTO> thanhToanDTOList = new ArrayList<ThanhToanDTO>();
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThanhToanDTO thanhToanDTO = new ThanhToanDTO();
            thanhToanDTO.setSoLuong(cursor.getInt(cursor.getColumnIndex(Database.TB_CHITIETGOIMON_SOLUONG)));
            thanhToanDTO.setGiaTien(cursor.getInt(cursor.getColumnIndex(Database.TB_MONAN_GIATIEN)));
            thanhToanDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(Database.TB_MONAN_TENMONAN)));
            thanhToanDTOList.add(thanhToanDTO);
            cursor.moveToNext();
        }
        return thanhToanDTOList;
    }
    public boolean CapNhatTrangThaiGoiMonTheoMaBan(int maban,String tinhtrang){
        ContentValues values = new ContentValues();
        values.put(Database.TB_GOIMON_TINHTRANG,maban);
        long kiemtra = sqLiteDatabase.update(Database.TB_GOIMON,values,Database.TB_GOIMON_MABAN + " = " + maban, null);
        if(kiemtra!=0){
            return true;
        }else{
            return false;
        }
    }
}
