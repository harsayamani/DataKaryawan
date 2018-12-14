package com.example.harsayamani.datapelanggan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText updateNama, updateAlamat, updateKota, updateHP;
    Button btnUpdate;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.harsayamani.datakaryawan.R.layout.activity_update);
        updateNama = findViewById(com.example.harsayamani.datakaryawan.R.id.update_nama_pelanggan);
        updateAlamat = findViewById(com.example.harsayamani.datakaryawan.R.id.updateAlamat);
        updateKota = findViewById(com.example.harsayamani.datakaryawan.R.id.updateKota);
        updateHP = findViewById(com.example.harsayamani.datakaryawan.R.id.update_no_hp);
        btnUpdate = findViewById(com.example.harsayamani.datakaryawan.R.id.btn_update);
        databaseHelper = new DatabaseHelper(this);

        try{
            updateData();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Belum Memasukkan Inputan", Toast.LENGTH_LONG).show();
        }

    }

    private void updateData() {
        final Intent get = getIntent();
        final String kode = get.getStringExtra("dataKode");

        updateNama.setText(get.getStringExtra("dataNama"));
        updateAlamat.setText(get.getStringExtra("dataAlamat"));
        updateKota.setText(get.getStringExtra("dataKota"));
        updateHP.setText(get.getStringExtra("dataHP"));

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nama = updateNama.getText().toString();
                    String alamat = updateAlamat.getText().toString();
                    String kota = updateKota.getText().toString();
                    String hp = updateHP.getText().toString();

                    //deklarasi variabel validasi input
                    boolean isEmptyFields = false;

                    //validasi input jika field kosong
                    if (TextUtils.isEmpty(nama)){
                        isEmptyFields = true;
                        updateNama.setError("Field ini tidak boleh kosong");
                    }
                    if (TextUtils.isEmpty(alamat)){
                        isEmptyFields = true;
                        updateAlamat.setError("Field ini tidak boleh kosong");
                    }
                    if (TextUtils.isEmpty(kota)){
                        isEmptyFields = true;
                        updateKota.setError("Field ini tidak boleh kosong");
                    }if (TextUtils.isEmpty(hp)) {
                        isEmptyFields = true;
                        updateHP.setError("Field ini tidak boleh kosong");

                        //validasi input jika field tidak kosong
                    }else{
                        boolean isUpdated = databaseHelper.updateData(kode, updateNama.getText().toString(), updateAlamat.getText().toString(), updateKota.getText().toString(), updateHP.getText().toString());
                        if(isUpdated){
                            Toast.makeText(getApplicationContext(),"Data Diupdate",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(UpdateActivity.this, ListData.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Data Diupdate",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
    }
}
