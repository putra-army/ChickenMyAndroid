package com.example.chickenmy.Fragments.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chickenmy.Databases.DatabaseInit;
import com.example.chickenmy.GetDate;
import com.example.chickenmy.R;

public class TambahKematianActivity extends AppCompatActivity implements View.OnClickListener {

    private GetDate getDate = new GetDate();
    private DatabaseInit db = new DatabaseInit();

    private EditText edtKematianAyam;
    private TextView tvTanggal;
    private Button btnSimpan;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kematian);

        btnSimpan = findViewById(R.id.btn_kematian_simpan);
        btnBack   = findViewById(R.id.btn_back_tambah_kematian);

        edtKematianAyam = findViewById(R.id.edt_level2);
        tvTanggal = findViewById(R.id.tv_tanggal_kematian);

        btnSimpan.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        tvTanggal.setText(getDate.getDateNow("E, dd MMMM yyyy"));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_kematian_simpan) {
            db.GrafikGrid
                    .child(getDate.getDateNow("yyyy-MM-dd"))
                    .child("mati")
                    .setValue(Integer.valueOf(edtKematianAyam.getText().toString()));
            finish();
        }
        if (i == R.id.btn_back_tambah_kematian) {
            finish();
        }
    }
}
