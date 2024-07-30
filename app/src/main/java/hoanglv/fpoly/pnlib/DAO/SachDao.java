package hoanglv.fpoly.pnlib.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import hoanglv.fpoly.pnlib.databases.DatabaseHelper;
import hoanglv.fpoly.pnlib.models.Sach;

public class SachDao {
    private SQLiteDatabase db;

    public SachDao(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.db = databaseHelper.getWritableDatabase();
        this.db = databaseHelper.getReadableDatabase();
    }

    public long insert(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("giaThue", sach.getGiaThue());
        values.put("maLoai", sach.getMaLoai());
        return db.insert("SACH", null, values);
    }

    public ArrayList<Sach> getData(String sql) {
        ArrayList<Sach> sachList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getInt(0));
                sach.setTenSach(cursor.getString(1));
                sach.setGiaThue(cursor.getInt(2));
                sach.setMaLoai(cursor.getInt(3));
                sachList.add(sach);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sachList;
    }

    public ArrayList<Sach> getAll() {
        String sql = "SELECT * FROM SACH";
        return getData(sql);
    }

    public Sach getID (String iD) {
        String sql = "SELECT * FROM SACH WHERE maSach = " + iD;
        ArrayList<Sach> sachList = getData(sql);
        return sachList.get(0);
    }

    public int update(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("giaThue", sach.getGiaThue());
        values.put("maLoai", sach.getMaLoai());
        return db.update("SACH", values, "maSach = ?", new String[]{String.valueOf(sach.getMaSach())});
    }

    public void deleteSach(String id) {
        db.delete("SACH", "maSach = ?", new String[]{String.valueOf(id)});
    }
}
