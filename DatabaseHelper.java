package com.example.harsayamani.datapelanggan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "Penjualan", null, 1);
    }
//create database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tb_karyawan(nomor_induk integer primary key autoincrement, " +
                "nama text, " +
                "alamat text," +
                "kota text," +
                "hp text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_pelanggan ");
        onCreate(db);
    }
//methode insert
    public boolean insertData(String nama, String alamat, String kota, String hp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("nama", nama);
        contentValues.put("alamat", alamat);
        contentValues.put("kota", kota);
        contentValues.put("hp", hp);

        db.insert("tb_karyawan", "nomor_induk", contentValues);
        return true;
    }
//method update
    public boolean updateData(String nomor_induk, String nama, String alamat, String kota, String hp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("nomor_induk", nomor_induk);
        contentValues.put("nama", nama);
        contentValues.put("alamat", alamat);
        contentValues.put("kota", kota);
        contentValues.put("hp", hp);

        db.update("tb_karyawan", contentValues, "nomor_induk = ?", new String[]{nomor_induk});
        return true;
    }
//method delete
    public int deleteData(String nomor_induk){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tb_karyawan", "nomor_induk = ?", new String[]{nomor_induk});
    }

}
