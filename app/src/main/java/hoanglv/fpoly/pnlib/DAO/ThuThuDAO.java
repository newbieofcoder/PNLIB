package hoanglv.fpoly.pnlib.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hoanglv.fpoly.pnlib.databases.DatabaseHelper;
import hoanglv.fpoly.pnlib.models.ThuThu;

public class ThuThuDAO {
    private SQLiteDatabase db;

    public ThuThuDAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.db = databaseHelper.getWritableDatabase();
        this.db = databaseHelper.getReadableDatabase();
    }

    public ArrayList<ThuThu> getData() {
        ArrayList<ThuThu> thuThuList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ThuThu thuThu = new ThuThu();
                thuThu.setMaTT(cursor.getString(0));
                thuThu.setTenTT(cursor.getString(1));
                thuThu.setMatKhau(cursor.getString(2));
                thuThuList.add(thuThu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return thuThuList;
    }

    public long insert(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("maTT", thuThu.getMaTT());
        values.put("tenTT", thuThu.getTenTT());
        values.put("matKhau", thuThu.getMatKhau());
        return db.insert("THUTHU", null, values);
    }

    public void update(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("tenTT", thuThu.getTenTT());
        values.put("matKhau", thuThu.getMatKhau());
        db.update("THUTHU", values, "maTT = ?", new String[]{thuThu.getMaTT()});
    }

    public void updatePassword(String password, String ma) {
        ContentValues values = new ContentValues();
        values.put("matKhau", password);
        db.update("THUTHU", values, "maTT = ?", new String[]{ma});
    }

    public void delete(ThuThu thuThu) {
        db.delete("THUTHU", "maTT = ?", new String[]{thuThu.getMaTT()});
    }
}
