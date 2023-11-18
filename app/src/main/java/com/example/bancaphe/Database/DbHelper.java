package com.example.bancaphe.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
static final String dbName = "BanCaPhe";
static final int dbVersion = 7;
    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// Bảng thể loại
        String createTableTheLoai = "CREATE TABLE THELOAI(maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT);";
        db.execSQL(createTableTheLoai);
        db.execSQL("INSERT INTO THELOAI VALUES(1, 'Cà Phê Truyền Thống'), (2, 'Cà Phê Máy'), (3, 'Trà Sữa'), (4, 'Bánh Ngọt');");
//Bảng sản phẩm
        String createTableSanPham = ("CREATE TABLE SanPham(\n" +
                "MaSanPham INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "image BLOG,\n" +
                "TenSanPham TEXT,\n" +
                "Price double,\n" +
                "MaLoai INTEGER REFERENCES THELOAI(maLoai),\n" +
                "MoTa TEXT\n" +
                ");");
        db.execSQL(createTableSanPham);
        db.execSQL("INSERT INTO SanPham VALUES(1,null, 'Cà phê sữa', 25, 1, 'Ngon' )");

// Bảng chức vụ
        String createTableChucVu = "CREATE Table ChucVu(\n" +
                "MaChucVu INTEGER PRIMARY KEY,\n" +
                "TenChucVu TEXT\n" +
                ");";
        db.execSQL(createTableChucVu);
        db.execSQL("INSERT INTO ChucVu VALUES (1,'Quản Lý'),(2,'Nhân Viên')");

// Bảng User
        String tableUser = "CREATE Table User(\n" +
                "MaUser INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "FullName TEXT,\n" +
                "Username TEXT,\n" +
                "ChucVu INTEGER REFERENCES ChucVu(machucvu),\n" +
                "Password TEXT,\n" +
                "SDT TEXT,\n" +
                "NamSinh INTEGER\n" +
                ");";
        db.execSQL(tableUser);
        db.execSQL("INSERT INTO User VALUES (1,'Poly Coffee','admin',1,'admin', 0868009681, 2004)");

// Bảng hóa đơn
        String tableHoaDon = "CREATE Table HoaDon(\n" +
                "MaHoaDon INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "MaUser INTEGER REFERENCES User(MaUser),\n" +
                "TenKhachHang TEXT,\n" +
                "NgayLapHD TEXT,\n" +
                "MaGioHang INTEGER\n" +
                ");";
        db.execSQL(tableHoaDon);
// Bảng giỏ hàng
        String tableGioHang = "CREATE Table GioHang (\n" +
                "MaGioHang INTEGER,\n" +
                "MaSanPham INTEGER REFERENCES SanPham(MaSanPham),\n" +
                "SoLuong INTEGER,\n" +
                "Size TEXT,\n" +
                "DonGia DOUBLE\n" +
                ");";
        db.execSQL(tableGioHang);
// Bảng lưu hóa đơn
        String tableLuuHoaDon = "CREATE Table LuuHoaDon (\n" +
                "maLuu INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "maHoaDon INTEGER REFERENCES HoaDon(MaHoaDon),\n" +
                "maUser INTEGER REFERENCES User(MaUser),\n" +
                "tenUser TEXT,\n" +
                "tenKhachHang TEXT,\n" +
                "NgayLapHD TEXT,\n" +
                "maSP INTEGER,\n" +
                "tenSP TEXT,\n" +
                "soLuong INTEGER,\n" +
                "size TEXT,\n" +
                "donGia DOUBLE\n," +
                "thanhTien DOUBLE\n" +
                ");";
        db.execSQL(tableLuuHoaDon);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
if(oldVersion != newVersion){
    db.execSQL("drop table if exists THELOAI");
    db.execSQL("drop table if exists SanPham");
    db.execSQL("drop table if exists ChucVu");
    db.execSQL("drop table if exists User");
    db.execSQL("drop table if exists HoaDon");
    db.execSQL("drop table if exists GioHang");
    db.execSQL("drop table if exists LuuHoaDon");
onCreate(db);
}
    }
}
