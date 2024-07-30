package hoanglv.fpoly.pnlib.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hoanglv.fpoly.pnlib.databases.DatabaseHelper;
import hoanglv.fpoly.pnlib.models.PhieuMuon;

public class PhieuMuonDao {
    private SQLiteDatabase db;

    public PhieuMuonDao(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.db = databaseHelper.getWritableDatabase();
        this.db = databaseHelper.getReadableDatabase();
    }

    public ArrayList<PhieuMuon> getData(String sql) {
        ArrayList<PhieuMuon> phieuMuonList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaPM(cursor.getInt(0));
                phieuMuon.setMaTT(cursor.getString(1));
                phieuMuon.setMaTV(cursor.getInt(2));
                phieuMuon.setMaSach(cursor.getInt(3));
                phieuMuon.setTienThue(cursor.getInt(4));
                phieuMuon.setNgay(cursor.getString(5));
                phieuMuon.setTraSach(cursor.getInt(6));
                phieuMuonList.add(phieuMuon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return phieuMuonList;
    }

    public long insert(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("tienThue", phieuMuon.getTienThue());
        values.put("ngay", phieuMuon.getNgay());
        values.put("traSach", phieuMuon.getTraSach());
        return db.insert("PHIEUMUON", null, values);
    }

    public int update(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("tienThue", phieuMuon.getTienThue());
        values.put("ngay", phieuMuon.getNgay());
        values.put("traSach", phieuMuon.getTraSach());
        return db.update("PHIEUMUON", values, "maPM = ?", new String[]{String.valueOf(phieuMuon.getMaPM())});
    }

    public void delete(String id) {
        db.delete("PHIEUMUON", "maPM = ?", new String[]{String.valueOf(id)});
    }
}
