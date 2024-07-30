package hoanglv.fpoly.pnlib.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import hoanglv.fpoly.pnlib.databases.DatabaseHelper;
import hoanglv.fpoly.pnlib.models.LoaiSach;

public class LoaiSachDAO {
    private SQLiteDatabase db;

    public LoaiSachDAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.db = databaseHelper.getWritableDatabase();
        this.db = databaseHelper.getReadableDatabase();
    }

    public ArrayList<LoaiSach> getData(String sql) {
        ArrayList<LoaiSach> loaiSachList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setMaLoai(cursor.getInt(0));
                loaiSach.setTenLoai(cursor.getString(1));
                loaiSachList.add(loaiSach);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return loaiSachList;
    }

    public LoaiSach getID (String iD) {
        String sql = "SELECT * FROM LOAISACH WHERE maLoai = " + iD;
        ArrayList<LoaiSach> loaiSachList = getData(sql);
        return loaiSachList.get(0);
    }

    public long insert(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSach.getTenLoai());
        return db.insert("LOAISACH", null, values);
    }

    public int update(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSach.getTenLoai());
        return db.update("LOAISACH", values, "maLoai = ?", new String[]{String.valueOf(loaiSach.getMaLoai())});
    }

    public void delete(String id) {
        db.delete("LOAISACH", "maLoai = ?", new String[]{String.valueOf(id)});
    }
}
