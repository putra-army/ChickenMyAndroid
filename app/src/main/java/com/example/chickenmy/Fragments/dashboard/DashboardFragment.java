package com.example.chickenmy.Fragments.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.chickenmy.Databases.DashboardDatabase;
import com.example.chickenmy.Databases.GrafikBeratDatabase;
import com.example.chickenmy.Databases.GrafikMatiDatabase;
import com.example.chickenmy.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private Button btnRincianBerat, btnRincianMati, btnTambahKematian;
    private TextView tvBeratToday, tvTotalMati, tvDayToday;
    private TextView tvTanggalMulai, tvTanggalAkhir;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final int green = ContextCompat.getColor(getContext(), R.color.greenColor);
        final int color = ContextCompat.getColor(getContext(), R.color.colorPrimary);

        btnRincianBerat = root.findViewById(R.id.btn_rincian_berat);
        btnRincianMati  = root.findViewById(R.id.btn_rincian_mati);
        btnTambahKematian= root.findViewById(R.id.btn_tambah_kematian);

        tvBeratToday    = root.findViewById((R.id.tv_berat_dash));
        tvTotalMati     = root.findViewById(R.id.tv_mati_dash);

        tvTanggalMulai  = root.findViewById(R.id.tv_tanggal_mulai);
        tvTanggalAkhir  = root.findViewById(R.id.tv_tanggal_selesai);

        btnRincianBerat.setOnClickListener(this);
        btnRincianMati.setOnClickListener(this);
        btnTambahKematian.setOnClickListener(this);

        new GrafikBeratDatabase().getData(new GrafikBeratDatabase.DataStatus() {
            @Override
            public void DataIsLoaded(List<Integer> beratToday, List<String> keys) {
                BarChart chart = root.findViewById(R.id.barchartBerat);

                ArrayList NoOfEmp = new ArrayList();
                ArrayList year = new ArrayList();

                int i = 0;
                for (int item : beratToday) {
                    NoOfEmp.add(new BarEntry(item, i));
                    year.add(keys.get(i));
                    i++;
                }

                BarDataSet bardataset = new BarDataSet(NoOfEmp, "Berat Ayam (Gram)");
                chart.animateY(1000);
                chart.setDescriptionTextSize(12);
                BarData data = new BarData(year, bardataset);
                bardataset.setColors(Collections.singletonList(green));
                chart.setData(data);
            }

        });

        new GrafikMatiDatabase().getData(new GrafikMatiDatabase.DataStatus() {
            @Override
            public void DataIsLoaded(List<Integer> matiToday, List<String> keys) {
                BarChart chart = root.findViewById(R.id.barchartMati);

                ArrayList NoOfEmp = new ArrayList();
                ArrayList year = new ArrayList();

                int i = 0;
                for (int item : matiToday) {
                    NoOfEmp.add(new BarEntry((Integer)item, i));
                    year.add(keys.get(i));
                    i++;
                }

                BarDataSet bardataset = new BarDataSet(NoOfEmp, "Jumlah Ayam Mati (Ekor)");
                chart.animateY(1000);
                chart.setDescriptionTextSize(12);
                BarData data = new BarData(year, bardataset);
                bardataset.setColors(Collections.singletonList(color));
                chart.setData(data);
            }
        });

        new DashboardDatabase().getDataBerat(new DashboardDatabase.DataStatus() {
            @Override
            public void DataIsLoaded(float beratToday) {
                tvBeratToday.setText(String.format("%.2f",beratToday) + " KG");
            }
        });

        new DashboardDatabase().getDataMati(new DashboardDatabase.DataStatusMati() {
            @Override
            public void DataIsLoaded(Integer totalMati) {
                tvTotalMati.setText(totalMati.toString() + " Ekor");
            }
        });

        new DashboardDatabase().getTanggalMulaiDanAkhir(new DashboardDatabase.DataTanggal() {
            @Override
            public void DataIsLoaded(String tanggalMulai, String tanggalTerakhir) {
                tvTanggalMulai.setText(tanggalMulai);
                tvTanggalAkhir.setText(tanggalTerakhir);
            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_rincian_berat) {
            startActivity(new Intent(getActivity(), RincianBeratActivity.class));
        }
        if (i == R.id.btn_rincian_mati) {
            startActivity(new Intent(getActivity(), RincianMatiActivity.class));
        }
        if (i == R.id.btn_tambah_kematian) {
            startActivity(new Intent(getActivity(), TambahKematianActivity.class));
        }
    }
}