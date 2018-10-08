package com.example.harsayamani.datapelanggan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    EditText nama, alamat, hp, kota, cari_data;
    Button btnInsert, btnUpdate, btn_cari;
    DatabaseHelper databaseHelper;
    TextView tampil_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //membuat objek database
        databaseHelper = new DatabaseHelper(this);
        //inisialisasi id field xml
        nama = findViewById(R.id.update_nama_pelanggan);
        alamat = findViewById(R.id.updateAlamat);
        kota = findViewById(R.id.kota);
        btnInsert = findViewById(R.id.btn_insert);
        hp = findViewById(R.id.no_hp);
        btnUpdate = findViewById(R.id.btn_update);
        btn_cari = findViewById(R.id.btn_cari);
        cari_data = findViewById(R.id.cari_data);
        tampil_data = findViewById(R.id.tampil_data);

        //validasi jika terjadi error
        try{
            cariData();
            insertData();
            lihatData();
            //error handling
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Database Kosong", Toast.LENGTH_LONG).show();
        }

    }

    private void cariData() {
        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper = new DatabaseHelper(MainActivity.this);
                String cari = cari_data.getText().toString();
                SQLiteDatabase ReadData = databaseHelper.getReadableDatabase();
                Cursor cursor = ReadData.rawQuery("SELECT * FROM tb_karyawan where nomor_induk = '" + cari+"'",null);
                String dataNama = " ";
                String dataAlamat = " ";
                String dataKota = " ";
                String dataHP =" ";

                if(cursor.moveToFirst()){
                    dataNama = cursor.getString(1);
                    dataAlamat = cursor.getString(2);
                    dataKota = cursor.getString(3);
                    dataHP = cursor.getString(4);
                }
                tampil_data.setText(dataNama);
            }
        });
    }

    //fungsi untuk melihat data yang ditampilkan dari database
    private void lihatData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pindah activity ke listData
                Intent intent = new Intent(MainActivity.this, ListData.class);
                startActivity(intent);
            }
        });
    }

    private void insertData() {
        //action klik tombol insert
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // deklarasi variabel yang akan dimasukkan di database

                String inputNama = nama.getText().toString();
                String inputAlamat = alamat.getText().toString();
                String inputKota = kota.getText().toString();
                String inputHP = hp.getText().toString();

                //deklarasi variabel validasi input
                boolean isEmptyFields = false;

                //validasi input jika field kosong
                if (TextUtils.isEmpty(inputNama)){
                    isEmptyFields = true;
                    nama.setError("Field ini tidak boleh kosong");
                }
                if (TextUtils.isEmpty(inputAlamat)){
                    isEmptyFields = true;
                    alamat.setError("Field ini tidak boleh kosong");
                }
                if (TextUtils.isEmpty(inputKota)){
                    isEmptyFields = true;
                    kota.setError("Field ini tidak boleh kosong");
                }if (TextUtils.isEmpty(inputHP)){
                    isEmptyFields = true;
                    kota.setError("Field ini tidak boleh kosong");

                 //validasi input jika field tidak kosong
                }else{
                    //insert data
                    boolean isInserted = databaseHelper.insertData(inputNama, inputAlamat, inputKota, inputHP);
                    //kondisi jika input data ke database behasil
                    if(isInserted){
                        Toast.makeText(getApplicationContext(),"Data Ditambah", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, ListData.class);
                        startActivity(intent);

                    }else{//jika tidak berhasil
                        Toast.makeText(getApplicationContext(),"Data Ditambah",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
