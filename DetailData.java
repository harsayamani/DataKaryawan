package com.example.harsayamani.datapelanggan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailData extends AppCompatActivity {
    TextView dataNama, dataAlamat, dataKota, dataHP;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inisialisasi field
        setContentView(com.example.harsayamani.datakaryawan.R.layout.activity_detail_data);
        dataNama = findViewById(com.example.harsayamani.datakaryawan.R.id.detailNama);
        dataAlamat = findViewById(com.example.harsayamani.datakaryawan.R.id.detailAlamat);
        dataKota = findViewById(com.example.harsayamani.datakaryawan.R.id.detailKota);
        dataHP = findViewById(com.example.harsayamani.datakaryawan.R.id.detailHP);
        btnUpdate = findViewById(com.example.harsayamani.datakaryawan.R.id.btnUpdate);

        dataDetail();
        update();
    }
//method update
    private void update() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //data parsing ynag diterima dari activity lain
                final Intent detail = getIntent();
                final String no_induk = detail.getStringExtra("kirimKode");
                final String nama = detail.getStringExtra("kirimNama");
                final String alamat = detail.getStringExtra("kirimAlamat");
                final String kota = detail.getStringExtra("kirimKota");
                final String hp = detail.getStringExtra("kirimHP");
                Intent intent = new Intent(DetailData.this, UpdateActivity.class);
                intent.putExtra("dataKode", no_induk);
                intent.putExtra("dataNama", nama);
                intent.putExtra("dataAlamat", alamat);
                intent.putExtra("dataKota", kota);
                intent.putExtra("dataHP", hp);
                startActivity(intent);
            }
        });
    }

    //method detai karyawan
    private void dataDetail() {
        final Intent detail = getIntent();
        String nama = detail.getStringExtra("kirimNama");
        String alamat = detail.getStringExtra("kirimAlamat");
        String kota = detail.getStringExtra("kirimKota");
        String hp = detail.getStringExtra("kirimHP");

        dataNama.setText(nama);
        dataAlamat.setText(alamat);
        dataKota.setText(kota);
        dataHP.setText(hp);
    }
}
