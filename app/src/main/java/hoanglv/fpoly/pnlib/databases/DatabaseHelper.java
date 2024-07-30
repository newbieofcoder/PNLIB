package hoanglv.fpoly.pnlib.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PNLIB.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tao bang thu thu
        String CREATE_TABLE_ThuThu = "CREATE TABLE THUTHU (" +
                "maTT TEXT PRIMARY KEY, " +
                "tenTT TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_ThuThu);

        //tao bang thanh vien
        String CREATE_TABLE_ThanhVien = "CREATE TABLE THANHVIEN (" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen TEXT NOT NULL," +
                "namSinh TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_ThanhVien);

        //tao bang loai sach
        String CREATE_TABLE_LoaiSach = "CREATE TABLE LOAISACH (" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_LoaiSach);

        //tao bang sach
        String CREATE_TABLE_Sach = "CREATE TABLE SACH (" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSach TEXT NOT NULL, " +
                "giaThue INTEGER NOT NULL, " +
                "maLoai INTEGER REFERENCES LOAISACH(maLoai))";
        db.execSQL(CREATE_TABLE_Sach);

        //tao bang phieu muon
        String CREATE_TABLE_PhieuMuon = "CREATE TABLE PHIEUMUON (" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTT TEXT REFERENCES THUTHU(maTT), " +
                "maTV INTEGER REFERENCES THANHVIEN(maTV), " +
                "maSach INTEGER REFERENCES SACH(maSach), " +
                "tienThue INTEGER NOT NULL, " +
                "ngay DATE NOT NULL, " +
                "traSach INTEGER NOT NULL)";
        db.execSQL(CREATE_TABLE_PhieuMuon);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS THUTHU");
        db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
        db.execSQL("DROP TABLE IF EXISTS LOAISACH");
        db.execSQL("DROP TABLE IF EXISTS SACH");
        db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
        onCreate(db);
    }
}
