package com.example.chickenmy.Fragments.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chickenmy.Adapters.KematianAyamAdapter;
import com.example.chickenmy.Databases.RincianMatiDatabase;
import com.example.chickenmy.Models.GridModel;
import com.example.chickenmy.R;

import java.util.List;

public class RincianMatiActivity extends AppCompatActivity {

    private TextView tvKematian, tvTertinggi, tvRerata, tvTerendah;
    private ImageView btnBack;
    private RecyclerView rvKematian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_mati);

        tvKematian  = findViewById(R.id.tv_mati_rincian);
        tvTertinggi = findViewById(R.id.tv_tertinggi_mati);
        tvRerata    = findViewById(R.id.tv_rerata_mati);
        tvTerendah  = findViewById(R.id.tv_terendah_mati);

        btnBack     = findViewById(R.id.btn_back_tambah_kematian);

        rvKematian  = findViewById(R.id.rincian_mati_recycler);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new RincianMatiDatabase().getData(new RincianMatiDatabase.DataStatus() {
            @Override
            public void DataIsLoaded(int mati, int tertinggi, int rerata, int terendah) {
                tvKematian.setText(String.valueOf(mati) + " Ekor");
                tvTertinggi.setText(String.valueOf(tertinggi) + " Ekor");
                tvRerata.setText(String.valueOf(rerata) + " Ekor");
                tvTerendah.setText(String.valueOf(terendah) + " Ekor");
            }
        });

        new RincianMatiDatabase().getDataMatiHarian(new RincianMatiDatabase.DataMatiHarian() {
            @Override
            public void DataISLoaded(List<GridModel> gridModels, List<String> keys) {
                new KematianAyamAdapter().setConfig(rvKematian, RincianMatiActivity.this, gridModels, keys);
            }
        });
    }
}
