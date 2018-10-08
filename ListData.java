package com.example.harsayamani.datapelanggan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListData extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ListView listData;
    ArrayList<String> list_data;
    String [] items ={"Hapus Data"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        databaseHelper = new DatabaseHelper(getBaseContext());
        listData = findViewById(R.id.listData);
        list_data = new ArrayList<>();
        listData.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_data));
//exception hendling
        try {
            getData();
            onItemClick();
            onItemLongClick();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_LONG).show();
        }
    }

    //method delete per item
    private void onItemLongClick() {
        listData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListData.this);
                builder.setTitle("Opsi");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(items[which].equals("Hapus Data")){
                            SQLiteDatabase ReadData = databaseHelper.getReadableDatabase();
                            Cursor cursor = ReadData.rawQuery("select * from tb_karyawan", null);
                            String id = " ";
                            if(cursor.moveToFirst()){
                                cursor.moveToPosition(position);
                                id = String.valueOf(cursor.getInt(0));
                            }
                            databaseHelper.deleteData(id);
                            Intent intent = new Intent(ListData.this, ListData.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }

    //klik per item di list
    private void onItemClick() {
        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                detail(position);
            }
        });
    }

    private void getData(){
        //Mengambil Repository dengan Mode Membaca
        SQLiteDatabase ReadData = databaseHelper.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM tb_karyawan",null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Akhir

        //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
        for(int count=0; count < cursor.getCount(); count++){

            cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir

            list_data.add(cursor.getString(1));//Mengambil Data Dari Kolom 1 (Nama)
            //Lalu Memasukan Semua Datanya kedalam ArrayList
        }
    }

    //method parsing data ke activity detail
    private void detail(int position){
        //memanggil data dari database
        SQLiteDatabase ReadData = databaseHelper.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM tb_karyawan",null); //untuk mengambil data sesuai query
        String dataNama = " ";
        String dataAlamat = " ";
        String dataKota = " ";
        String dataHP =" ";
        String dataNomorInduk = " ";

        if(cursor.moveToFirst()){//mengambil data dari urutan awal ke akhir
            cursor.moveToPosition(position);//memindahkan data sesuai posisi list
            dataNomorInduk = cursor.getString(0);
            dataNama = cursor.getString(1);
            dataAlamat = cursor.getString(2);
            dataKota = cursor.getString(3);
            dataHP = cursor.getString(4);
        }
        //perpindahan activity dibarengi perpindahan data
        Intent intent = new Intent(ListData.this, DetailData.class );
        intent.putExtra("kirimKode", dataNomorInduk);
        intent.putExtra("kirimNama", dataNama);
        intent.putExtra("kirimAlamat", dataAlamat);
        intent.putExtra("kirimKota", dataKota);
        intent.putExtra("kirimHP", dataHP);
        startActivity(intent);
    }

}
