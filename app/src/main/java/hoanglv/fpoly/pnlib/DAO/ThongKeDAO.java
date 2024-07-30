package hoanglv.fpoly.pnlib.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import hoanglv.fpoly.pnlib.databases.DatabaseHelper;
import hoanglv.fpoly.pnlib.models.Sach;
import hoanglv.fpoly.pnlib.models.Top;

public class ThongKeDAO {
    private SQLiteDatabase db;
    private Context context;
    private static SachDao sachDao;

    public ThongKeDAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.context = context;
        this.db = databaseHelper.getWritableDatabase();
        this.db = databaseHelper.getReadableDatabase();
        sachDao = new SachDao(context);
    }

    public ArrayList<Top> getTop() {
        ArrayList<Top> topList = new ArrayList<>();
        String sql = "SELECT maSach, COUNT(maSach) AS soLuong FROM PHIEUMUON GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Top top = new Top();
                Sach sach = sachDao.getID(cursor.getString(0));
                top.setTenSach(sach.getTenSach());
                top.setSoLuong(cursor.getInt(1));
                topList.add(top);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("TAG", "getTop: " + topList.size());
        return topList;
    }

    public int getDoanhThu(String ngayBD, String ngayKT) {
        String sql = "SELECT SUM(tienThue) as doanhThu FROM PHIEUMUON WHERE ngay BETWEEN ? AND ?";
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, new String[]{ngayBD, ngayKT});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                try {
                    list.add(cursor.getInt(0));
                } catch (Exception e) {
                    list.add(0);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list.get(0);
    }
}
