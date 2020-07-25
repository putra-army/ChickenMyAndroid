package com.example.chickenmy.Fragments.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.chickenmy.Adapters.GridAdapter;
import com.example.chickenmy.Databases.DatabaseInit;
import com.example.chickenmy.Databases.RincianGridDatabase;
import com.example.chickenmy.Models.GridModel;
import com.example.chickenmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RincianBeratActivity extends AppCompatActivity {

    public DatabaseInit db = new DatabaseInit();
    private Spinner spinner;
    private static final String[] paths = {"item 1", "item 2", "item 3"};

    public ArrayAdapter<String> adapter;
    public List<GridModel> gridModels = new ArrayList<>();

    private RecyclerView rvGrid;
    private TextView txtBerat, txtSuhu, txtHumi, txtAmonia;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_berat);

        spinner = findViewById(R.id.spinner);
        rvGrid  = findViewById(R.id.rincian_grid_recycler);

        txtBerat    = findViewById(R.id.tv_mati_rincian);
        txtSuhu     = findViewById(R.id.tv_tertinggi_mati);
        txtHumi     = findViewById(R.id.tv_rerata_mati);
        txtAmonia   = findViewById(R.id.tv_terendah_mati);

        btnBack     = findViewById(R.id.btn_back_tambah_kematian);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new RincianGridDatabase().getData(new RincianGridDatabase.DataStatus() {
            @Override
            public void DataIsLoaded(List<String> keys) {
                Log.d("Tanggal", keys.get(0));
                adapter = new ArrayAdapter<String>(RincianBeratActivity.this,
                        android.R.layout.simple_spinner_item,keys);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String date = String.valueOf(spinner.getSelectedItem());
                        db.GrafikGrid.child(date).child("RFID").orderByChild("idGrid").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                gridModels.clear();
                                if (dataSnapshot.exists()) {
                                    int berat = 0;
                                    Double suhu = 0.0, humi = 0.0, amonia = 0.0;
                                    int jumData = 0;

                                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                                        if (ds.exists()) {
                                            final GridModel gridModel = ds.getValue(GridModel.class);

                                            berat = berat + Integer.valueOf(gridModel.getA_berat());
                                            suhu = suhu + Double.valueOf(gridModel.getD_temp());
                                            humi = humi + Double.valueOf(gridModel.getC_huma());
                                            amonia = amonia + Double.valueOf(gridModel.getB_amonia());

                                            gridModels.add(gridModel);
                                            jumData++;
                                        }
                                    }

                                    berat = berat / jumData;
                                    suhu = suhu / jumData;
                                    humi = humi / jumData;
                                    amonia = amonia / jumData;

                                    DecimalFormat df = new DecimalFormat("#.##");

                                    txtBerat.setText(String.valueOf(berat) + " Gram");
                                    txtSuhu.setText(String.valueOf(df.format(suhu)) + " °C");
                                    txtHumi.setText(String.valueOf(df.format(humi)) + " %");
                                    txtAmonia.setText(String.valueOf(df.format(amonia)) + " ppm");

                                    new GridAdapter().setConfig(rvGrid, RincianBeratActivity.this, gridModels);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }
        });



    }
}
