package hoanglv.fpoly.pnlib.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import hoanglv.fpoly.pnlib.databases.DatabaseHelper;
import hoanglv.fpoly.pnlib.models.ThanhVien;

public class ThanhVienDAO {
    private SQLiteDatabase db;

    public ThanhVienDAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.db = databaseHelper.getWritableDatabase();
        this.db = databaseHelper.getReadableDatabase();
    }

    public long insert(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("hoTen", thanhVien.getHoTen());
        values.put("namSinh", thanhVien.getNamSinh());
        return db.insert("THANHVIEN", null, values);
    }

    public ArrayList<ThanhVien> getData(String sql) {
        ArrayList<ThanhVien> thanhVienList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setMaTV(cursor.getInt(0));
                thanhVien.setHoTen(cursor.getString(1));
                thanhVien.setNamSinh(cursor.getString(2));
                thanhVienList.add(thanhVien);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return thanhVienList;
    }

    public ArrayList<ThanhVien> getAll() {
        String sql = "SELECT * FROM THANHVIEN";
        return getData(sql);
    }

    public ThanhVien getID (String iD) {
        String sql = "SELECT * FROM THANHVIEN WHERE maTV = "+ iD;
        ArrayList<ThanhVien> thanhVienList = getData(sql);
        return thanhVienList.get(0);
    }

    public int update(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("hoTen", thanhVien.getHoTen());
        values.put("namSinh", thanhVien.getNamSinh());
        return db.update("THANHVIEN", values, "maTV = ?", new String[]{String.valueOf(thanhVien.getMaTV())});
    }

    public void delete(String id) {
        db.delete("THANHVIEN", "maTV = ?", new String[]{String.valueOf(id)});
    }
}
